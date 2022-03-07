package animals.repository;

import animals.domain.KnowledgeTree;
import animals.domain.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KnowledgeBaseJackson implements KnowledgeBase {
    private static final Logger LOGGER = Logger.getLogger(KnowledgeBaseJackson.class.getName());

    private final ObjectMapper objectMapper;
    private final String fileName;

    KnowledgeBaseJackson(ObjectMapper mapper, String fileName) {
        objectMapper = mapper;
        this.fileName = fileName;
    }

    public KnowledgeTree load() {
        LOGGER.log(Level.CONFIG, "Loading Knowledge base: {0}", fileName);
        final var knowledgeTree = new KnowledgeTree();
        try {
            knowledgeTree.setRoot(objectMapper.readValue(new File(fileName), TreeNode.class));
        } catch (IOException error) {
            LOGGER.log(Level.WARNING, "The knowledge Tree has not been loaded.", error);
        }
        LOGGER.info("The Knowledge Tree has loaded successful.");
        LOGGER.log(Level.CONFIG, "Root node is '{0}'.", knowledgeTree.getData());
        return knowledgeTree;
    }

    public boolean save(KnowledgeTree knowledgeTree) {
        LOGGER.log(Level.CONFIG, "Saving Knowledge base: {0}", fileName);
        try {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(new File(fileName), knowledgeTree.getRoot());
        } catch (IOException error) {
            LOGGER.log(Level.SEVERE, "Could not save the Knowledge Tree.", error);
            return false;
        }
        LOGGER.info("The Knowledge Tree has saved successful.");
        return true;
    }

}