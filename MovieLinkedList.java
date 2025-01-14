package com.example.ds4;
class MovieNode {
    private Movie data;
    private MovieNode next;

    // Constructor
    public MovieNode(Movie data) {
        this.data = data;
        this.next = null;
    }

    // Getter and Setter for data
    public Movie getData() {
        return data;
    }

    public void setData(Movie data) {
        this.data = data;
    }

    // Getter and Setter for next node
    public MovieNode getNext() {
        return next;
    }

    public void setNext(MovieNode next) {
        this.next = next;
    }

    // toString method for Node
    @Override
    public String toString() {
        return data.toString();  // Directly use Movie's toString
    }
}
public class MovieLinkedList {
    private MovieNode head;
    private MovieNode tail;
    private int size;  // To track the size of the linked list

    public MovieLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Add a Movie to the list
    public void add(Movie movie) {
        MovieNode newNode = new MovieNode(movie);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;  // Increment size
    }

    // Get the size of the linked list
    public int size() {
        return size;
    }
    public Movie get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        MovieNode current = head;
        int currentIndex = 0;

        // Traverse to the desired index
        while (currentIndex < index) {
            current = current.getNext();
            currentIndex++;
        }

        return current.getData();  // Return the Movie at the specified index
    }

    // Method to traverse and print the list (or any other operation)
    public void printList() {
        MovieNode current = head;
        while (current != null) {
            System.out.println(current.getData());
            current = current.getNext();
        }
    }

    // Getter for the head node (optional, in case you need it for traversal)
    public MovieNode getHead() {
        return head;
    }

    // Setter for the head node (optional, in case you need to reset it)
    public void setHead(MovieNode head) {
        this.head = head;
    }
}
