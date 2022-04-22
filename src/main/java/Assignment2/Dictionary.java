/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

/**
 *
 * @author josia
 * @author david
 */
public class Dictionary {
   private class Node {
        WordInfo node_word;
        Node left;
        Node right;

        public Node(WordInfo word) {
            this.node_word = word;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public Dictionary() {
        this.root = null;
    }

    private Node add(Node root, String word, String meaning) {
        if (root == null) {
            root = new Node(new WordInfo(word, meaning));
        }
        else if (word.compareTo(root.node_word.getWord()) < 0) {
            root.left = add(root.left, word, meaning);
        }
        else {
            root.right = add(root.right, word, meaning);
        }
        return root;
    }
    //Function to add a word and definition without the node root
    public boolean add(String word, String meaning) {
        word = word.toLowerCase();
        if (this.root == null) {
            this.root = new Node(new WordInfo(word, meaning));
            return true;
        }
        if (exists(this.root, word))
            return false;
        else
            add(this.root, word, meaning);
        return true;
    }
    private boolean delete(Node root, Node previous, String word) {
        if (root == null)
            return false;
        if (root.node_word.getWord().equals(word)) {
            if (root.left == null && root.right == null) {
                if (previous == null)
                    this.root = null;
                else
                    if (previous.left == root)
                        previous.left = null;
                    else
                        previous.right = null;
            }
            else if (root.left != null && root.right == null) {
                root.node_word = root.left.node_word;
                if (root.left.left == null)
                    root.left = null;
                else
                    root.left = root.left.left;
            }
            else if (root.left == null && root.right != null) {
                root.node_word = root.right.node_word;
                if (root.right.right == null)
                    root.right = null;
                else
                    root.right = root.right.right;
            }
            else {
                if (root.right.left == null) {
                    root.node_word = root.right.node_word;
                    if (root.right.right == null)
                        root.right = null;
                    else
                        root.right = root.right.right;
                }
                else {
                    Node current = root.right;
                    Node successor = root.right.left;
                    while (successor.left != null) {
                        current = successor;
                        successor = successor.left;
                    }
                    root.node_word = successor.node_word;
                    if (successor.right != null) {
                        current.left = successor.right;
                    }
                    else
                        current.left = null;
                }
            }
            return true;
        }
        if (word.compareTo(root.node_word.getWord()) < 0)
            return delete(root.left, root, word);
        else
            return delete(root.right, root, word);
    }

    //Overloaded function to delete word just by the word
    public boolean delete(String word) {
        word = word.toLowerCase();
        if (!exists(this.root, word))
            return false;
        return delete(this.root, null, word);
    }

    private boolean exists(Node root, String word) {
        if (root == null)
            return false;
        if (root.node_word.getWord().equals(word))
            return true;
        if (word.compareTo(root.node_word.getWord()) < 0)
            return exists(root.left, word);
        else
            return exists(root.right, word);
    }

    public boolean exists(String word) {
        word = word.toLowerCase();
        return exists(this.root, word);
    }

    private String getMeaning(Node root, String word) {
        if (root.node_word.getWord().equals(word))
            return root.node_word.getMeaning();
        if (word.compareTo(root.node_word.getWord()) < 0)
            return getMeaning(root.left, word);
        else
            return getMeaning(root.right, word);

    }

    public String getMeaning(String word) {
        word = word.toLowerCase();
        if (!exists(this.root, word))
            return "Word not found in dictionary";
        return getMeaning(this.root, word);
    }

    //count takes the root and returns 0 if null, or recursively calls itself to count up if not null
    private int count(Node root) {
        if (root == null)
            return 0;
        return 1 + count(root.left) + count(root.right);
    }

    public int getCount() {
        return count(this.root);
    }

    private String printWordList(Node root) {
        String words = "";
        if (root.left != null)
            words += printWordList(root.left);
        words += root.node_word.getWord() + '\n';
        if (root.right != null)
            words += printWordList(root.right);
        return words;
    }
    //overloaded function for printing words without an argument
    public String printWordList() {
        if (this.root == null)
            return "Dictionary is empty";
        return printWordList(this.root);
    }

    private void printDictionary(Node root) {
        if (root.left != null)
            printDictionary(root.left);
        System.out.println(root.node_word);
        if (root.right != null)
            printDictionary(root.right);
    }
    //overloaded function for printing dictionary with no arguments
    public void printDictionary() {
        if (this.root == null)
            System.out.println("Dictionary is empty");
        else
            printDictionary(this.root);
    }
  
}
