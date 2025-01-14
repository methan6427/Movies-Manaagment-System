package com.example.ds4;



public class HashNode<T> {
    private int key;
    private T Value;
    private char status;
    private HashNode<T> next;

    public HashNode(T value, int key, char status) {
        Value = value;
        this.key = key;
        this.status = status;
    }

    public HashNode(int key, T value) {
        this.key = key;
        this.Value = value;
        this.status = 'F';
    }
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public T getValue() {
        return Value;
    }

    public void setValue(T value) {
        Value = value;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public HashNode<T> getNext() {
        return next;
    }

    public void setNext(HashNode<T> next) {
        this.next = next;
    }
}