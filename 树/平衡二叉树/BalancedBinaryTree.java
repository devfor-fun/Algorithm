package com.company;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * <p></p>
 *
 * @author wangsizheng
 * @version V1.0.0
 * @since V1.0.0
 */
public class BalancedBinaryTree {


    public static void main(String[] args) {
        BalancedBinaryTree balancedBinaryTree = new BalancedBinaryTree();
        balancedBinaryTree.insert(10);
        balancedBinaryTree.insert(12);
        balancedBinaryTree.insert(14);
        balancedBinaryTree.insert(18);
        balancedBinaryTree.insert(13);
        balancedBinaryTree.insert(15);
        List<Integer> cur = balancedBinaryTree.prePrintln();
        for (Integer i : cur) {
            System.out.print(i + " ");
        }
        System.out.println();
        cur = balancedBinaryTree.midPrintln();
        for (Integer i : cur) {
            System.out.print(i + " ");
        }
        System.out.println();
        balancedBinaryTree.insert(8);
        balancedBinaryTree.insert(4);
        balancedBinaryTree.insert(6);
        balancedBinaryTree.insert(7);
        balancedBinaryTree.insert(9);
        balancedBinaryTree.insert(16);
        balancedBinaryTree.insert(5);
        cur = balancedBinaryTree.prePrintln();
        for (Integer i : cur) {
            System.out.print(i + " ");
        }
        System.out.println();
        cur = balancedBinaryTree.midPrintln();
        for (Integer i : cur) {
            System.out.print(i + " ");
        }
        System.out.println();
        balancedBinaryTree.remove(12);
        balancedBinaryTree.remove(7);
        balancedBinaryTree.remove(16);
        balancedBinaryTree.remove(18);
        balancedBinaryTree.remove(14);
        cur = balancedBinaryTree.prePrintln();
        for (Integer i : cur) {
            System.out.print(i + " ");
        }
        System.out.println();
        cur = balancedBinaryTree.midPrintln();
        for (Integer i : cur) {
            System.out.print(i + " ");
        }
    }


    private static final int ALLOWED_IMBALANCE = 1;

    BinaryTreeNode root;

    public BalancedBinaryTree () {
        this(null);
    }

    public BalancedBinaryTree (int value) {
        this(new BinaryTreeNode(value));
    }

    public BalancedBinaryTree (BinaryTreeNode root) {
        this.root = root;
    }

    /**
     * 往根节点后添加
     * @param value
     * @return
     */
    public boolean insert(int value) {
        if (root == null) {
            root = new BinaryTreeNode(value);
            return true;
        }
        root = insert(root, value);
        return true;
    }

    /**
     * 往某个节点后添加
     * @param curTreeNode
     * @param value
     * @return
     */
    private BinaryTreeNode insert(BinaryTreeNode curTreeNode, int value) {
        if (curTreeNode == null) {
            return new BinaryTreeNode(value);
        }
        if (curTreeNode.value > value) {
            curTreeNode.left = insert(curTreeNode.left, value);
        } else {
            curTreeNode.right = insert(curTreeNode.right, value);
        }
        return balance(curTreeNode);
    }

    /**
     * 平衡二叉搜索树
     * @param curTreeNode
     * @return
     */
    private BinaryTreeNode balance(BinaryTreeNode curTreeNode) {
        if (curTreeNode == null) {
            return null;
        }
        if (height(curTreeNode.left) - height(curTreeNode.right) >  ALLOWED_IMBALANCE) {
            if (height(curTreeNode.left.left) >= height(curTreeNode.left.right)) {
                curTreeNode = rotateLeftToRight(curTreeNode);
            } else {
                curTreeNode = doubleRotateLeftRight(curTreeNode);
            }
        } else if (height(curTreeNode.right) - height(curTreeNode.left) > ALLOWED_IMBALANCE) {
            if (height(curTreeNode.right.right) >= height(curTreeNode.right.left)) {
                curTreeNode = rotateRightToLeft(curTreeNode);
            } else {
                curTreeNode = doubleRotateRightLeft(curTreeNode);
            }
        }
        curTreeNode.height = Math.max(height(curTreeNode.left), height(curTreeNode.right)) + 1;
        return curTreeNode;
    }

    /**
     * 获取某个节点的高度
     * @param treeNode
     * @return
     */
    private int height(BinaryTreeNode treeNode) {
        return treeNode == null ? -1 : treeNode.height;
    }

    /**
     * 以某个节点为中心进行右旋
     * @param curTreeNode
     * @return
     */
    private BinaryTreeNode rotateLeftToRight(BinaryTreeNode curTreeNode) {
        BinaryTreeNode left = curTreeNode.left;
        curTreeNode.left = left.right;
        left.right = curTreeNode;
        curTreeNode.height = Math.max(height(curTreeNode.left), height(curTreeNode.right)) + 1;
        left.height = Math.max(height(left.left), curTreeNode.height) + 1;
        return left;
    }

    /**
     * 左右情况下，进行两次旋转
     * @param curTreeNode
     * @return
     */
    private BinaryTreeNode doubleRotateLeftRight(BinaryTreeNode curTreeNode) {
        // 以左节点为中心进行左旋，变成左左
        curTreeNode.left = rotateRightToLeft(curTreeNode.left);
        // 进行右旋
        return rotateLeftToRight(curTreeNode);
    }

    /**
     * 以某个节点为中心进行左旋
     * @param curTreeNode
     * @return
     */
    private BinaryTreeNode rotateRightToLeft(BinaryTreeNode curTreeNode) {
        BinaryTreeNode right = curTreeNode.right;
        curTreeNode.right = right.left;
        right.left = curTreeNode;
        curTreeNode.height = Math.max(height(curTreeNode.left), height(curTreeNode.right)) + 1;
        right.height = Math.max(height(right.right), curTreeNode.height) + 1;
        return right;
    }

