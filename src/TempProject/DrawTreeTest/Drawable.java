package TempProject.DrawTreeTest;

public interface Drawable extends DepthSearchable{
    /**
     * 获取值
     * @return 结点的值
     */
    String getValue();

    /**
     * 获取左子树
     * @return 结点左子树
     */
    Drawable getLeftNode();

    /**
     * 获取右子树
     * @return 结点右子树
     */
    Drawable getRightNode();
}
