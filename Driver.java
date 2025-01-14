package com.example.ds4;


public class Driver {
    public static void main(String[] args) {
        // Create a HashTable instance with a capacity of 10
        HashTable<Movie> movieHashTable = new HashTable<>(10);

        // Create some sample Movie objects
        Movie movie1 = new Movie("Inception", "A mind-bending thriller", 2010, 8.8, "url1");
        Movie movie2 = new Movie("The Matrix", "A sci-fi classic", 1999, 8.7, "url2");
        Movie movie3 = new Movie("Interstellar", "A space epic", 2014, 8.6, "url3");
        Movie movie4 = new Movie("The Dark Knight", "A superhero movie", 2008, 9.0, "url4");

        // Insert the movies into the HashTable
        movieHashTable.put( movie1);
        movieHashTable.put(movie2);
        movieHashTable.put(movie3);
        movieHashTable.put(movie4);

        // Print the table to see the inserted movies
        System.out.println("Initial HashTable:");
        movieHashTable.printTable();

        // Clear the table
        movieHashTable.clearTable();
        System.out.println("\nHashTable after clearing:");
        movieHashTable.printTable();
    }
}
