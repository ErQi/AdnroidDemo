package com.example;

import org.junit.Test;

/**
 * 功　　能：线索二叉树的遍历 <br>
 * 作　　者：ErQi <br>
 * 创建日期：2017-2-28  22:34 <br>
 */

public class ThrTest {

    class BinThrNode {
        String data; // 数据
        BinThrNode lchild; // 左指针
        BinThrNode rchild; // 右指针
        boolean ltag; // 左标记
        boolean rtag; // 右标记

        public BinThrNode(String data) {
            this.data = data;
        }
    }

    BinThrNode pre; // 全局变量,标记上一个结点

    void preThreading(BinThrNode tree) {
        if (null == tree) {
            return;
        }
        if (null == tree.lchild) { // 当前节点没有左子树,替换为前驱
            tree.ltag = true; // 修改标记
            tree.lchild = pre; // 替换前驱
        }
        if (null != pre && null == pre.rchild) { // 上一节点没有右子树
            pre.rtag = true; // 修改标记
            pre.rchild = tree; // 替换后继
        }
        pre = tree; // 更新指针
        if (!tree.ltag) { // 判断是否是左子树,避免进入死循环
            preThreading(tree.lchild);
        }
        if (!tree.rtag) {
            preThreading(tree.rchild);
        }
    }

    // 前序遍历
    public void preOrderTraverse_Thr(BinThrNode tree) {
        if (null == tree) {
            return;
        }
        while (null != tree) {
            System.err.print(tree.data);
            while (!tree.ltag) {
                tree = tree.lchild;
                System.err.print(tree.data);
            }
            tree = tree.rchild;
        }
    }

    // 中序二叉树线索化
    void inThreading(BinThrNode tree) {
        if (null == tree) {
            return;
        }
        inThreading(tree.lchild); // 递归找到左子树
        if (null == tree.lchild) { // 当前节点没有左子树,替换为前驱
            tree.ltag = true; // 修改标记
            tree.lchild = pre; // 替换前驱
        }
        if (null != pre && null == pre.rchild) { // 上一节点没有右子树
            pre.rtag = true; // 修改标记
            pre.rchild = tree; // 替换后继
        }
        pre = tree; // 更新指针
        inThreading(tree.rchild);
    }

    // 中序遍历
    public void inOrderTraverse_Thr(BinThrNode tree) {
        if (null == tree) {
            return;
        }
        while (null != tree.lchild && !tree.ltag) { // 先找到左子树的起始结点
            tree = tree.lchild;
        }
        while (null != tree) {
            System.err.print(tree.data); // 对结点操作
            if (tree.rtag) { // 有后继直接指向后继
                tree = tree.rchild;
            } else {
                tree = tree.rchild; // 非后继就是右子树,
                if (null != tree) {
                    while (!tree.ltag) { // 找到右子树下左子树的结点
                        tree = tree.lchild;
                    }
                }
            }
        }
    }

    // 后序二叉树线索化
    void postThreading(BinThrNode tree) {
        if (null == tree) {
            return;
        }
        postThreading(tree.lchild);
        postThreading(tree.rchild);
        if (null == tree.lchild) { // 当前节点没有左子树,替换为前驱
            tree.ltag = true; // 修改标记
            tree.lchild = pre; // 替换前驱
        }
        if (null != pre && null == pre.rchild) { // 上一节点没有右子树
            pre.rtag = true; // 修改标记
            pre.rchild = tree; // 替换后继
        }
        pre = tree; // 更新指针
    }

    // 后序遍历
    public void postOrderTraverse_Thr(BinThrNode tree) {
        if (null == tree) {
            return;
        }
        while (true) {
            while (null != tree.lchild && !tree.ltag) { // 先找到左子树的最终结点
                tree = tree.lchild;
            }
            if (null != tree.rchild && !tree.rtag) { // 若最终结点有右子树,则换到右子树继续
                tree = tree.rchild;
            }else { // 到叶节点了,也就是遍历的第一个,跳出循环.
                break;
            }
        }
        while (null != tree) {
            System.err.print(tree.data);
            if (tree.rtag) { // 有后继直接指向后继
                tree = tree.rchild;
            } else {
                tree = tree.rchild; // 非后继就是右子树,
                if (null != tree) {
                    while (!tree.ltag) { // 找到右子树下左子树的结点
                        tree = tree.lchild;
                    }
                }
            }
        }
    }
    @Test
    public void test() {
        String s = "ABCDEFGH###I##J#K";
        BinThrNode tree = geBiTree(s, 0);
//        preThreading(tree);
//        preOrderTraverse_Thr(tree);
//        inThreading(tree);
//        inOrderTraverse_Thr(tree);
        postThreading(tree);
//        postOrderTraverse_Thr(tree);
    }

    public BinThrNode geBiTree(String s, int index) {
        if (index >= s.length()) {
            return null;
        }
        if ("#".equals(s.substring(index, index + 1))) {
            return null;
        }
        BinThrNode tree = new BinThrNode(s.substring(index, index + 1));
        tree.lchild = geBiTree(s, 2 * index + 1);
        tree.rchild = geBiTree(s, 2 * index + 2);
        return tree;
    }
}
