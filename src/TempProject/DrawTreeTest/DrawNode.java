package TempProject.DrawTreeTest;

public class DrawNode implements Drawable{
    /**
     * 结点的值
     */
    private String val;
    /**
     * 结点左子树
     */
    private DrawNode left;
    /**
     * 结点右子树
     */
    private DrawNode right;
    public DrawNode() {}
    public DrawNode(String val) {
        this.val = val;
    }
    public DrawNode(String val, DrawNode left, DrawNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String getValue() {
        return this.val;
    }

    @Override
    public Drawable getLeftNode() {
        return this.left;
    }

    @Override
    public Drawable getRightNode() {
        return this.right;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public DrawNode getLeft() {
        return left;
    }

    public void setLeft(DrawNode left) {
        this.left = left;
    }

    public DrawNode getRight() {
        return right;
    }

    public void setRight(DrawNode right) {
        this.right = right;
    }

    @Override
    public DepthSearchable getDepthSearchableLeft() {
        return this.left;
    }

    @Override
    public DepthSearchable getDepthSearchableRight() {
        return this.right;
    }
}
