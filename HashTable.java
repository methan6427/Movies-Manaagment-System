package com.example.ds4;

import java.util.ArrayList;
import java.util.List;

// HashTable Class
public class HashTable<T> {
    private Object[] table; // Holds either null or an AVL tree
    private int size;
    private int capacity;

    public Object[] getTable() {
        return table;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    // Constructor
    public HashTable(int initialCapacity) {
        this.capacity = getNextPrime(initialCapacity);
        this.table = new Object[this.capacity];
        this.size = 0;
    }

    // Clear the hash table
    public void clearTable() {
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
        }
        size = 0;
    }

    // Hash function for strings
    public int hashFunction(String title) {
        int hash = 0;
        for (char c : title.toCharArray()) {
            hash = (31 * hash + c) % capacity;
        }
        return hash;
    }

    // Check if a number is prime
    public boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    // Get the next prime number
    public int getNextPrime(int number) {
        while (!isPrime(number)) {
            number++;
        }
        return number;
    }

    // Insert a movie into the hash table
    public void put(Movie movie) {
        int index = hashFunction(movie.getTitle());

        // Insert into the table
        if (table[index] == null) {
            MovieAvlTree<Movie> tree = new MovieAvlTree<>();
            tree.insert(movie);
            table[index] = tree;
        } else {
            MovieAvlTree<Movie> tree = (MovieAvlTree<Movie>) table[index];
            tree.insert(movie);
        }
        size++;

        // Check if resizing is needed
        if (calculateAverageHeight() > 3) {
            resizeTable();
        }
    }

    // Get a movie by title
    public Movie get(String title) {
        int index = hashFunction(title);

        // Check if the index contains any data
        if (table[index] == null) {
            return null; // No data at this index
        }

        // Retrieve the AVL tree at the index
        MovieAvlTree<Movie> tree = (MovieAvlTree<Movie>) table[index];

        // Search for the movie in the AVL tree
        return searchInAVLTree(tree.getRoot(), title);
    }

    // Helper method to search in the AVL tree
    private Movie searchInAVLTree(AVLNode<Movie> node, String title) {
        if (node == null) {
            return null; // Movie not found
        }

        int comparison = node.data.getTitle().compareTo(title);

        if (comparison < 0) {
            // Search in the left subtree
            return searchInAVLTree(node.left, title);
        } else if (comparison > 0) {
            // Search in the right subtree
            return searchInAVLTree(node.right, title);
        } else {
            // Movie found
            return node.data;
        }
    }
    public void remove(String title) {
        int index = hashFunction(title);

        // Check if the index contains any data
        if (table[index] == null) {
            System.out.println("Movie not found: " + title);
            return;
        }

        // Retrieve the AVL tree at the index
        MovieAvlTree<Movie> tree = (MovieAvlTree<Movie>) table[index];

        // Attempt to remove the movie from the AVL tree
        Movie dummyMovie = new Movie(title, "", 0, 0.0, "");
        tree.delete(dummyMovie);

        // If the AVL tree becomes empty, remove it from the table
        if (tree.getRoot() == null) {
            table[index] = null;
        }

        System.out.println("Movie removed: " + title);
    }


    // Delete a movie by title
    public void delete(String title) {
        int index = hashFunction(title);
        if (table[index] == null) {
            System.out.println("Movie not found.");
            return;
        }
        MovieAvlTree<Movie> tree = (MovieAvlTree<Movie>) table[index];
        tree.delete(new Movie(title, "", 0, 0.0, ""));
        System.out.println("Deleted movie: " + title);
    }

    // Calculate the average height of all AVL trees
    private double calculateAverageHeight() {
        int totalHeight = 0;
        int treeCount = 0;

        for (Object obj : table) {
            if (obj instanceof MovieAvlTree) {
                MovieAvlTree<Movie> tree = (MovieAvlTree<Movie>) obj;
                totalHeight += tree.getHeight(tree.getRoot());
                treeCount++;
            }
        }

        return treeCount == 0 ? 0 : (double) totalHeight / treeCount;
    }

    // Resize the hash table
    private void resizeTable() {
        int newCapacity = getNextPrime(capacity * 2);
        Object[] newTable = new Object[newCapacity];

        // Rehash all entries
        for (Object obj : table) {
            if (obj instanceof MovieAvlTree) {
                MovieAvlTree<Movie> tree = (MovieAvlTree<Movie>) obj;
                rehashTree(tree, newTable, newCapacity);
            }
        }

        // Update table and capacity
        table = newTable;
        capacity = newCapacity;
        System.out.println("HashTable resized to new capacity: " + newCapacity);
    }

    // Rehash an AVL tree into the new table
    private void rehashTree(MovieAvlTree<Movie> tree, Object[] newTable, int newCapacity) {
        rehashNode(tree.getRoot(), newTable, newCapacity);
    }

    private void rehashNode(AVLNode<Movie> node, Object[] newTable, int newCapacity) {
        if (node != null) {
            // Rehash current node
            int newIndex = Math.abs(node.data.getTitle().hashCode() % newCapacity);
            if (newTable[newIndex] == null) {
                newTable[newIndex] = new MovieAvlTree<Movie>();
            }
            MovieAvlTree<Movie> newTree = (MovieAvlTree<Movie>) newTable[newIndex];
            newTree.insert(node.data);

            // Rehash left and right subtrees
            rehashNode(node.left, newTable, newCapacity);
            rehashNode(node.right, newTable, newCapacity);
        }
    }

    // Print the table for debugging
    public void printTable() {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                System.out.println("Index " + i + ": ");
                MovieAvlTree<Movie> tree = (MovieAvlTree<Movie>) table[i];
                tree.inOrder(); // Print all movies in the tree
            } else {
                System.out.println("Index " + i + ": Empty");
            }
        }
    }
    public MovieLinkedList getYear(String year) {
        MovieLinkedList moviesByYear = new MovieLinkedList();
        int targetYear;

        try {
            targetYear = Integer.parseInt(year); // Convert year to integer
        } catch (NumberFormatException e) {
            System.out.println("Invalid year format: " + year);
            return moviesByYear;  // Return empty linked list in case of error
        }

        // Iterate through the hash table
        for (Object obj : table) {
            if (obj instanceof MovieAvlTree) {
                MovieAvlTree<Movie> tree = (MovieAvlTree<Movie>) obj;
                collectMoviesByYear(tree.getRoot(), targetYear, moviesByYear);
            }
        }

        return moviesByYear;
    }

    public MovieLinkedList getAllMovies() {
        MovieLinkedList movies = new MovieLinkedList();

        // Iterate through the hash table
        for (Object obj : table) {
            if (obj instanceof MovieAvlTree) {
                MovieAvlTree<Movie> tree = (MovieAvlTree<Movie>) obj;
                collectMovies(tree.getRoot(), movies);
            }
        }

        return movies;
    }


    private void collectMovies(AVLNode<Movie> node, MovieLinkedList movies) {
        if (node != null) {
            collectMovies(node.left, movies); // Traverse left subtree
            movies.add(node.data);           // Add movie to the list
            collectMovies(node.right, movies); // Traverse right subtree
        }
    }
    private void collectMoviesByYear(AVLNode<Movie> node, int year, MovieLinkedList moviesByYear) {
        if (node != null) {
            // Traverse left subtree
            collectMoviesByYear(node.left, year, moviesByYear);

            // Check if the movie's release year matches the target year
            if (node.data.getReleaseYear() == year) {
                moviesByYear.add(node.data);
            }

            // Traverse right subtree
            collectMoviesByYear(node.right, year, moviesByYear);
        }
    }
}
