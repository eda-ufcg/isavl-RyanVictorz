package main.java;
import java.util.*;
class Balance {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Balance  b = new Balance();
        String[] valores = sc.nextLine().split(" ");
        for (int i = 0; i < valores.length; i++)
            b.add(Integer.parseInt(valores[i]));
        b.preOrder();
    }

    private Node root;
    private int size;

    public boolean isEmpty() {
        return this.root == null;
    }

     public void add(int element) {
        this.size += 1;
        if (isEmpty())
            this.root = new Node(element);
        else {
            
            Node aux = this.root;
            
            while (aux != null) {
                
                if (element < aux.value) {
                    if (aux.left == null) { 
                        Node newNode = new Node(element);
                        aux.left = newNode;
                        newNode.parent = aux;
                        return;
                    }
                    
                    aux = aux.left;
                } else {
                    if (aux.right == null) { 
                        Node newNode = new Node(element);
                        aux.right = newNode;
                        newNode.parent = aux;
                        return;
                    }
                    
                    aux = aux.right;
                }
            }
        }
        
    }

    public void preOrder() {
    StringBuilder sb = new StringBuilder();
    buildPreOrder(this.root, sb);
    if (sb.length() > 0) {
        sb.setLength(sb.length() - 1); // remove o último espaço
    }
    System.out.println(sb.toString());
}

private void buildPreOrder(Node node, StringBuilder sb) {
    if (node != null) {
        sb.append(node.value).append(",").append(node.balance()).append(" ");
        buildPreOrder(node.left, sb);
        buildPreOrder(node.right, sb);
    }
}


    public Node max() {
        if (isEmpty()) return null;
        
        Node node = this.root;
        while(node.right != null)
            node = node.right;
        
        return node;
    }

    private Node max(Node node) {
        if (node.right == null) return node;
        else return max(node.right);
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    public int size() {
        return this.size;
    }
}

class Node {
    
    int value;
    Node left;
    Node right;
    Node parent;
    
    Node(int v) {
        this.value = v;
    }

    public Node getLeft() {
        return this.left;
    }

    public Node getRight() {
        return this.right;
    }

    public Node getParent() {
        return this.parent;
    }

    public int height() {
        return height(this);
    }

    private int height(Node node) {
        if (node == null) return -1;
        else return 1 + Math.max(height(node.left), height(node.right));
    }

    public int balance() {
        int left = this.left == null ? -1 : this.left.height();
        int right = this.right == null ? -1 : this.right.height();
        return left - right;
    }

    public boolean hasOnlyLeftChild() {
        return (this.left != null && this.right == null);
    }
    
    public boolean hasOnlyRightChild() {
        return (this.left == null && this.right != null);
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }
}