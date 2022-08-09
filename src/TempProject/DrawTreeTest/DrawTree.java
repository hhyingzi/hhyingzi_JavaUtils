package TempProject.DrawTreeTest;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

public class DrawTree {
    //以下是常量
    /**
     * 二叉树结点圆半径
     */
    private static final int OVAL_RADIUS = 15;
    /**
     * 字体大小
     */
    private static final int FONT_SIZE = 15;
    /**
     * 结点值的字符串长度为1时，字体的偏移量
     */
    private static final int offset_1 = 12;
    /**
     * 结点值的字符串长度为3时，字体的偏移量
     */
    private static final int offset_2 = 9;
    /**
     * 结点值的字符串长度为2时，字体的偏移量
     */
    private static final int offset_3 = 4;

    /**
     * 画连线时的偏移量
     */
    private static final int LINE_OFFSET = 3;
    /**
     * 字体格式
     */
    private static final String FONT_STYLE = "宋体";
    /**
     * 图片格式
     */
    private static final String IMAGE_STYLE = "PNG";
    /**
     * 写结点的值时用到的偏移量
     */
    private static final int FONT_OFFSET = 1;
    /**
     * 父圆形与子圆形之间的y轴偏移量
     */
    private static final int OVAL_OFFSET_Y = 10;
    /**
     * root结点的y坐标
     */
    private static final int ROOT_Y = 50;
    /**
     * 图形缓存区对象
     */
    private BufferedImage bi;
    /**
     * 画图笔对象
     */
    private Graphics2D g2;

    //以下是变量
    /**
     * 当前圆的x坐标
     */
    private int my_x;
    /**
     * 当前圆的y坐标
     */
    private int my_y;
    /**
     * 当前圆的值
     */
    private String val;
    /**
     * 当前字体x坐标
     */
    private int my_font_x;
    /**
     * 当前字体y坐标
     */
    private int my_font_y;
    /**
     * 文件输出路径
     */
    private String path;
    /**
     * 画板长度
     */
    private int width = 800;
    /**
     * 画板宽度
     */
    private int height = 500;
    /**
     * 父圆形与子圆形之间的x轴偏移量数组，防止结点重合
     */
    private int[] offset_arr;
    /**
     * 是否忽略结点重合
     */
    private boolean ignoreOverlap;


    /**
     * 初始化设置
     * @param drawable 可画的二叉树的根节点
     * @param path 图片路径
     * @param offset_x_arr 父圆形与子圆形之间的x轴偏移量数组
     */
    private void initSettings(Drawable drawable,String path,boolean ignoreOverlap,int[] offset_x_arr){
        this.path = path;
        this.ignoreOverlap = ignoreOverlap;
        //查询树深度
        int depth = DepthSearch.search(drawable, 1);
        //根据树的深度，规划各层x轴偏移量数组
        offset_arr = new int[depth];
        if(ignoreOverlap){
            //忽略重合，则偏移量恒为圆直径
            for (int i = depth-1;i>=1;i--){
                offset_arr[i] = OVAL_RADIUS*2;
            }
        }else{
            if(null == offset_x_arr){
                //将树当成满二叉树来对待，从而设计各层偏移量
                int offset_i = 1;
                for (int i = depth-1;i>=1;i--){
                    offset_arr[i] = OVAL_RADIUS*offset_i;
                    offset_i*=2;
                }
            }else{
                if(offset_x_arr.length < depth-1){
                    throw  new RuntimeException("自定义x轴偏移量数组的长度必须大于等于树的深度减去1！");
                }
                for (int i = 1; i < offset_arr.length; i++) {
                    offset_arr[i] = offset_x_arr[i-1];
                }
            }
        }


        //得到图片缓冲区
        bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //得到绘制环境(这张图片的笔)
        g2 = (Graphics2D) bi.getGraphics();
        g2.fillRect(0,0,width,height);
        g2.setBackground(Color.WHITE);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font(FONT_STYLE,Font.PLAIN,FONT_SIZE));
    }

    /**
     * 画图结束时的设置
     */
    private void closeSettings() {
        try {
            ImageIO.write(bi,IMAGE_STYLE,new FileOutputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("画图失败,异常信息为："+e.getMessage());
        }
    }

    /**
     * 画一个结点圆和结点圆的值
     */
    private void drawOval(){
        g2.drawOval(my_x,my_y,OVAL_RADIUS*2,OVAL_RADIUS*2);
        my_font_y = my_y+OVAL_RADIUS+FONT_SIZE/2-FONT_OFFSET;
        switch (val.length()){
            case 1: my_font_x = my_x+offset_1;break;
            case 2: my_font_x = my_x+offset_2;break;
            case 3: my_font_x = my_x+offset_3;break;
            default: System.out.println("值为"+val.length()+"位数，不支持！");
        }
        g2.drawString(val,my_font_x,my_font_y);
    }

    /**
     * 画父圆与子圆之间的连线
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
     * @param parentNode 父结点
     * @param node 子结点
     * @param isLeft 父结点与子结点的关系
     * @param parent_x 父结点的x坐标
     * @param parent_y 父结点的y坐标
     * @param level 当前层数，需要根据当前层数确定父圆与子圆的x轴偏移量
     */
    private void firstTraverseAndDraw(Drawable parentNode,Drawable node,boolean isLeft,int parent_x,int parent_y,int level){
        if(node == null){
            return;
        }
        //只画当前结点，而不画父结点。但是要根据父结点与当前结点的关系，确定当前结点的位置，并将连线画出来
        val = node.getValue();
        if(parentNode == null){
            my_x = width/2 - OVAL_RADIUS;
            my_y = ROOT_Y;
        }else{
            my_y = parent_y+2*OVAL_RADIUS+OVAL_OFFSET_Y;
            if(isLeft){
                my_x = parent_x-offset_arr[level];
            }else{
                my_x = parent_x+offset_arr[level];
            }
            drawLine(isLeft,parent_x,parent_y);
        }
        drawOval();

        // 递归画出左子树和右子树
        parent_x = my_x;
        parent_y = my_y;
        firstTraverseAndDraw(node,node.getLeftNode(),true,parent_x,parent_y,level+1);
        firstTraverseAndDraw(node,node.getRightNode(),false,parent_x,parent_y,level+1);
    }


    /**
     * 忽略重合
     * @param drawable
     * @param path
     */
    public void drawEntrance(Drawable drawable,String path) {
        drawEntrance(drawable,path,true,null);
    }

    /**
     * 不忽略重合，但将树当做满二叉树去设计x轴偏移量
     * @param drawable
     * @param path
     * @param ignoreOverlap
     */
    public void drawEntrance(Drawable drawable,String path,boolean ignoreOverlap) {
        drawEntrance(drawable,path,ignoreOverlap,null);
    }

    /**
     * 画二叉树核心构造方法
     * @param drawable 根结点
     * @param path 图片路径
     * @param ignoreOverlap 是否忽略重合
     * @param offset_x_arr 当不忽略重合且不为null时，采用自定义x轴偏移量;假设树的深度是n层，则该数组长度为n-1，且值从第二层开始
     */
    public void drawEntrance(Drawable drawable,String path,boolean ignoreOverlap,int[] offset_x_arr) {
        initSettings(drawable,path,ignoreOverlap,offset_x_arr);
        firstTraverseAndDraw(null,drawable,true,0,0,0);
        closeSettings();
    }


    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
