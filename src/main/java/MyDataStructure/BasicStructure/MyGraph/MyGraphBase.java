package MyDataStructure.BasicStructure.MyGraph;

//无向图基本接口
public interface MyGraphBase {
    int getVNum();  //获取全图顶点数
    int getENum();  //获取边顶点数
    void addEdge(int key1, int key2);  //添加边
    Iterable<Integer> getAdjVertex(int key);  //获取相邻所有顶点的 key
    String toString();  //用字符串打印该图（邻接表）
}
