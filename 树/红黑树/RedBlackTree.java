package com.company.tree.redblack;

import java.util.TreeMap;

public class RedBlackTree<T extends Comparable<? super T>> {

    private RedBlackNode<T> root;

    private int size = 0;

    public RedBlackTree() {
        root = null;
    }

    public RedBlackTree(RedBlackNode<T> root) {
        this.root = root;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(T value) {
        RedBlackNode<T> redBlackNode = getNodeByValue(value);
        return redBlackNode != null;
    }

    public RedBlackNode<T> getNodeByValue(T value) {
        return getNodeByValue(root, value);
    }

    private RedBlackNode<T> getNodeByValue(RedBlackNode<T> current, T value) {
        if (current == null) {
            return null;
        }
        int compareResult = value.compareTo(current.getValue());
        if (compareResult > 0) {
            return getNodeByValue(current.getRight(), value);
        } else if (compareResult < 0) {
            return getNodeByValue(current.getLeft(), value);
        } else {
            return current;
        }
    }

    public RedBlackNode<T> parentOf(RedBlackNode<T> current) {
        return current == null ? null : current.getParent();
    }

    public RedBlackNode<T> leftOf(RedBlackNode<T> current) {
        return current == null ? null : current.getLeft();
    }

    public RedBlackNode<T> rightOf(RedBlackNode<T> current) {
        return current == null ? null : current.getRight();
    }

    public int colorOf(RedBlackNode<T> current) {
        return current != null ? current.getColor() : ColorEnum.BLACK.getColor();
    }

    public void setColor(RedBlackNode<T> current, int newColor) {
        if (current != null) {
            current.setColor(newColor);
        }
    }

    public int compare(T value1, T value2) {
        return value1.compareTo(value2);
    }

    public boolean insert(T value) {
        insertValue(value);
        return true;
    }

    private void insertValue(T value) {
        RedBlackNode<T> temp = root;
        if (temp == null) {
            compare(value, value); // value 为null 时报错
            RedBlackNode<T> newNode = new RedBlackNode<>(value);
            root = newNode;
            size++;
            return;
        }
        RedBlackNode<T> parent;
        do {
            parent = temp;
            int compareResult = compare(value, temp.getValue());
            if (compareResult >= 0) {
                temp = temp.getRight();
            } else if (compareResult < 0) {
                temp = temp.getLeft();
            }
        } while (temp != null);
        RedBlackNode<T> newNode = new RedBlackNode<>(value, null, null, parent);
        if (compare(value, parent.getValue()) > 0) {
            parent.setRight(newNode);
        } else {
            parent.setLeft(newNode);
        }
        size++;
        handRedBlackTree(newNode);
    }

    private void handRedBlackTree(RedBlackNode<T> newNode) {
        newNode.setColor(ColorEnum.RED.getColor());

        while (newNode != null && newNode != root && colorOf(parentOf(newNode)) == ColorEnum.RED.getColor()) {
            // 判断父节点是祖父节点的哪个节点
            // 父节点是祖父节点的左节点
            if (leftOf(parentOf(parentOf(newNode))) == parentOf(newNode)) {
                // 则祖父节点的右节点为叔节点 且叔节点也为红色
                if (colorOf(rightOf(parentOf(parentOf(newNode)))) == ColorEnum.RED.getColor()) {
                    // 把父节点和叔节点变为黑色，把祖父节点变为红色
                    setColor(parentOf(newNode), ColorEnum.BLACK.getColor());
                    setColor(rightOf(parentOf(parentOf(newNode))), ColorEnum.BLACK.getColor());
                    setColor(parentOf(parentOf(newNode)), ColorEnum.RED.getColor());
                    // 因为本来祖父节点为黑色，父节点和叔节点为红色，所以转化后，仍满足红黑树条件四
                    // 但是祖父节点变成了红色，为防止祖父节点的父节点为红色
                    // 所以需要将当前节点转化为祖父节点，重复该操作，满足该操作
                    newNode = parentOf(parentOf(newNode));
                }
                // 叔节点为黑色（该情况一般为叔节点为null） 需要进行旋转
                else {
                    // 简化下面的步骤
                    if (rightOf(parentOf(newNode)) == newNode) {
                        newNode = parentOf(newNode);
                        rotateWithRightChildToLeft(newNode);
                    }
                    rotateWithLeftChildToRight(parentOf(parentOf(newNode)));
                    setColor(parentOf(newNode), ColorEnum.BLACK.getColor());
                    setColor(rightOf(parentOf(newNode)), ColorEnum.RED.getColor());
                  /*  // 如果为左左的情况
                    if(leftOf(parentOf(newNode)) == newNode){
                        rotateWithLeftChildToRight(parentOf(parentOf(newNode)));
                        setColor(parentOf(newNode),ColorEnum.BLACK.getColor());
                        setColor(rightOf(parentOf(newNode)),ColorEnum.RED.getColor());
                    }
                    // 左右
                    else{
                        // 将左右变成左左
                        // 因为左旋以后插入的叶子节点会被其父节点代替
                        newNode = parentOf(newNode);
                        rotateWithRightChildToLeft(newNode);
                        rotateWithLeftChildToRight(parentOf(parentOf(newNode)));
                        setColor(parentOf(newNode),ColorEnum.BLACK.getColor());
                        setColor(rightOf(parentOf(newNode)),ColorEnum.RED.getColor());
                    }*/
                }
            }
            // 右节点
            else {
                if (colorOf(leftOf(parentOf(parentOf(newNode)))) == ColorEnum.RED.getColor()) {
                    // 把父节点和叔节点变为黑色，把祖父节点变为红色
                    setColor(parentOf(newNode), ColorEnum.BLACK.getColor());
                    setColor(leftOf(parentOf(parentOf(newNode))), ColorEnum.BLACK.getColor());
                    setColor(parentOf(parentOf(newNode)), ColorEnum.RED.getColor());
                    // 因为本来祖父节点为黑色，父节点和叔节点为红色，所以转化后，仍满足红黑树条件四
                    // 但是祖父节点变成了红色，为防止祖父节点的父节点为红色
                    // 所以需要将当前节点转化为祖父节点，重复该操作，满足该操作
                    newNode = parentOf(parentOf(newNode));
                }
                // 叔节点为黑色（该情况一般为叔节点为null）
                else {
                    // 简化下面的步骤
                    if (leftOf(parentOf(newNode)) == newNode) {
                        newNode = parentOf(newNode);
                        rotateWithLeftChildToRight(newNode);
                    }
                    rotateWithRightChildToLeft(parentOf(parentOf(newNode)));
                    setColor(parentOf(newNode), ColorEnum.BLACK.getColor());
                    setColor(leftOf(parentOf(newNode)), ColorEnum.RED.getColor());
                    /*// 如果为右右的情况
                    if(rightOf(parentOf(newNode)) == newNode){
                        rotateWithRightChildToLeft(parentOf(parentOf(newNode)));
                        setColor(parentOf(newNode),ColorEnum.BLACK.getColor());
                        setColor(leftOf(parentOf(newNode)),ColorEnum.RED.getColor());
                    }
                    // 右左
                    else{
                        // 将右左变成右右
                        // 因为右旋以后插入的叶子节点会被其父节点代替
                        newNode = parentOf(newNode);
                        rotateWithLeftChildToRight(newNode);
                        rotateWithRightChildToLeft(parentOf(parentOf(newNode)));
                        setColor(parentOf(newNode),ColorEnum.BLACK.getColor());
                        setColor(leftOf(parentOf(newNode)),ColorEnum.RED.getColor());
                    }*/
                }
            }
        }
        root.setColor(ColorEnum.BLACK.getColor());
    }

    private void rotateWithRightChildToLeft(RedBlackNode<T> current) {
        if (current != null) {
            RedBlackNode<T> temp = rightOf(current);
            current.setRight(temp.getLeft());
            if (leftOf(temp) != null) {
                leftOf(temp).setParent(current);
            }
            temp.setParent(current.getParent());
            if (parentOf(current) == null) {
                root = temp;
            } else if (leftOf(parentOf(current)) == current) {
                parentOf(current).setLeft(temp);
            } else {
                parentOf(current).setRight(temp);
            }
            temp.setLeft(current);
            current.setParent(temp);
        }
    }

    private void rotateWithLeftChildToRight(RedBlackNode<T> current) {
        if (current != null) {
            RedBlackNode<T> temp = leftOf(current);
            current.setLeft(temp.getRight());
            if (rightOf(temp) != null) {
                rightOf(temp).setParent(current);
            }
            temp.setParent(current.getParent());
            if (parentOf(current) == null) {
                root = temp;
            } else if (rightOf(parentOf(current)) == current) {
                parentOf(current).setRight(temp);
            } else {
                parentOf(current).setLeft(temp);
            }
            temp.setRight(current);
            current.setParent(temp);
        }
    }

    public void remove(T value) {
        removeValue(value);
    }

    private void removeValue(T value) {
        RedBlackNode<T> remove = getNodeByValue(value);
        if (remove == null) {
            return;
        }
        if (leftOf(remove) != null && rightOf(remove) != null) {
            RedBlackNode<T> findMinThanRemove = rightOf(remove);
            while (leftOf(findMinThanRemove) != null) {
                findMinThanRemove = leftOf(findMinThanRemove);
            }
            remove.setValue(findMinThanRemove.getValue());
            remove = findMinThanRemove;
        }
        RedBlackNode<T> replaceNode = leftOf(remove) != null ? leftOf(remove) : rightOf(remove);
        if (replaceNode != null) {
            replaceNode.setParent(remove.getParent());
            if (parentOf(remove) == null) {
                root = replaceNode;
            } else if (leftOf(parentOf(remove)) == remove) {
                parentOf(remove).setLeft(replaceNode);
            } else {
                parentOf(remove).setRight(replaceNode);
            }

            remove.setRight(null);
            remove.setLeft(null);
            remove.setParent(null);

            // 删除父节点后通过旋转 更换颜色
            if (colorOf(remove) == ColorEnum.BLACK.getColor()) {
                handRemove(replaceNode);
            }

        } else if (parentOf(remove) == null) {
            root = null;
        } else {
            // 将remove节点转变成红色节点后删除，先删除会导致找不到父节点
            if (colorOf(remove) == ColorEnum.BLACK.getColor()) {
                handRemove(remove);
            }
            // 删除remove  则把父节点的指向变化
            if (parentOf(remove) != null) {
                if (leftOf(parentOf(remove)) == remove) {
                    parentOf(remove).setLeft(null);
                } else {
                    parentOf(remove).setRight(null);
                }
                remove.setParent(null);
            }
        }
        size--;
//        // 代替节点为左节点
//        if(leftOf(parentOf(remove))==remove){
//            if(colorOf(remove)==ColorEnum.RED.getColor()){
//                parentOf(remove).setLeft(null);
//            }
//        }
//        // 代替节点为右节点
//        else{
//            if(colorOf(remove)==ColorEnum.RED.getColor()){
//                parentOf(remove).setRight(null);
//            }
//        }
//        deleteHand(remove);
    }

    private void handRemove(RedBlackNode<T> replaceNode) {
        while (replaceNode != null && ColorEnum.BLACK.getColor() == colorOf(replaceNode)) {
            if (replaceNode == leftOf(parentOf(replaceNode))){
                RedBlackNode<T> brother = rightOf(parentOf(replaceNode));
                // 将兄弟节点变成黑色
                if(colorOf(brother)==ColorEnum.RED.getColor()){
                    setColor(brother, ColorEnum.BLACK.getColor());
                    setColor(parentOf(replaceNode), ColorEnum.RED.getColor());
                    rotateWithRightChildToLeft(parentOf(replaceNode));
                    brother = rightOf(parentOf(replaceNode));
                }

                if(colorOf(leftOf(brother))==ColorEnum.BLACK.getColor() &&
                    colorOf(rightOf(brother))== ColorEnum.BLACK.getColor()){
                    // 因为左节点将减少一个，所以将右节点也减少一个，再重新判断
                    setColor(brother, ColorEnum.RED.getColor());
                    replaceNode = parentOf(replaceNode);
                }else{
                    if(colorOf(leftOf(brother))==ColorEnum.RED.getColor()){
                        setColor(brother, ColorEnum.RED.getColor());
                        setColor(leftOf(brother), ColorEnum.BLACK.getColor());
                        rotateWithLeftChildToRight(brother);
                        brother = rightOf(parentOf(replaceNode));
                    }
                    setColor(brother, parentOf(replaceNode).getColor());
                    setColor(parentOf(replaceNode), ColorEnum.BLACK.getColor());
                    setColor(rightOf(brother), ColorEnum.BLACK.getColor());
                    rotateWithRightChildToLeft(parentOf(replaceNode));
                    replaceNode = root;
                }
            } else{
                RedBlackNode<T> brother = leftOf(parentOf(replaceNode));
                if(colorOf(brother) == ColorEnum.RED.getColor()){
                    setColor(brother, ColorEnum.BLACK.getColor());
                    setColor(parentOf(replaceNode), ColorEnum.RED.getColor());
                    rotateWithLeftChildToRight(parentOf(replaceNode));
                    brother = leftOf(parentOf(replaceNode));
                }

                if(colorOf(leftOf(brother))==ColorEnum.BLACK.getColor()&&
                    colorOf(rightOf(brother))==ColorEnum.BLACK.getColor()){
                    setColor(brother, ColorEnum.RED.getColor());
                    replaceNode = parentOf(replaceNode);
                }else{
                    if(colorOf(rightOf(brother)) == ColorEnum.RED.getColor()){
                        setColor(brother, ColorEnum.RED.getColor());
                        setColor(rightOf(brother), ColorEnum.BLACK.getColor());
                        rotateWithRightChildToLeft(brother);
                        brother = leftOf(parentOf(replaceNode));
                    }

                    setColor(brother, parentOf(replaceNode).getColor());
                    setColor(parentOf(replaceNode), ColorEnum.BLACK.getColor());
                    setColor(leftOf(brother), ColorEnum.BLACK.getColor());
                    rotateWithLeftChildToRight(parentOf(replaceNode));
                    replaceNode = root;
                }
            }
            // 替换节点为父节点的左节点
            /*if (x == leftOf(parentOf(x))) {

                TreeMap.Entry<K,V> sib = rightOf(parentOf(x));
                // 兄弟节点为红色
                if (colorOf(sib) == RED) {
                    // 将兄弟节点转换为黑色
                    setColor(sib, BLACK);
                    // 将父节点转化为红色
                    setColor(parentOf(x), RED);
                    // 左旋
                    rotateLeft(parentOf(x));
                    // 修改兄弟节点
                    sib = rightOf(parentOf(x));
                }
                // 兄弟节点的两个子节点为黑色
                if (colorOf(leftOf(sib))  == BLACK &&
                        colorOf(rightOf(sib)) == BLACK) {
                    // 兄弟节点设置为红色
                    setColor(sib, RED);
                    // x的父节点设置为新的替换节点
                    x = parentOf(x);
                }
                // 至少有一个节点不为黑色
                else {
                    // 如果兄弟节点的右节点为黑色，左节点为红色
                    if (colorOf(rightOf(sib)) == BLACK) {
                        // 兄弟的左节点设置为黑色
                        setColor(leftOf(sib), BLACK);
                        // 兄弟节点设置为红色
                        setColor(sib, RED);
                        // 右旋
                        rotateRight(sib);
                        // 重新计算兄弟节点
                        sib = rightOf(parentOf(x));
                    }
                    // 兄弟节点设置为父节点的颜色
                    setColor(sib, colorOf(parentOf(x)));
                    // 将父节点设置为黑色
                    setColor(parentOf(x), BLACK);
                    // 将兄弟节点的右节点设置为黑色
                    setColor(rightOf(sib), BLACK);
                    // 左旋
                    rotateLeft(parentOf(x));
                    // 结束，跳出循环
                    x = root;
                }
            }*/
        }
        setColor(replaceNode, ColorEnum.BLACK.getColor());
    }

    public void printfTree() {
        printfTree(root);
    }

    public void printfTree2() {
        printfTree2(root);
    }

    private void printfTree(RedBlackNode<T> root) {
        if (root != null) {
            printfTree(root.getLeft());
            System.out.print(root.getValue() + " " + root.getColor() + ",");
            printfTree(root.getRight());
        }
    }

    private void printfTree2(RedBlackNode<T> root) {
        if (root != null) {
            printfTree2(root.getLeft());
            printfTree2(root.getRight());
            System.out.print(root.getValue() + " " + root.getColor() + ",");
        }
    }


    public static void main(String[] args) {
        RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
        redBlackTree.insert(10);
        redBlackTree.insert(70);
        redBlackTree.insert(32);
        redBlackTree.insert(34);
        redBlackTree.insert(13);
        redBlackTree.insert(56);
        redBlackTree.insert(32);
        redBlackTree.insert(56);
        redBlackTree.insert(21);
        redBlackTree.insert(3);
        redBlackTree.insert(62);
        redBlackTree.insert(4);
        System.out.println(redBlackTree.size());
        redBlackTree.printfTree();
        System.out.println();
        redBlackTree.printfTree2();

        redBlackTree.remove(62);
        redBlackTree.remove(70);
        redBlackTree.remove(56);
        System.out.println(redBlackTree.size());
        redBlackTree.printfTree();
        System.out.println();
        redBlackTree.printfTree2();

    }
}