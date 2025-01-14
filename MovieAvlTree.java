package com.example.ds4;

public class MovieAvlTree<Movie extends Comparable<Movie>> implements Comparable<MovieAvlTree<Movie>> {
    private AVLNode<Movie> root; // Root of the AVL tree
    private String title;
    private String description;
    private int releaseYear;
    private double rating;

    public MovieAvlTree(String title, String description, int releaseYear, double rating) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    public MovieAvlTree() {
        root = null;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public AVLNode<Movie> getRoot() {
        return root;
    }

    public void setRoot(AVLNode<Movie> root) {
        this.root = root;
    }

    // CompareTo implementation for MovieAvlTree
    @Override
    public int compareTo(MovieAvlTree<Movie> other) {
        return this.title.compareTo(other.title);
    }

    private boolean isEmpty() {
        return root == null;
    }

    public void insert(Movie movie) {
        root = insert(root, movie);
    }

    private AVLNode<Movie> insert(AVLNode<Movie> node, Movie data) {
        if (node == null) {
            return new AVLNode<>(data);
        }

        if (data.compareTo(node.data) < 0) {
            node.left = insert(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = insert(node.right, data);
        }

        return rebalance(node);
    }

    private AVLNode<Movie> rebalance(AVLNode<Movie> node) {
        int balance = getHeightDifference(node);

        if (balance > 1) {
            if (getHeightDifference(node.left) >= 0) {
                return rotateRight(node);
            } else {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }

        if (balance < -1) {
            if (getHeightDifference(node.right) <= 0) {
                return rotateLeft(node);
            } else {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }

        return node;
    }

    private AVLNode<Movie> rotateRight(AVLNode<Movie> node) {
        AVLNode<Movie> leftChild = node.left;
        node.left = leftChild.right;
        leftChild.right = node;
        return leftChild;
    }

    private AVLNode<Movie> rotateLeft(AVLNode<Movie> node) {
        AVLNode<Movie> rightChild = node.right;
        node.right = rightChild.left;
        rightChild.left = node;
        return rightChild;
    }

    public int getHeight(AVLNode<Movie> root) {
        if (root == null) {
            return 0;
        }
        return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
    }

    private int getHeightDifference(AVLNode<Movie> node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    public MovieLinkedList inOrder() {
        MovieLinkedList list = new MovieLinkedList();
        return inOrder(list,root);
    }

    private MovieLinkedList inOrder(MovieLinkedList list,AVLNode<Movie> node) {
        if (node != null) {
            inOrder(list,node.left);
            list.add((com.example.ds4.Movie) node.data);
            inOrder(list,node.right);
        }
        return list;
    }

    public void delete(Movie movie) {
        root = delete(root, movie);
    }

    private AVLNode<Movie> delete(AVLNode<Movie> node, Movie data) {
        if (node == null) {
            return null;
        }

        if (data.compareTo(node.data) < 0) {
            node.left = delete(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = delete(node.right, data);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                AVLNode<Movie> temp = getMinValueNode(node.right);
                node.data = temp.data;
                node.right = delete(node.right, temp.data);
            }
        }

        if (node == null) {
            return null;
        }

        return rebalance(node);
    }

    private AVLNode<Movie> getMinValueNode(AVLNode<Movie> node) {
        AVLNode<Movie> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    @Override
    public String toString() {
        return "MovieAvlTree{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseYear=" + releaseYear +
                ", rating=" + rating +
                '}';
    }
}