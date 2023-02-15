package MyDataStructure.BasicStructure;

import java.util.*;

/**
 * AVL 树，演示了查找，插入，和插入后重平衡算法
 * 没有实现删除算法（粗略实现，未验证）
 * 原理：插入或删除后，对节点往上依次更新节点高度，并检查左右孩子节点高度差是否未超过1，若超过则进行重平衡算法使其不超过1。
 */
public class MyLearnAVLTree extends MyBinarySearchTree {
    public Node root;
    private Node _hot;  //在二分查找时，记录上一个经过的节点，可解决 null 无法记录父节点的问题
    public int size;

    public class Node extends MyBinarySearchTree.Node{
        public int key;
        public int value;
        public int height;  //节点的高度，用于检查平衡性。叶子节点高度为1，其祖先节点为 2, 3, ...
        public Node leftChild;
        public Node rightChild;
        public Node parent;  //记录其父节点，用于增删节点后，检查祖先节点的平衡性
    }
    public LinkedHashMap<Integer, Integer> resultLinkedHashMap = new LinkedHashMap<>();

    public void put(int key, int value){
        //从根向下二分查找，返回命中的节点或null，并将其父节点赋值到 this._hot
        Node searchedNode = searchNode(key);
        //命中则直接修改
        if (searchedNode != null){
            searchedNode.value = value;
            return;
        }
        //未命中则新建节点，并挂载到 _hot 之下。
        searchedNode = new Node();
        searchedNode.key = key;
        searchedNode.value = value;
        searchedNode.height = 1;
        searchedNode.parent = _hot;
        this.size++;
        if (_hot == null){
            this.root = searchedNode;
            return;
        }
        else {
            if (key < _hot.key) _hot.leftChild = searchedNode;
            else _hot.rightChild = searchedNode;
            updateHeight(_hot);
        }

        //向上更新每个祖先节点的高度，并检查平衡性，如果失衡则对 (grandParent, parent, node) 进行旋转。
        //首个失衡的节点 grandParent ，至少是叶子 searchedNode 的祖父级别的节点。
        for (Node grandParent = _hot.parent; grandParent != null; searchedNode = _hot, _hot = grandParent, grandParent = grandParent.parent) {
            updateHeight(grandParent);
            if(!checkBalance(grandParent)){
                Node grandParentParent = grandParent.parent;
                Node tempNode = rotateAt(searchedNode);
                if(this.root == grandParent){
                    this.root = tempNode;  //检查平衡性，如果不平衡则对 (searchedNode, _hot, grandParent) 进行旋转
                    tempNode.parent = null;
                }
                else if(grandParentParent.leftChild == grandParent){
                    grandParentParent.leftChild = tempNode;
                    tempNode.parent = grandParentParent;
                }
                else{
                    grandParentParent.rightChild = tempNode;
                    tempNode.parent = grandParentParent;
                }
            }
        }
    }

    private Node searchNode(int key){
        Node node = this.root;
        this._hot = null;
        while(node != null){
            this._hot = node;
            if(key == node.key) return node;
            else if(key < node.key) node = node.leftChild;
            else node = node.rightChild;
        }
        return null;
    }

    //根据左右子树的高度，更新本节点高度
    private void updateHeight(Node node) {
        if (node == null) return;
        int leftHeight, rightHeight;
        //左子树高度
        if (node.leftChild == null) leftHeight = 0;
        else leftHeight = node.leftChild.height;
        //右子树高度
        if (node.rightChild == null) rightHeight = 0;
        else rightHeight = node.rightChild.height;
        //找出左右子树最高的高度，+1 后赋值给 node
        if (leftHeight >= rightHeight) node.height = leftHeight + 1;
        else node.height = rightHeight + 1;
    }

    //检查节点的左右孩子的平衡性，不平衡则返回 false。
    private boolean checkBalance(Node node) {
        int leftHeight = 0, rightHeight = 0;
        if (node.leftChild != null) leftHeight = node.leftChild.height;
        if (node.rightChild != null) rightHeight = node.rightChild.height;
        if(leftHeight - rightHeight > 1 || leftHeight - rightHeight < -1) return false;
        else return true;
    }

