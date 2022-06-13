package animals.binaryTree;

import animals.entity.Animal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class KnowledgeTree {

    private Node root;
    @JsonIgnore
    private Node current;

    public KnowledgeTree(Object value) {
        this.root = new Node(value);
    }

    public KnowledgeTree(Node root) {
        this.root = root;
    }

    public KnowledgeTree() {
    }

    public void add(Object value, Object alternate, boolean prevAnswer, boolean isRight) {
        Node newNode = new Node(value);

        if (root.value instanceof Animal) {

            if (prevAnswer) {
                newNode.right = root;
                newNode.left = new Node(alternate);
            } else {
                newNode.right = new Node(alternate);
                newNode.left = root;
            }

            root = newNode;
        } else {
            if (prevAnswer) {
                newNode.right = new Node(current.value);
                newNode.left = new Node(alternate);
            } else {
                newNode.right = new Node(alternate);
                newNode.left = new Node(current.value);
            }

            if (isRight) {
                getParent(root, current.value, null).right = newNode;
            } else {
                getParent(root, current.value, null).left = newNode;
            }
        }
    }

    private Node getParent(Node node, Object val, Node parent) {
        if (node == null) {
            return null;
        } else if (!(node.value == val)) {
            parent = getParent(node.left, val, node);
            if (parent == null) {
                parent = getParent(node.right, val, node);
            }
        }
        return parent;
    }

    public Object getRootValue() {
        current = root;
        return current.value;
    }

    public Node getRoot() {
        return root;
    }

    public Object getNextValue(boolean isTrue) {

        if (isTrue) {
            if (current.right != null) {
                current = current.right;
            } else {
                return null;
            }
        } else {
            if (current.left != null) {
                current = current.left;
            } else {
                return null;
            }
        }

        return current.value;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Node {
        private Object value;
        private Node left;
        private Node right;

        public Node() {
        }

        Node(Object value) {
            this.value = value;
            right = null;
            left = null;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        private void transform(Node node) {
            if (node == null) {
                return;
            }

            if (node.value instanceof Map) {
                LinkedHashMap<?, ?> map = (LinkedHashMap<?, ?>) node.value;
                String question = (String) map.get("question");
                node.value = new Animal(question.replaceAll("(?i)is it (.*)\\?", "$1"));
            }
            transform(node.left);
            transform(node.right);
        }

        public void transform() {
            transform(this);
        }
    }
}
