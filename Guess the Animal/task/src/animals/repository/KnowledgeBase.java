package animals.repository;

import animals.domain.KnowledgeTree;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public interface KnowledgeBase {
    String LANGUAGE = System.getProperty("user.language");
    String FILENAME = "en".equals(LANGUAGE) ? "animals" : "animals_" + LANGUAGE;

    KnowledgeTree load();

    boolean save(KnowledgeTree tree);

    enum Type {
        XML(new XmlMapper()),
        JSON(new JsonMapper()),
        YAML(new YAMLMapper()),
        IN_MEMORY(new KnowledgeBase() {
            @Override
            public KnowledgeTree load() {
                return new KnowledgeTree();
            }

            @Override
            public boolean save(KnowledgeTree knowledgeTree) {
                return false;
            }
        });

        private final KnowledgeBase repository;

        Type(KnowledgeBase repository) {
            this.repository = repository;
        }

        Type(ObjectMapper objectMapper) {
            this.repository = new KnowledgeBaseJackson(
                    objectMapper, FILENAME + "." + this.name().toLowerCase()
            );
        }

        public KnowledgeBase getInstance() {
            return repository;
        }
    }

}