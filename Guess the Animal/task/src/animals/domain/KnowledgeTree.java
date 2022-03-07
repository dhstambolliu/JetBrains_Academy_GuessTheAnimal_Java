package animals.domain;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.summarizingInt;

public class KnowledgeTree {
    private static final Logger LOGGER = Logger.getLogger(KnowledgeTree.class.getName());
    private final Map<String, List<String>> animals = new HashMap<>();
    private TreeNode current;
    private TreeNode root;

    public void reset() {
        current = root;
    }

    public String getQuestion() {
        final var question =
                current.isLeaf() ? LanguageRules.ANIMAL_QUESTION : LanguageRules.STATEMENT_QUESTION;
        return question.apply(current.getData());
    }

    public boolean isEmpty() {
        return Objects.isNull(root);
    }

    public boolean isAnimal() {
        return current.isLeaf();
    }

    public boolean isStatement() {
        return !isAnimal();
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
        this.current = root;
    }

    public void next(boolean direction) {
        current = direction ? current.getYes() : current.getNo();
    }

    public String getData() {
        return current == null ? "Null" : current.getData();
    }

    public IntSummaryStatistics getStatistics() {
        return getAnimals().values().stream().collect(summarizingInt(List::size));
    }

    public void addAnimal(final String animal, final String statement, final boolean isRight) {
        LOGGER.log(Level.FINER, "...entering method addAnimal(...)");
        final var newAnimal = new TreeNode(animal);
        final var oldAnimal = new TreeNode(current.getData());
        current.setData(statement);
        current.setYes(isRight ? newAnimal : oldAnimal);
        current.setNo(isRight ? oldAnimal : newAnimal);
        LOGGER.log(Level.FINER, "...added {0}, '{1}' - {2}", new Object[]{animal, statement, isRight});
    }

    public Map<String, List<String>> getAnimals() {
        animals.clear();
        collectAnimals(root, new LinkedList<>());
        return animals;
    }

    private void collectAnimals(final TreeNode node, final Deque<String> facts) {
        if (node.isLeaf()) {
            animals.put(node.getData(), List.copyOf(facts));
            return;
        }
        final var statement = node.getData();
        facts.add(LanguageRules.POSITIVE_FACT.apply(statement));
        collectAnimals(node.getYes(), facts);
        facts.removeLast();
        facts.add(LanguageRules.NEGATIVE_FACT.apply(statement));
        collectAnimals(node.getNo(), facts);
        facts.removeLast();
    }

    public boolean deleteAnimal(TreeNode parent, TreeNode child, String animal) {
        if (child.isLeaf() && animal.equals(child.getData())) {
            final var source = parent.getYes() == child ? parent.getNo() : parent.getYes();
            parent.setData(source.getData());
            parent.setYes(source.getYes());
            parent.setNo(source.getNo());
            return true;
        }
        return !child.isLeaf() &&
                (deleteAnimal(child, child.getYes(), animal) || deleteAnimal(child, child.getNo(), animal));
    }
}