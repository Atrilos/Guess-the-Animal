package animals.binaryTree;

import animals.entity.Animal;

public class KnowledgeTree {

    Node root;
    Node current;

    public KnowledgeTree(Object value) {
        this.root = new Node(value);
    }

    public void add(Object value, Object alternate, boolean prevAnswer, boolean isRight) {
        Node newNode = new Node(value);

        if (root.value instanceof Animal) {

            if (prevAnswer) {
                newNode.right = root;
                newNode.right.parent = newNode;
                newNode.left = new Node(alternate, newNode);
            } else {
                newNode.right = new Node(alternate, newNode);
                newNode.left = root;
                newNode.left.parent = newNode;
            }

            root = newNode;
        } else {
            if (prevAnswer) {
                newNode.right = new Node(current.value, newNode);
                newNode.left = new Node(alternate, newNode);
            } else {
                newNode.right = new Node(alternate, newNode);
                newNode.left = new Node(current.value, newNode);
            }
            newNode.parent = current.parent;
            if (isRight) {
                current.parent.right = newNode;
            } else {
                current.parent.left = newNode;
            }
        }
    }

    public Object getRootValue() {
        current = root;
        return current.value;
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

    static class Node {
        Object value;
        Node left;
        Node right;
        Node parent;

        Node(Object value) {
            this.value = value;
            right = null;
            left = null;
            parent = null;
        }

        public Node(Object value, Node parent) {
            this.value = value;
            right = null;
            left = null;
            this.parent = parent;

        }
    }
}
