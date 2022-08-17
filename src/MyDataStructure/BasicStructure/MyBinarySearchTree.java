package MyDataStructure.BasicStructure;

import java.util.*;


/**
 * 二叉查找树（参考自算法第四版）
 * 节点 node，左孩子的 key 小于 node.key，右孩子的 key 大于 node.key。
 * 整棵树中序遍历 key 是递增的，但左右子树高度可能会非常不平衡。
 */
public class MyBinarySearchTree {
    public Node root;
    public int size = 0;  //整棵树的元素数量，空树为默认值 0
    public class Node{
        public int key;
        public int value;
        public Node leftChild;
        public Node rightChild;
    }
    public LinkedHashMap<Integer, Integer> resultLinkedHashMap = new LinkedHashMap<>();

    //向树中添加或修改键值对
    public void put(int key, int value){
        Node node = this.root;
        Node parent = null;
        //向下查找，命中则修改value并返回，否则一直走到null。
        while(node != null){
            parent = node;
            if(key == node.key) {
                node.value = value;
                return;
            }
            else if(key < node.key) node = node.leftChild;
            else node = node.rightChild;
        }
        //未命中，新建节点
        node = new Node();
        node.key = key; node.value = value;
        if(parent == null){
            this.root = node;
        }
        else if(key < parent.key) parent.leftChild = node;
        else parent.rightChild = node;
        size++;
    }

    //查找键对应的值
    public int get(int key){
        Node node = this.root;
        while(node != null){
            if(key == node.key) return node.value;  //命中则返回相应 key 对应的 value。
            else if(key < node.key) node = node.leftChild;
            else node = node.rightChild;
        }
        return -99;  //未命中
    }

    //删除键对应的结点。delete未验证
    public void delete(int key){
        root = assistDelete(this.root, key);
    }
    //由于存在删除根节点的可能
    private Node assistDelete(Node node, int key){
        if(node == null) return null;
        if(key < node.key) node.leftChild = assistDelete(node.leftChild, key);
        else if(key > node.key) node.rightChild = assistDelete(node.rightChild, key);
        else{
            //如果没有右孩子，那么左孩子直接上来当父节点，右孩子不会冲突
            if(node.rightChild == null) return node.leftChild;
            //如果没有左孩子，那么右孩子直接上来当父节点，左孩子不会冲突
            else if(node.leftChild == null) return node.rightChild;
            //若左右子树均存在，则选择x的直接后继（必为右子树的最左节点）作为实际被摘除节点
            else{
                Node recordLeftChild = node.leftChild;  //用临时变量记录下当前的左子树
                node = assistGetMin(node.rightChild);  //当前节点替换成右子树最小节点（一定是个叶子节点）
                node.leftChild = recordLeftChild;  //把左子树挂载回来
                node.rightChild = assistDeleteMin(node.rightChild);  //右孩子挂载为删除最小节点后的右子树根
                size--;
            }
        }
        return node;
    }

    public int getMin(){ return assistGetMin(this.root).key; }

    private Node assistGetMin(Node node){
        if(node.leftChild == null) return node;
        else return assistGetMin(node.leftChild);
    }

    public void deleteMin(){ this.root = assistDeleteMin(this.root); }
    private Node assistDeleteMin(Node node){
        //如果左孩子为空，那么本节点该被删除，应当返回右孩子。
        if(node.leftChild == null) return node.rightChild;
        //如果左孩子不为空，那么递归删除左子树中的最小节点，将左孩子设置为删除后的左子树根（返回值）
        node.leftChild = assistDeleteMin(node.leftChild);
        return node;
    }

    private void visit(Node node){ resultLinkedHashMap.put(node.key, node.value); }
    //层次遍历
    public void leverOrder(Node node) {
        resultLinkedHashMap.clear();
        if (node == null) return;
        LinkedList<Node> queue = new LinkedList<>();
        queue.addLast(node); //根节点入队
        while (!queue.isEmpty()) {
            node = queue.removeFirst();  //取出队首
            visit(node);
            if(node.leftChild != null) queue.addLast(node.leftChild);  //左孩子入队尾
            if(node.rightChild != null) queue.addLast(node.rightChild);  //右孩子入队尾
        }
    }

    //先序遍历
    public void preOrderRecur(Node node) {
        if (node == null) return;
        visit(node);
        preOrderRecur(node.leftChild);
        preOrderRecur(node.rightChild);
    }

    //中序遍历
    public void inOrderRecur(Node node) {
        if (node == null) return;
        inOrderRecur(node.leftChild);
        visit(node);
        inOrderRecur(node.rightChild);
    }

    //后序遍历
    public void postOrderRecur(Node node) {
        if (node == null) return;
        postOrderRecur(node.leftChild);
        postOrderRecur(node.rightChild);
        visit(node);
    }

    //调用 MyBinaryTreeIntNode.drawTreeNode(node) 函数进行绘图
    public void drawAndTraval(){
        int[] preOrderArr = new int[this.size];  //先序遍历结果数组
        int[] inOrderArr = new int[this.size];  //中序遍历结果数组
        resultLinkedHashMap.clear();
        preOrderRecur(this.root);
        Iterator<Integer> keyIter1 = resultLinkedHashMap.keySet().iterator();
        for(int i=0; i<preOrderArr.length; i++){
            preOrderArr[i] = keyIter1.next();
        }
        resultLinkedHashMap.clear();
        inOrderRecur(this.root);
        Iterator<Integer> keyIter2 = resultLinkedHashMap.keySet().iterator();
        for(int i=0; i<inOrderArr.length; i++){
            inOrderArr[i] = keyIter2.next();
        }
        //生成 MyBinaryTreeIntNode 二叉树
        MyBinaryTreeIntNode stdBinaryTree = new MyBinaryTreeIntNode();
        stdBinaryTree.initFromPreIn(preOrderArr, inOrderArr);
        System.out.println("==========");
        stdBinaryTree.printTravelResult();
        stdBinaryTree.drawTreeNode();
    }

    public static void main(String[] args){
        //随机产生数据并测试
        int length = 10;
        int[] arrKey = new int[length]; int[] arrValue = new int[length];
        MyBinarySearchTree tree1 = new MyBinarySearchTree();
        for(int i=0; i<arrKey.length; i++){
            arrKey[i] = new Random().nextInt(10);
            arrValue[i] = new Random().nextInt(100);
        }
        System.out.println("Key: " + Arrays.toString(arrKey));
        System.out.println("Value: " + Arrays.toString(arrValue));
        for(int j=0; j< arrKey.length; j++) tree1.put(arrKey[j], arrValue[j]);
        int searchKey = new Random().nextInt(10);
        tree1.drawAndTraval();
        System.out.println("Search key: " + searchKey + "  result value = " + tree1.get(searchKey));

        //自己录入数据并测试
        MyBinarySearchTree treeTest = new MyBinarySearchTree();
        int[] testKey = new int[]{1, 2, 3, 2};
        int[] testValue = new int[]{101, 102, 103, 202};
        searchKey = 2;
        for(int i=0; i<testKey.length; i++) treeTest.put(testKey[i], testValue[i]);
        System.out.println("Key: " + Arrays.toString(testKey));
        System.out.println("Value: " + Arrays.toString(testValue));
        treeTest.drawAndTraval();
        System.out.println("Search key: " + searchKey + "  result value = " + treeTest.get(searchKey));
    }
}
