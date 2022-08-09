package TempProject.DrawTreeTest;

import TempProject.DrawTreeTest.DrawTree;

import java.util.Random;

public class DrawTreeBuilder {
    /**
     * 构建一颗可画的二叉树
     * @return 可画二叉树的根结点
     */
    public static DrawNode buildBySample_1(){
        DrawNode e = new DrawNode("73");
        DrawNode h = new DrawNode("51");
        DrawNode i = new DrawNode("93");
        DrawNode j = new DrawNode("37");
        DrawNode k = new DrawNode("120");
        DrawNode l = new DrawNode("9");
        DrawNode f = new DrawNode("99",i,k);
        DrawNode c = new DrawNode("88",e,f);
        DrawNode g = new DrawNode("35",l,j);
        DrawNode d = new DrawNode("47",g,h);
//        DrawNode b = new DrawNode("58",d,null);
        DrawNode m = new DrawNode("60");
        DrawNode b = new DrawNode("58",d,m);
        DrawNode a = new DrawNode("62",b,c);
        return a;
    }

    /**
     * 构建一颗n层的满二叉树。
     * @param node 根节点
     * @param level 层数
     */
    private static void buildByLevel(DrawNode node,int level){
        if(level == 0){
            return;
        }
        //构建当前层的左孩子和右孩子
        node.setLeft(new DrawNode(String.valueOf(new Random().nextInt(999))));
        node.setRight(new DrawNode(String.valueOf(new Random().nextInt(999))));
        //递归构建下一层
        buildByLevel(node.getLeft(),level-1);
        buildByLevel(node.getRight(),level-1);
    }

    /**
     * 构建一颗满二叉树
     * @param level 树的深度
     * @return 二叉树根结点
     */
    public static DrawNode buildByLevel(int level){
        //生成根节点
        DrawNode root = new DrawNode(String.valueOf(new Random().nextInt(999)));
        //递归构建具有n层的满二叉树
        buildByLevel(root,level);
        //构建完成返回根节点
        return root;
    }
}
