package main.java;
import java.util.Scanner;

class Rotacoes {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Rotacoes r = new Rotacoes();
        String[] valores = sc.nextLine().split(" ");
        for (int i = 0; i < valores.length; i++)
            r.add(Integer.parseInt(valores[i]));
        r.rotate();
    }

    private Node root;
    private int size;
    private Node lastInserted;

    public boolean isEmpty() {
        return this.root == null;
    }

    public void add(int element) {
        this.size += 1;
        if (isEmpty()) {
            this.root = new Node(element);
            this.lastInserted = this.root;
        } else {
            Node aux = this.root;
            while (aux != null) {
                if (element < aux.value) {
                    if (aux.left == null) {
                        Node newNode = new Node(element);
                        aux.left = newNode;
                        newNode.parent = aux;
                        this.lastInserted = newNode;
                        return;
                    }
                    aux = aux.left;
                } else {
                    if (aux.right == null) {
                        Node newNode = new Node(element);
                        aux.right = newNode;
                        newNode.parent = aux;
                        this.lastInserted = newNode;
                        return;
                    }
                    aux = aux.right;
                }
            }
        }   
    }

    public Node leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) y.left.parent = x;
        y.left = x;
        y.parent = x.parent;
        x.parent = y;
        return y;
    }

    public Node rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != null) y.right.parent = x;
        y.right = x;
        y.parent = x.parent;
        x.parent = y;
        return y;
    }

    public void rotate() {
        Node z = lastInserted;
        Node y = z != null ? z.parent : null;
        Node x = y != null ? y.parent : null;

        if (x == null || y == null || z == null) {
            if (root != null && root.isBalanced()) {
                System.out.println("balanceada");
            }
            return;
        }

        if (x.isRightPending() && y.left == z) {
            Node newY = rightRotate(y);
            if (x.right == y) x.right = newY;
            newY.parent = x;

            System.out.println("rot_dir(" + y.value + ")");
            preOrder();

            Node newX = leftRotate(x);
            if (x == root) root = newX;
            else {
                Node p = x.parent;
                if (p.left == x) p.left = newX;
                else p.right = newX;
                newX.parent = p;
            }

            System.out.println("rot_esq(" + x.value + ")");
            preOrder();
        }

        else if (x.isLeftPending() && y.right == z) {
            Node newY = leftRotate(y);
            if (x.left == y) x.left = newY;
            newY.parent = x;

            System.out.println("rot_esq(" + y.value + ")");
            preOrder();

            Node newX = rightRotate(x);
            if (x == root) root = newX;
            else {
                Node p = x.parent;
                if (p.left == x) p.left = newX;
                else p.right = newX;
                newX.parent = p;
            }

            System.out.println("rot_dir(" + x.value + ")");
            preOrder();
        }

        else if (x.isLeftPending() && y.left == z) {
            Node newRoot = rightRotate(x);
            if (x == root) root = newRoot;
            else {
                Node p = x.parent;
                if (p.left == x) p.left = newRoot;
                else p.right = newRoot;
                newRoot.parent = p;
            }

            System.out.println("rot_dir(" + x.value + ")");
            preOrder();
        }

        else if (x.isRightPending() && y.right == z) {
            Node newRoot = leftRotate(x);
            if (x == root) root = newRoot;
            else {
                Node p = x.parent;
                if (p.left == x) p.left = newRoot;
                else p.right = newRoot;
                newRoot.parent = p;
            }

            System.out.println("rot_esq(" + x.value + ")");
            preOrder();
        }

        else {
            System.out.println("balanceada");
        }
    }


    public void preOrder() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        buildPreOrder(this.root, sb);
        if (sb.length() > 2) sb.setLength(sb.length() - 2); // remove última vírgula e espaço
            sb.append("]");
        System.out.println(sb.toString());
    }

    private void buildPreOrder(Node node, StringBuilder sb) {
        if (node != null) {
            sb.append(node.value).append(", ");
        buildPreOrder(node.left, sb);
        buildPreOrder(node.right, sb);
    }
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

    public boolean isLeftPending() {
        return this.balance() >= 2;
    }

    public boolean isRightPending() {
        return this.balance() <= -2;
    }

    public boolean isBalanced() {
        int b = this.balance();
        return b >= -1 && b <= 1;
    }

    public int balance() {
        int left = this.left == null ? -1 : this.left.height();
        int right = this.right == null ? -1 : this.right.height();
        return left - right;
    }

    public int height() {
        return height(this);
    }

    private int height(Node node) {
        if (node == null) return -1;
        else return 1 + Math.max(height(node.left), height(node.right));
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