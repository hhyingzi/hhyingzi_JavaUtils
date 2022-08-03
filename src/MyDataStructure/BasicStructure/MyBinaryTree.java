package MyDataStructure.BasicStructure;

import java.util.*;

/**
 * 二叉树基本数据结构
 * 节点的数组表现形式为 arr[0, length-1]
 * 节点k的父节点：arr[(k-1)/2]
 * 节点k的左右孩子：arr[k*2+1], arr[k*2+2]
 */
public class MyBinaryTree {
    public BinNode root;
    public int size;

    public class BinNode {
        public int data;
        public BinNode leftChild;
        public BinNode rightChild;
    }
    public ArrayList<Integer> resultNodeArray = new ArrayList<>();

    public void visit(BinNode node) {
        //System.out.println(node.data);
        resultNodeArray.add(node.data);
    }

    //传入一个数组，根据层次结构，建立完全二叉树（一定是完全二叉树，否则不能通过层次结构确定唯一结构）
    public void initFromLevel(int[] arr) {
        if (arr == null || arr.length == 0) return;
        ArrayList<BinNode> nodeList = new ArrayList<>();
        //把每个元素都转化成节点
        for (int i = 0; i < arr.length; i++) {
            BinNode temp = new BinNode();
            temp.data = arr[i];
            nodeList.add(temp);
        }
        //给每个父节点绑定左右孩子
        for (int j = 0; j <= arr.length / 2 - 1; j++) {
            if (j * 2 + 1 < arr.length) nodeList.get(j).leftChild = nodeList.get(j * 2 + 1);
            if (j * 2 + 2 < arr.length) nodeList.get(j).rightChild = nodeList.get(j * 2 + 2);
        }
        this.root = nodeList.get(0);
    }

    //根据先序+中序 建立这棵二叉树
    public void initFromPreIn(int[] arrPre, int[] arrIn) {
        BinNode node = new BinNode();

    }

    //根据中序+后序，建立这棵二叉树
    public void initFromInPost(int[] arrIn, int[] arrPost) {
    }

    //层次遍历迭代(广度优先遍历)
    public void leverOrder(BinNode node) {
        if (node == null) return;
        LinkedList<BinNode> queue = new LinkedList<>();
        queue.addLast(node); //根节点入队
        while (!queue.isEmpty()) {
            node = queue.removeFirst();  //取出队首
            visit(node);
            if(node.leftChild != null) queue.addLast(node.leftChild);  //左孩子入队尾
            if(node.rightChild != null) queue.addLast(node.rightChild);  //右孩子入队尾
        }
    }

    //先序遍历（递归版）
    public void preOrderRecur(BinNode node) {
        if (node == null) return;
        visit(node);
        preOrderRecur(node.leftChild);
        preOrderRecur(node.rightChild);
    }

    //中序遍历（递归版）
    public void inOrderRecur(BinNode node) {
        if (node == null) return;
        inOrderRecur(node.leftChild);
        visit(node);
        inOrderRecur(node.rightChild);
    }

    //后序遍历（递归版）
    public void postOrderRecur(BinNode node) {
        if (node == null) return;
        postOrderRecur(node.leftChild);
        postOrderRecur(node.rightChild);
        visit(node);
    }

    //先序遍历（迭代版）：出栈，访问该节点，右孩子入栈，左孩子入栈
    public void preOrderIter(BinNode node) {
        if (node == null) return;
        LinkedList<BinNode> stack = new LinkedList<>();
        while (true) {
            visit(node);
            if (node.rightChild != null) stack.push(node.rightChild);  //右孩子先入栈后出栈
            if (node.leftChild != null) stack.push(node.leftChild);  //左孩子入栈
            if(!stack.isEmpty()) node = stack.pop();
            else return;
        }
    }

    //中序遍历（迭代版）
    public void inOrderIter(BinNode node) {
        if (node == null) return;
        LinkedList<BinNode> stack = new LinkedList<>();
        while (true) {
            //持续向左孩子走，找到最左孩子，然后访问。
            while (node.leftChild != null) {
                stack.push(node);
                node = node.leftChild;
            }
            visit(node);
            //出栈父节点然后访问。
            if(!stack.isEmpty()){
                node = stack.pop();
                visit(node);
            }
            //前往右孩子一步，开始下轮循环。没有右孩子则彻底结束。
            if(node.rightChild == null) return;
            else node = node.rightChild;
        }
    }

