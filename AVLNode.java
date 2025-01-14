package com.example.ds4;


public class AVLNode<T> {
    T data;
    AVLNode<T> left, right;

    public AVLNode(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public AVLNode<T> getLeft() {
        return left;
    }

    public void setLeft(AVLNode<T> left) {
        this.left = left;
    }

    public AVLNode<T> getRight() {
        return right;
    }

    public void setRight(AVLNode<T> right) {
        this.right = right;
    }

    @Override
    public String toString() {
        String leftStr = (left == null) ? "null" : left.data.toString();
        String rightStr = (right == null) ? "null" : right.data.toString();
        return "root=" + data + ", left=" + leftStr + ", right=" + rightStr;
    }
}