    //结点 node 的 grandParent 发生左右子树失衡后，对 node, parent, grandParent 进行旋转操作。
    private Node rotateAt(Node node) {
        Node parent = node.parent;
        Node grandParent = parent.parent;
        if (grandParent.leftChild == parent) {
            /** zig(grandParent)
             *                           grandParent(k+3)
             *                parent(k+2)      |       T3(k)
             *         node(k+1)       T2(k)
             *    T0(<=k)   T1(<=k)
             *    ================================================
             *                parent(k+2)
             *         node(k+1)    |    grandParent(K+1)
             *    T0(<=k)    T1(<=k) |  T2(k)          T3(k)
             */
            if (parent.leftChild == node) {
                return connect34(node, parent, grandParent, node.leftChild, node.rightChild, parent.rightChild, grandParent.rightChild);
            }
            /** zag(parent), zig(grandParent)
             *                          grandParent(k+3)
             *               parent(k+2)       |        T3(k)
             *           T0(k)         node(k+1)
             *                      T1(<=k)    T2(<=k)
             *    ================================================
             *                              node(k+2)
             *               parent(k+1)      |       grandParent(k+1)
             *           T0(k)      T1(<=k)   |    T2(<=k)      T3(k)
             */
            else {
                return connect34(parent, node, grandParent, parent.leftChild, node.leftChild, node.rightChild, grandParent.rightChild);
            }
        } else {
            /** zag(parent)
             *          grandParent(k+3)
             *        T0(k)      |           parent(k+2)
             *                          T1(k)         node(k+1)
             *                                    T2(<=k)  T3(<=k)
             *    ================================================
             *                    parent(K+2)
             *      grandParent(k+1)   |     node(k+1)
             *    T0(k)         T1(k)  |  T2(<=k)  T3(<=k)
             */
            if (parent.rightChild == node) {
                return connect34(grandParent, parent, node, grandParent.leftChild, parent.leftChild, node.leftChild, node.rightChild);
            }
            /** zig(parent), zag(grandParent)
             *              grandParent(k+3)
             *         T0(k)       |        parent(k+2)
             *                      node(k+1)       T3(k)
             *                 T1(<=k)   T2(<=k)
             *    ================================================
             *                     node(k+2)
             *        grandParent(k+1)  |     parent(k+1)
             *      T0(k)    T1(<=k)  |  T2(<=k)   T3(k)
             */
            else {
                return connect34(grandParent, node, parent, grandParent.leftChild, node.leftChild, node.rightChild, parent.rightChild);
            }
        }
    }

    //对 三个节点 node, parent, grandParent，和 四个子树 T0, T1, T2, T3，进行重组。
    private Node connect34(Node node, Node parent, Node grandParent, Node T0, Node T1, Node T2, Node T3) {
        //T0, T1 挂载到 node 上
        node.leftChild = T0; if(T0 != null) T0.parent = node;
        node.rightChild = T1; if(T1 != null)T1.parent = node;
        //T2, T3 挂载到 grandParent 上
        grandParent.leftChild = T2; if(T2 != null) T2.parent = grandParent;
        grandParent.rightChild = T3; if(T3 != null) T3.parent = grandParent;
        //node, grandParent 挂载到 parent 上
        parent.leftChild = node; node.parent = parent;
        parent.rightChild = grandParent; grandParent.parent = parent;
        //更新高度
        updateHeight(node); updateHeight(grandParent); updateHeight(parent);
        return parent;
    }

    public int get(int key){
        Node searchedNode = searchNode(key);
        if(searchedNode == null) return -99;
        else return searchedNode.value;
    }

