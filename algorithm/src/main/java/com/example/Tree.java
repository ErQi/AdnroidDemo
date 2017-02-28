package com.example;

/**
 * 功　　能：树的存储结构 <br>
 * 作　　者：ErQi <br>
 * 创建日期：2017-2-27  17:27 <br>
 */
public class Tree {


    { // 树的双亲表示法

        // 在每个结点中,都用一个指示器指示其父结点在数组中的位置

        class PTNode {
            Object data; // 数据
            int parent; // 双亲位置
        }

        class PTree {
            PTNode[] nodes; // 结点数组
            int r; // 根结点
            int n; // 结点数
        }
    }


    { // 树的兄弟表示法

        // 每个结点中,用单链表保存孩子的位置

        class CTNode {
            int child; // 孩子的位置
            CTNode next; // 下一个孩子位置
        }

        class CTBox {
            Object data; // 数据
            CTNode firstchild; // 孩子结点信息
        }

        class CTree {
            CTBox[] nodes; // j结点数组
            int r; // 根结点
            int n; // 结点数
        }
    }

    { // 孩子兄弟表示法

        // 每个结点中,设置两个指针,只想该结点的第一个孩子和此结点的有兄弟

        class CSNode{
            Object data; // 数据
            CSNode firstchild; // 第一个孩子结点
            CSNode rightsib; // 右边兄弟结点
        }
    }

    { // 标准的二叉树兑现
        class BiTNode{
            Object data;
            BiTNode lchild;
            BiTNode rchild;
        }
    }
}
