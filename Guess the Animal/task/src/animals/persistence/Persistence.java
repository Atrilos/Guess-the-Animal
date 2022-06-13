package animals.persistence;

import animals.binaryTree.KnowledgeTree;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.IOException;

public class Persistence {

    public static void save(KnowledgeTree tree, String fileName, ObjectMapper objectMapper) throws IOException {
        objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(new File(fileName), tree.getRoot());
    }

    public static KnowledgeTree load(String fileName, ObjectMapper objectMapper) throws IOException {
        KnowledgeTree.Node root = objectMapper.readValue(new File(fileName), KnowledgeTree.Node.class);
        root.transform();
        return new KnowledgeTree(root);
    }
}
