package TempProject.DrawTreeTest;

import java.io.IOException;

public class DrawMain {
    public static void main(String[] args) throws IOException {
        DrawTree d = new DrawTree();
        //先构建一颗二叉树
        DrawNode root = DrawTreeBuilder.buildBySample_1();
        //调用入口方法画图
        d.drawEntrance(root, "C:\\Users\\12921\\Downloads\\tree2.png");

        //按树的高度构建满二叉树
        //DrawNode root2 = DrawTreeBuilder.buildByLevel(5);
        //d.drawEntrance(root2,CommonUtil.getResourceRoot()+"tree/draw/level.png");
    }
}
