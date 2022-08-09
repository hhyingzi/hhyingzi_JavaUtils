package TempProject.DrawTreeTest;

public class DepthSearch {
    public static int search(DepthSearchable depthSearchable, int depth){
        if(depthSearchable == null){
            return 0;
        }
        if(depthSearchable.getDepthSearchableLeft() == null && depthSearchable.getDepthSearchableRight() == null){
            return depth;
        }
        if(depthSearchable.getDepthSearchableLeft() == null){
            return search(depthSearchable.getDepthSearchableRight(),depth+1);
        }
        if(depthSearchable.getDepthSearchableRight() == null){
            return search(depthSearchable.getDepthSearchableLeft(),depth+1);
        }

        int a = search(depthSearchable.getDepthSearchableLeft(),depth+1);
        int b = search(depthSearchable.getDepthSearchableRight(),depth+1);
        return a>b?a:b;
    }
}
