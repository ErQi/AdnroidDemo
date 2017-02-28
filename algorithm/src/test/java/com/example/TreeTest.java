package com.example;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 功　　能：进行遍历算法测试 <br>
 * 作　　者：ErQi <br>
 * 创建日期：2017-2-28  16:55 <br>
 */

public class TreeTest {
    // 标准的二叉树对象
    class BiTree {
        public String data;
        public BiTree lchild;
        public BiTree rchild;

        public BiTree(String data) {
            this.data = data;
        }
    }

    // 前序遍历
    public void preOrderTraverse(BiTree tree) {
        if (null == tree) {
            return;
        }
        System.err.print(tree.data);
        preOrderTraverse(tree.lchild);
        preOrderTraverse(tree.rchild);
    }

    // 中序遍历
    public void inOrderTraverse(BiTree tree) {
        if (null == tree) {
            return;
        }
        inOrderTraverse(tree.lchild);
        System.err.print(tree.data);
        inOrderTraverse(tree.rchild);
    }

    // 后序遍历
    public void postOrderTraverse(BiTree tree) {
        if (null == tree) {
            return;
        }
        postOrderTraverse(tree.lchild);
        postOrderTraverse(tree.rchild);
        System.err.print(tree.data);
    }

    // 层序遍历
    public void floorOrderTraverse(BiTree tree) {
        if (null == tree) {
            return;
        }
        Queue<BiTree> queue = new LinkedList<>();
        queue.add(tree);
        while (!queue.isEmpty()) {
            BiTree temp = queue.poll();
            System.out.print(temp.data);
            if (null != temp.lchild) queue.add(temp.lchild);
            if (null != temp.rchild) queue.add(temp.rchild);
        }
    }

    @Test
    public void onTest() {
//        preOrderTraverse(geBiTree());
//        inOrderTraverse(geBiTree());
//        postOrderTraverse(geBiTree());
        floorOrderTraverse(geBiTree());
    }

    public BiTree geBiTree() {
        BiTree a = new BiTree("A");
        BiTree b = new BiTree("B");
        BiTree c = new BiTree("C");
        BiTree d = new BiTree("D");
        BiTree e = new BiTree("E");
        BiTree f = new BiTree("F");
        BiTree g = new BiTree("G");
        BiTree h = new BiTree("H");
        BiTree i = new BiTree("I");
        BiTree j = new BiTree("J");
        BiTree k = new BiTree("K");
        a.lchild = b;
        a.rchild = c;
        b.lchild = d;
        b.rchild = e;
        d.lchild = h;
        h.rchild = k;
        c.lchild = f;
        c.rchild = g;
        f.lchild = i;
        g.rchild = j;
        return a;
    }
}
