package TempProject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

public class DrawTree1 {
    // 以下是常量
    private static final String PATH = "C:\\Users\\12921\\Downloads\\tree.png";
    // 画板长度
    private static final int WIDTH = 800;
    // 画板宽度
    private static final int HEIGHT = 500;
    // 圆形半径
    private static final int OVAL_RADIUS = 15;
    // 字体大小
    private static final int FONT_SIZE = 15;
    // 根据字体位数，选择字体偏移量
    private static final int offset_1 = 12;
    private static final int offset_2 = 9;
    private static final int offset_3 = 4;
    //画连线时的偏移量
    private static final int LINE_OFFSET = 3;
    //字体格式
    private static final String FONT_STYLE = "宋体";
    //图片格式
    private static final String IMAGE_STYLE = "PNG";
    //写数字时用到的偏移量
    private static final int FONT_OFFSET = 1;
    //父圆形与子圆形之间的偏移量
    private static final int OVAL_OFFSET = 10;
    //root结点的y坐标
    private static final int ROOT_Y = 50;
    //图形缓存区
    private BufferedImage bi;
    //画图笔
    private Graphics2D g2;

    //以下是变量
    // 当前圆位置和值
    private int my_x;
    private int my_y;
    private int val;

    // 当前字体位置
    private int my_font_x;
    private int my_font_y;


    private void initSettings(){
        //得到图片缓冲区
        bi = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        //得到绘制环境(这张图片的笔)
        g2 = (Graphics2D) bi.getGraphics();
        g2.fillRect(0,0,WIDTH,HEIGHT);
        g2.setBackground(Color.WHITE);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font(FONT_STYLE,Font.PLAIN,FONT_SIZE));
    }
    private void closeSettings() throws IOException {
        ImageIO.write(bi,IMAGE_STYLE,new FileOutputStream(PATH));
    }

    public static class DrawNode{
        protected int val;
        protected DrawNode left;
        protected DrawNode right;
        public DrawNode() {}
        public DrawNode(int val) {
            this.val = val;
        }
        public DrawNode(int val, DrawNode left, DrawNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 画一个圆，包括数字
     */
    private void drawOval(){
        g2.drawOval(my_x,my_y,OVAL_RADIUS*2,OVAL_RADIUS*2);
        my_font_y = my_y+OVAL_RADIUS+FONT_SIZE/2-FONT_OFFSET;
        switch (String.valueOf(val).length()){
            case 1: my_font_x = my_x+offset_1;break;
            case 2: my_font_x = my_x+offset_2;break;
            case 3: my_font_x = my_x+offset_3;break;
            default: System.out.println("值为"+String.valueOf(val).length()+"位数，不支持！");
        }
        g2.drawString(String.valueOf(val),my_font_x,my_font_y);
    }

    /**
     * 画连线
     * @param isLeft 父结点与子结点的关系
     * @param parent_x 父结点的x坐标
     * @param parent_y 父结点的y坐标
     */
    private void drawLine(boolean isLeft,int parent_x,int parent_y){
        int parent_point_x;
        int parent_point_y;
        int my_point_x;
        int my_point_y;
        if(isLeft){
            parent_point_x = parent_x+OVAL_RADIUS/2-LINE_OFFSET;
            parent_point_y = parent_y+(3*OVAL_RADIUS)/2+LINE_OFFSET;
            my_point_x = my_x+(3*OVAL_RADIUS)/2+LINE_OFFSET;
            my_point_y = my_y+OVAL_RADIUS/2-LINE_OFFSET;
        }else{
            parent_point_x = parent_x+(3*OVAL_RADIUS)/2+LINE_OFFSET;
            parent_point_y = parent_y+(3*OVAL_RADIUS)/2+LINE_OFFSET;
            my_point_x = my_x+OVAL_RADIUS/2-LINE_OFFSET;
            my_point_y = my_y+OVAL_RADIUS/2-LINE_OFFSET;
        }
        g2.drawLine(parent_point_x,parent_point_y,my_point_x,my_point_y);
    }

    /**
     * 一边中序遍历一边画图
     * @param node
     */
    private void firstTraverseAndDraw(DrawNode parentNode,DrawNode node,boolean isLeft,int parent_x,int parent_y){
        if(node == null){
            return;
        }
        //只画当前结点，而不画父结点。但是要根据父结点与当前结点的关系，确定当前结点的位置，并将连线画出来
        val = node.val;
        if(parentNode == null){
            my_x = WIDTH/2 - OVAL_RADIUS;
            my_y = ROOT_Y;
        }else{
            my_y = parent_y+2*OVAL_RADIUS+OVAL_OFFSET;
            if(isLeft){
                my_x = parent_x-2*OVAL_RADIUS-OVAL_OFFSET;
            }else{
                my_x = parent_x+2*OVAL_RADIUS+OVAL_OFFSET;
            }
            drawLine(isLeft,parent_x,parent_y);
        }
        drawOval();

        // 递归画出左子树和右子树
        parent_x = my_x;
        parent_y = my_y;
        firstTraverseAndDraw(node,node.left,true,parent_x,parent_y);
        firstTraverseAndDraw(node,node.right,false,parent_x,parent_y);
    }

    public DrawNode build(){
        DrawNode m = new DrawNode(60);
        DrawNode e = new DrawNode(73);
        DrawNode h = new DrawNode(51);
        DrawNode i = new DrawNode(93);
        DrawNode j = new DrawNode(37);
        DrawNode k = new DrawNode(120);
        DrawNode l = new DrawNode(9);
        DrawNode f = new DrawNode(99,i,k);
        DrawNode c = new DrawNode(88,e,f);
        DrawNode g = new DrawNode(35,l,j);
        DrawNode d = new DrawNode(47,g,h);
//        DrawNode b = new DrawNode(58,d,null);
        DrawNode b = new DrawNode(58,d,m);
        DrawNode a = new DrawNode(62,b,c);
        return a;
    }
    public void drawEntrance(DrawNode drawNode) throws IOException {
        initSettings();
        firstTraverseAndDraw(null,drawNode,true,0,0);
        closeSettings();
    }

    public static void main(String[] args) throws IOException {
        DrawTree1 d = new DrawTree1();
        //先构建一颗二叉树
        DrawNode root = d.build();
        //调用入口方法画图
        d.drawEntrance(root);
    }

}