    /**
     * 右左情况下，进行两次旋转
     * @param curTreeNode
     * @return
     */
    private BinaryTreeNode doubleRotateRightLeft(BinaryTreeNode curTreeNode) {
        // 以右节点为中心进行右旋，变成右右
        curTreeNode.right = rotateLeftToRight(curTreeNode.right);
        // 进行左旋
        return rotateRightToLeft(curTreeNode);
    }

    /**
     * 从根节点移除某个节点
     * @param value
     * @return
     */
    public boolean remove(int value) {
        if (root == null) {
            return true;
        }
        root = remove(root, value);
        return true;
    }

    /**
     * 从某个节点下移除某个节点
     * @param curTreeNode
     * @param value
     * @return
     */
    private BinaryTreeNode remove(BinaryTreeNode curTreeNode, int value) {
        if (curTreeNode == null) {
            return null;
        }
        if (curTreeNode.value > value) {
            curTreeNode.left = remove(curTreeNode.left, value);
        } else if (curTreeNode.value < value) {
            curTreeNode.right = remove(curTreeNode.right, value);
        } else if (curTreeNode.left != null && curTreeNode.right != null){
            curTreeNode.value = findMin(curTreeNode.right);
            curTreeNode.right = remove(curTreeNode.right, curTreeNode.value);
        } else {
            curTreeNode = curTreeNode.left != null ? curTreeNode.left : curTreeNode.right;
        }
        return balance(curTreeNode);
    }

    /**
     * 找到某个节点下的最小值
     * @param right
     * @return
     */
    private int findMin(BinaryTreeNode right) {
        while (right.left != null) {
            right = right.left;
        }
        return right.value;
    }

    /**
     * 根节点 前序遍历，递归版
     * @return
     */
    public List<Integer> prePrintln() {
        return prePrintln(root);
    }

    /**
     * 某个节点前序遍历，递归版
     * @param root
     * @return
     */
    private List<Integer> prePrintln(BinaryTreeNode root) {
        List<Integer> result = new ArrayList<>();
        prePrintln(root, result);
        return result;
    }

    /**
     * 深度优先搜索
     * @param root
     * @param result
     */
    private void prePrintln(BinaryTreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        result.add(root.value);
        prePrintln(root.left, result);
        prePrintln(root.right, result);
    }

    /**
     * 根节点 前序遍历  迭代版
     * @return
     */
    public List<Integer> prePrintlnIteration() {
        return prePrintlnIteration(root);
    }

    /**
     * 根节点 前序遍历  迭代版
     * @return
     */
    private List<Integer> prePrintlnIteration(BinaryTreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<BinaryTreeNode> stack = new ArrayDeque<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                result.add(root.value);
                root = root.left;
            } else {
                root = stack.pop();
                root = root.right;
            }
        }
        return result;
    }

    /**
     * 根节点 中序遍历，递归版
     * @return
     */
    public List<Integer> midPrintln() {
        return midPrintln(root);
    }

    /**
     * 某个节点 中序遍历，递归版
     * @param root
     * @return
     */
    private List<Integer> midPrintln(BinaryTreeNode root) {
        List<Integer> result = new ArrayList<>();
        midPrintln(root, result);
        return result;
    }

    /**
     * 深度优先搜索
     * @param root
     * @param result
     */
    private void midPrintln(BinaryTreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        midPrintln(root.left, result);
        result.add(root.value);
        midPrintln(root.right, result);
    }

    /**
     * 根节点 中序遍历，迭代版
     * @return
     */
    public List<Integer> midPrintlnIteration() {
        return midPrintlnIteration(root);
    }

    /**
     * 根节点 中序遍历，迭代版
     * @param root
     * @return
     */
    private List<Integer> midPrintlnIteration(BinaryTreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<BinaryTreeNode> stack = new ArrayDeque<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                result.add(root.value);
                root = root.right;
            }
        }
        return result;
    }

    /**
     * 根节点 后序遍历，递归版
     * @return
     */
    public List<Integer> postPrintln() {
        return postPrintln(root);
    }

    /**
     * 某个节点 后序遍历，递归版
     * @param root
     * @return
     */
    private List<Integer> postPrintln(BinaryTreeNode root) {
        List<Integer> result = new ArrayList<>();
        postPrintln(root, result);
        return result;
    }

    /**
     * 深度优先搜索
     * @param root
     * @param result
     */
    private void postPrintln(BinaryTreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        postPrintln(root.left, result);
        postPrintln(root.right, result);
        result.add(root.value);
    }

    /**
     * 根节点 后序遍历，迭代版
     * @return
     */
    public List<Integer> postPrintlnIteration() {
        return postPrintlnIteration(root);
    }

    /**
     * 根节点 后序遍历，迭代版
     * @param root
     * @return
     */
    private List<Integer> postPrintlnIteration(BinaryTreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<BinaryTreeNode> stack = new ArrayDeque<>();
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.push(root);
                result.add(0, root.value);
                root = root.right;
            } else {
                root = stack.pop();
                root = root.left;
            }
        }
        return result;
    }


}
class BinaryTreeNode {


    BinaryTreeNode left;

    BinaryTreeNode right;

    int value;

    int height;

    public BinaryTreeNode (int value) {
        this(value, null, null);
    }

    public BinaryTreeNode (int value, BinaryTreeNode left, BinaryTreeNode right) {
        this(value, left, right, 0);
    }

    public BinaryTreeNode (int value, BinaryTreeNode left, BinaryTreeNode right, int height) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.height = height;
    }
}