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
    private boolean dataWrong = false;
    private int treeDepth; //树最大深度，用来辅助二叉树绘图，可删除

    public class BinNode {
        public int data;
        public BinNode leftChild;
        public BinNode rightChild;
        public int nodeDepth;  //节点深度，用来辅助二叉树绘图，可删除

        public BinNode(){}
        public BinNode(int data){this.data = data;}
        public BinNode(int data, BinNode leftChild, BinNode rightChild){
            this.data = data;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
    }
    //手动创建节点
    public void createNewNode(int data){
        BinNode newNode = new BinNode();
        newNode.data = data;
    }
    public void createNewNode(int data, BinNode leftChild, BinNode rightChild){
        BinNode newNode = new BinNode();
        newNode.data = data;
        newNode.leftChild = leftChild;
        newNode.rightChild = rightChild;
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
        dataWrong = false;
        //输入为空的情况下，建立空的二叉树。
        if(arrPre == null && arrIn == null) return;
        //正常建立二叉树，获取树根并赋值给类的 root 成员变量。
        else this.root = assistInitFromPreIn(arrPre, 0, arrPre.length-1, arrIn, 0, arrIn.length-1);
        //检测构造过程中是否出现错误
        if(dataWrong) System.out.println("输入数据有误，无法构建二叉树。");
    }

    //传入先序遍历输入数组 arrPrep[indexPreHead, indexPreTail]，中序遍历输入数组 arrIn(indexInHead, indexInTail]，找出父节点并实例化，然后连接上左右节点。返回该父节点。
    private BinNode assistInitFromPreIn(int[] arrPre, int indexPreHead, int indexPreTail,
                                     int[] arrIn, int indexInHead, int indexInTail){
        BinNode parentNode = new BinNode();
        //之前计算检测到错误，或输入的先序中序数组长度不同，则无法构建子树，直接返回
        if(dataWrong || indexPreTail - indexPreHead != indexInTail - indexInHead){
            dataWrong = true;
            return null;
        }
        //在中序数组中定位父节点的位置 parentIndexIn
        parentNode.data = arrPre[indexPreHead];
        int parentIndexIn = -1;
        for(int i=indexInHead; i<=indexInTail; i++){
            if(arrIn[i] == parentNode.data){
                parentIndexIn = i;
                break;
            }
        }
        if(parentIndexIn == -1){
            System.out.println("先序遍历中的某个父节点，不在中序遍历中的正确位置上，计算失败！");
            dataWrong = true;
            return null;
        }
        //计算左右子树的边界。
        int preLeftHead, preLeftTail, preRightHead, preRightTail;  //先序数组的：左子树起点、末尾，右子树起点、末尾。
        int inLeftHead, inLeftTail, inRightHead, inRightTail;  //中序数组的：左子树起点、末尾，右子树起点、末尾。
        int leftLength = parentIndexIn - indexInHead;  //左子树长度 = 中序数组父节点左边的元素数量
        //计算先序数组左右子树，arrPre[0] 是父节点
        preLeftHead = indexPreHead + 1;  //先序：左子树起点
        preLeftTail = preLeftHead + leftLength - 1;  //先序：左子树终点
        preRightHead = preLeftTail + 1; //先序：右子树起点
        preRightTail = indexPreTail;  //先序：右子树终点
        //计算中序数组左右子树，arrIn[parentIndexIn] 是父节点
        inLeftHead = indexInHead;  //中序：左子树起点
        inLeftTail = parentIndexIn - 1;  //中序：左子树终点
        inRightHead = parentIndexIn + 1;  //中序：右子树起点
        inRightTail = indexInTail;  //中序：右子树终点
        //如果有左子树，则计算并挂载到父节点下
        if(parentIndexIn > indexInHead){  //中序父节点的左边，存在其他元素
            parentNode.leftChild = assistInitFromPreIn(arrPre, preLeftHead, preLeftTail, arrIn, inLeftHead, inLeftTail);  //计算并挂载左子树
        }
        //如果有右子树，则计算并挂载到父节点下
        if(parentIndexIn < indexInTail){  //中序父节点的右边，存在其他元素
            parentNode.rightChild = assistInitFromPreIn(arrPre, preRightHead, preRightTail, arrIn, inRightHead, inRightTail);  //计算并挂载右子树
        }
        return parentNode;
    }

    //根据中序+后序，建立这棵二叉树
    public void initFromInPost(int[] arrIn, int[] arrPost) {
        dataWrong = false;
        //输入为空的情况下，建立空的二叉树。
        if(arrIn == null && arrPost == null) return;
        //正常建立二叉树，获取树根并赋值给类的 root 成员变量。
        else this.root = assistInitFromInPost(arrIn, 0, arrIn.length-1, arrPost, 0, arrPost.length-1);
        //检测构造过程中是否出现错误
        if(dataWrong) System.out.println("输入数据有误，无法构建二叉树。");
    }

    //传入中序遍历输入数组 arrIn[indexInHead, indexInTail]，后序遍历输入数组 arrPost(indexPostHead, indexPostTail]，找出父节点并实例化，然后连接上左右节点。返回该父节点。
    private BinNode assistInitFromInPost(int[] arrIn, int indexInHead, int indexInTail,
                                        int[] arrPost, int indexPostHead, int indexPostTail){
        BinNode parentNode = new BinNode();
        //之前计算检测到错误，或输入的先序中序数组长度不同，则无法构建子树，直接返回
        if(dataWrong || indexInTail - indexInHead != indexPostTail - indexPostHead){
            dataWrong = true;
            return null;
        }
        //在中序数组中定位父节点的位置 parentIndexIn
        parentNode.data = arrPost[indexPostTail];
        int parentIndexIn = -1;
        for(int i=indexInHead; i<=indexInTail; i++){
            if(arrIn[i] == parentNode.data){
                parentIndexIn = i;
                break;
            }
        }
        if(parentIndexIn == -1){
            System.out.println("后序遍历中的某个父节点，不在中序遍历中的正确位置上，计算失败！");
            dataWrong = true;
            return null;
        }
        //计算左右子树的边界。
        int inLeftHead, inLeftTail, inRightHead, inRightTail;  //中序数组的：左子树起点、末尾，右子树起点、末尾。
        int postLeftHead, postLeftTail, postRightHead, postRightTail;  //后序数组的：左子树起点、末尾，右子树起点、末尾。
        int leftLength = parentIndexIn - indexInHead;  //左子树长度 = 中序数组父节点左边的元素数量
        //计算中序数组左右子树，arrIn[parentIndexIn] 是父节点
        inLeftHead = indexInHead;  //中序：左子树起点
        inLeftTail = parentIndexIn - 1;  //中序：左子树终点
        inRightHead = parentIndexIn + 1;  //中序：右子树起点
        inRightTail = indexInTail;  //中序：右子树终点
        //计算后序数组左右子树，后序数组最后一个元素 arrPost[indexPostTail] 是父节点
        postLeftHead = indexPostHead;  //后序：左子树起点
        postLeftTail = postLeftHead + leftLength - 1;  //后序：左子树终点
        postRightHead = postLeftTail + 1; //后序：右子树起点
        postRightTail = indexPostTail - 1;  //后序：右子树终点
        //如果有左子树，则计算并挂载到父节点下
        if(parentIndexIn > indexInHead){  //中序父节点的左边，存在其他元素
            parentNode.leftChild = assistInitFromInPost(arrIn, inLeftHead, inLeftTail, arrPost, postLeftHead, postLeftTail);  //计算并挂载左子树
        }
        //如果有右子树，则计算并挂载到父节点下
        if(parentIndexIn < indexInTail){  //中序父节点的右边，存在其他元素
            parentNode.rightChild = assistInitFromInPost(arrIn, inRightHead, inRightTail, arrPost, postRightHead, postRightTail);  //计算并挂载右子树
        }
        return parentNode;
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

    public Integer[] printTravelResult() {
        //返回遍历结果
        //层次遍历
        leverOrder(this.root);
        System.out.println("层次遍历：" + Arrays.toString(resultNodeArray.toArray(new Integer[resultNodeArray.size()])));
        cleanResultArray();

        //先序遍历
        preOrderRecur(this.root);  //递归版
//        preOrderIter(myTree.root);  //迭代版
        System.out.println("先序遍历：" + Arrays.toString(resultNodeArray.toArray(new Integer[resultNodeArray.size()])));
        cleanResultArray();
        //中序遍历
        inOrderRecur(this.root);  //递归版
//        myTree.inOrderIter(myTree.root);  //迭代版
        System.out.println("中序遍历：" + Arrays.toString(resultNodeArray.toArray(new Integer[resultNodeArray.size()])));
        cleanResultArray();
        //后序遍历
        postOrderRecur(this.root);  //递归版
//        myTree.postOrderIter(myTree.root);  //迭代版
        System.out.println("后序遍历：" + Arrays.toString(resultNodeArray.toArray(new Integer[resultNodeArray.size()])));
        cleanResultArray();

        return resultNodeArray.toArray(new Integer[resultNodeArray.size()]);
    }

    //根据完全二叉树的节点数组做图
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

    //计算树最大深度，进行一次先序遍历，给每个节点赋值深度，并记录树的最大深度。初始深度为1。
    private void calTreeDepth(BinNode node, int nodeDepth){
        if (node == null) return;
        node.nodeDepth = nodeDepth;
        if(nodeDepth > this.treeDepth) treeDepth = nodeDepth;
        if(node.leftChild != null){
            calTreeDepth(node.leftChild, nodeDepth + 1);
        }
        if(node.rightChild != null){
            calTreeDepth(node.rightChild, nodeDepth + 1);
        }
    }

    //层次遍历绘图(用 -99 作为空节点)
    private void leverOrderDraw(BinNode node) {
        if (node == null) {
            return;
        }
        calTreeDepth(root, 1);  //计算出整棵树的深度和每个节点的深度
        LinkedList<BinNode> queue = new LinkedList<>();
        queue.addLast(node); //根节点入队
        while (!queue.isEmpty()) {
            node = queue.removeFirst();  //取出队首
            visit(node);
            if(node.leftChild != null) queue.addLast(node.leftChild);  //左孩子入队尾
            else if(node.nodeDepth < this.treeDepth) {  //空孩子只要不是树的最后一层，都创建一个 -99 的节点。
                BinNode nullNode = new BinNode();
                nullNode.data = -99;
                nullNode.nodeDepth = node.nodeDepth + 1;
                queue.addLast(nullNode);
            }
            if(node.rightChild != null) queue.addLast(node.rightChild);  //右孩子入队尾
            else if(node.nodeDepth < this.treeDepth) {  //空孩子只要不是树的最后一层，都创建一个 -99 的节点。
                BinNode nullNode = new BinNode();
                nullNode.data = -99;
                nullNode.nodeDepth = node.nodeDepth + 1;
                queue.addLast(nullNode);
            }
        }
    }

    //根据节点做二叉树图，其中将 -99 替换为 空字符串
    public void drawTreeNode() {
        calTreeDepth(root, 1);
        System.out.println("The tree`s max depth is: " + treeDepth);
        leverOrderDraw(this.root);  //用于绘图的层次遍历，将空节点记录为-99。
        //根据 this.resultNodeArray() 中的层次遍历结果进行做图
        int index = 0;
        //做图
        int maxRow = (int) Math.floor(Math.log(resultNodeArray.size()) / Math.log(2) + 1); //log(e)(length) / log(e)(2) = log(2)(length)
        outloop:
        for (int row = 1; row <= maxRow; row++) {
            boolean isFirstCol = true;
            for (int col = 1; col <= Math.pow(2, row - 1); col++) {
                if (index > resultNodeArray.size() - 1) break outloop;
                int nextPrintNodeDate = resultNodeArray.get(index++);
                if(isFirstCol) {
                    if(nextPrintNodeDate != -99) System.out.format("%"+ 4 * (int)Math.pow(2, maxRow-row) / 2 +"d", nextPrintNodeDate);
                    else System.out.format("%"+ 4 * (int)Math.pow(2, maxRow-row) / 2 +"s", "");
                }
                else {
                    if(nextPrintNodeDate != -99) System.out.format("%"+ 4 * (int)Math.pow(2, maxRow-row) +"d", nextPrintNodeDate);
                    else System.out.format("%"+ 4 * (int)Math.pow(2, maxRow-row) +"s", "");
                }
                isFirstCol = false;
            }
            System.out.println();
        }
    }

    //测试使用层序构造二叉树
    public void testInitFromLevel(int[] arr){
        System.out.println("==========");
        System.out.println("原始数据： " + Arrays.toString(arr));
        initFromLevel(arr);
        //画出二叉树图案
        drawTreeArray();
        cleanResultArray();
        //输出层序，中序，后序遍历结果
        printTravelResult();
    }

    //测试使用先序+中序构造二叉树
    public void testInitFromPreIn(int[] arrPre, int[] arrIn){
        System.out.println("==========");
        System.out.println("原始数据： " + "先序 " + Arrays.toString(arrPre) + "  中序 " + Arrays.toString(arrIn));
        initFromPreIn(arrPre, arrIn);
        //输出层序，中序，后序遍历结果
        printTravelResult();
    }

    public void testInitFromInPost(int[] arrIn, int[] arrPost){
        System.out.println("==========");
        System.out.println("原始数据： " + "中序 " + Arrays.toString(arrIn) + "  后序 " + Arrays.toString(arrPost));
        initFromInPost(arrIn, arrPost);
        //输出层序，中序，后序遍历结果
        printTravelResult();
    }

    public static void main(String[] args) {
        //通过 层次 遍历数组构造二叉树
        int[] arr1 = new int[7];
        for (int i=0; i<arr1.length; i++){
            arr1[i] = i+1;
        }
        MyBinaryTree myLevelTree1 = new MyBinaryTree();
        myLevelTree1.testInitFromLevel(arr1);

        //通过 先序+中序 遍历数组构造二叉树
        int[] arrPre2 = new int[]{1, 2, 4, 5, 3, 6, 7};
        int[] arrIn2 = new int[]{4, 2, 5, 1, 6, 3, 7};
        MyBinaryTree myPreInTree2 = new MyBinaryTree();
        myPreInTree2.testInitFromPreIn(arrPre2, arrIn2);
        //画出二叉树图案
        myPreInTree2.drawTreeArray();
        myPreInTree2.cleanResultArray();

        //通过 中序+后序 遍历数组构造二叉树
        int[] arrIn3 = new int[]{4, 2, 5, 1, 6, 3, 7};
        int[] arrPost3 = new int[]{4, 5, 2, 6, 7, 3, 1};
        MyBinaryTree myPreInTree3 = new MyBinaryTree();
        myPreInTree3.testInitFromInPost(arrIn3, arrPost3);
        //画出二叉树图案
        myPreInTree3.drawTreeArray();
        myPreInTree3.cleanResultArray();

        //通过自定义节点构造二叉树
        MyBinaryTree myNodeTree4 = new MyBinaryTree();
        MyBinaryTree.BinNode f = myNodeTree4.new BinNode(1112);
        MyBinaryTree.BinNode e = myNodeTree4.new BinNode(122);
        MyBinaryTree.BinNode d = myNodeTree4.new BinNode(111, null, f);
        MyBinaryTree.BinNode c = myNodeTree4.new BinNode(12, null, e);
        MyBinaryTree.BinNode b = myNodeTree4.new BinNode(11, d, null);
        MyBinaryTree.BinNode a = myNodeTree4.new BinNode(1, b, c);
        myNodeTree4.root = a;
        myNodeTree4.drawTreeNode();
    }
}