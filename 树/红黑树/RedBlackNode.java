package com.company.tree.redblack;

public class RedBlackNode<T> {

    private RedBlackNode<T> left;
    private RedBlackNode<T> right;
    private RedBlackNode<T> parent;
    private T value;
    private int color = ColorEnum.BLACK.getColor();

    public RedBlackNode(T value) {
        this(value, null, null);
    }

    public RedBlackNode(T value, RedBlackNode<T> left, RedBlackNode<T> right) {
        this(value, left, right, null);
    }

    public RedBlackNode(T value, RedBlackNode<T> left, RedBlackNode<T> right, RedBlackNode<T> parent) {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.value = value;
    }

    public RedBlackNode<T> getLeft() {
        return left;
    }

    public void setLeft(RedBlackNode<T> left) {
        this.left = left;
    }

    public RedBlackNode<T> getRight() {
        return right;
    }

    public void setRight(RedBlackNode<T> right) {
        this.right = right;
    }

    public RedBlackNode<T> getParent() {
        return parent;
    }

    public void setParent(RedBlackNode<T> parent) {
        this.parent = parent;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
