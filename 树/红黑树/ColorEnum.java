package com.company.tree.redblack;

public enum ColorEnum {

    BLACK(1),RED(0);

    private int color;

    ColorEnum(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