    //delete未验证
    public void delete(int key){
        //从根向下二分查找，返回命中的节点或null，并将其父节点赋值到 this._hot
        Node searchedNode = searchNode(key);

        //未命中则不删除
        if(searchedNode == null) return;

        //命中，则删除该节点，并获取新的子树根，挂载到其父节点下面
        Node nextSubTreeRoot, leafParent = searchedNode.parent;  //新的子树根，缺少一个节点的位置的父节点（用于更新高度）
        //如果没有右孩子，那么左孩子直接上来当父节点
        if(searchedNode.rightChild == null) nextSubTreeRoot = searchedNode.leftChild;
        //如果没有左孩子，那么右孩子直接上来当父节点
        else if(searchedNode.leftChild == null) nextSubTreeRoot = searchedNode.rightChild;
        //若左右子树均存在，则选择x的直接后继（必为右子树的最左节点）作为实际被摘除节点
        else{
            Node recordLeftChild = searchedNode.leftChild;  //用临时变量记录下当前的左子树
            nextSubTreeRoot = assistGetMin(searchedNode.rightChild);  //当前节点替换成右子树最小节点（一定是个叶子节点）
            leafParent = nextSubTreeRoot.parent;  //被删除的叶子的父节点
            nextSubTreeRoot.leftChild = recordLeftChild;  //把左子树挂载回来
            nextSubTreeRoot.rightChild = assistDeleteMin(searchedNode.rightChild);  //右孩子挂载为删除最小节点后的右子树根
        }
        this.size--;
        //新子树的根，挂载到父节点上
        if (_hot == null){
            this.root = nextSubTreeRoot;
            return;
        }
        else {
            if (key < _hot.key) _hot.leftChild = nextSubTreeRoot;
            else _hot.rightChild = nextSubTreeRoot;
            updateHeight(_hot);
        }

        //向上更新每个祖先节点的高度，并检查平衡性，如果失衡则对 (grandParent, parent, node) 进行旋转。
        //首个失衡的节点 grandParent ，至少是叶子 searchedNode 的祖父级别的节点。
        searchedNode = leafParent; _hot = searchedNode.parent;
        for (Node grandParent = _hot.parent; grandParent != null; searchedNode = _hot, _hot = grandParent, grandParent = grandParent.parent) {
            updateHeight(grandParent);
            if(!checkBalance(grandParent)){
                Node grandParentParent = grandParent.parent;
                Node tempNode = rotateAt(searchedNode);
                if(this.root == grandParent){
                    this.root = tempNode;  //检查平衡性，如果不平衡则对 (searchedNode, _hot, grandParent) 进行旋转
                    tempNode.parent = null;
                }
                else if(grandParentParent.leftChild == grandParent){
                    grandParentParent.leftChild = tempNode;
                    tempNode.parent = grandParentParent;
                }
                else{
                    grandParentParent.rightChild = tempNode;
                    tempNode.parent = grandParentParent;
                }
            }
        }
    }

    private void updateHeightAbove(Node node){
        while(node != null){
            updateHeight(node);
            node = node.parent;
        }
    }

    private Node assistGetMin(Node node){
        if(node.leftChild == null) return node;
        else return assistGetMin(node.leftChild);
    }
    private Node assistDeleteMin(Node node){
        //如果左孩子为空，那么本节点该被删除，应当返回右孩子。
        if(node.leftChild == null) return node.rightChild;
        //如果左孩子不为空，那么递归删除左子树中的最小节点，将左孩子设置为删除后的左子树根（返回值）
        node.leftChild = assistDeleteMin(node.leftChild);
        return node;
    }

    //先序遍历
    public void preOrderRecur(Node node) {
        if (node == null) return;
        visit(node);
        preOrderRecur(node.leftChild);
        preOrderRecur(node.rightChild);
    }
    //中序遍历，会从小到大输出节点
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

    private void visit(Node node){ resultLinkedHashMap.put(node.key, node.value); }

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
        MyLearnAVLTree tree1 = new MyLearnAVLTree();
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
        MyLearnAVLTree treeTest = new MyLearnAVLTree();
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