    //后序遍历（迭代版）
    public void postOrderIter(BinNode node){
        if(node == null) return;
        LinkedList<BinNode> stack = new LinkedList<>();
        BinNode prevNode = new BinNode();  //标记前驱节点，用于判断是否访问过当前节点的右孩子。
        while(true){
            //自己持续向左走，直到自己变成最左的孩子（可能自己还会有右孩子）
            while(node.leftChild != null && prevNode != node.leftChild && prevNode != node.rightChild){
                stack.push(node);
                node = node.leftChild;
            }
            //没有左孩子了，向右孩子走一步
            if(node.rightChild != null && node.rightChild != prevNode){
                stack.push(node);
                node = node.rightChild;
                //若有左孩子，则继续往左走去
                if(node.leftChild != null && prevNode != node.leftChild) continue;
            }
            //左右孩子都不可用了，访问自己，然后弹出栈顶元素（自己的父节点或右兄弟）
            visit(node);
            prevNode = node;
            if(stack.isEmpty()) return;
            else node = stack.pop();
        }
    }

    //深度优先遍历（先序遍历迭代版）。若要改为搜索，只需加一个 boolean 类型的成员变量 ifSearched ,在 visit() 中判断是否已经找到。
    public void dfs(int value) {
        preOrderIter(this.root);
    }
    //广度优先搜索（层次遍历迭代版）。若要改为搜索，只需加一个 boolean 类型的成员变量 ifSearched ,在 visit() 中判断是否已经找到。
    public void bfs(int value) {
        leverOrder(this.root);
    }

    public void cleanResultArray(){
        this.resultNodeArray.clear();
    }

    //根据满二叉树的节点数组做图
    public void drawTreeArray() {
        cleanResultArray();  //清除结果数组
        leverOrder(this.root);  //进行一次层次遍历，用结果做图
        int index = 0;
        //做图
        int maxRow = (int) Math.floor(Math.log(resultNodeArray.size()) / Math.log(2) + 1); //log(e)(length) / log(e)(2) = log(2)(length)
        outloop:
        for (int row = 1; row <= maxRow; row++) {
            boolean isFirstCol = true;
            for (int col = 1; col <= Math.pow(2, row - 1); col++) {
                if (index > resultNodeArray.size() - 1) break outloop;
                if(isFirstCol) System.out.format("%"+ 4 * (int)Math.pow(2, maxRow-row) / 2 +"d", resultNodeArray.get(index++));
                else System.out.format("%"+ 4 * (int)Math.pow(2, maxRow-row) +"d", resultNodeArray.get(index++));
                isFirstCol = false;
            }
            System.out.println();
        }
    }

    //根据节点做二叉树图
    public void drawTreeNode() {

    }

    public static Integer[] testBinaryTree(int[] arr) {
        System.out.println("==========");
        System.out.println("原始数据： " + Arrays.toString(arr));
        MyBinaryTree myTree = new MyBinaryTree();

        //使用层次结构初始化完全二叉树
        myTree.initFromLevel(arr);

        //画出二叉树图案
        myTree.drawTreeArray();
        myTree.cleanResultArray();

        //返回遍历结果
        //层次遍历
        myTree.leverOrder(myTree.root);
        System.out.println("层次遍历：" + Arrays.toString(myTree.resultNodeArray.toArray(new Integer[arr.length])));
        myTree.cleanResultArray();

        //先序遍历
        myTree.preOrderRecur(myTree.root);  //递归版
//        myTree.preOrderIter(myTree.root);  //迭代版
        System.out.println("先序遍历：" + Arrays.toString(myTree.resultNodeArray.toArray(new Integer[arr.length])));
        myTree.cleanResultArray();
        //中序遍历
        myTree.inOrderRecur(myTree.root);  //递归版
//        myTree.inOrderIter(myTree.root);  //迭代版
        System.out.println("中序遍历：" + Arrays.toString(myTree.resultNodeArray.toArray(new Integer[arr.length])));
        myTree.cleanResultArray();
        //后序遍历
        myTree.postOrderRecur(myTree.root);  //递归版
//        myTree.postOrderIter(myTree.root);  //迭代版
        System.out.println("后序遍历：" + Arrays.toString(myTree.resultNodeArray.toArray(new Integer[arr.length])));
        myTree.cleanResultArray();

        return myTree.resultNodeArray.toArray(new Integer[arr.length]);
    }

    public static void main(String[] args) {
        int[] arr = new int[7];
        for (int i=0; i<arr.length; i++){
            arr[i] = i+1;
        }
        Integer[] resultArr = testBinaryTree(arr);
    }
}