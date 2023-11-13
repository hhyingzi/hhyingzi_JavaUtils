package MyDataStructure.BasicStructure.MyGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//无向无权图，邻接顶点集形式，《算法 第四版》经典数据结构。
public class MyGraphUnweight implements MyGraphBase {
    private int VNum;  //顶点数量
    private int ENum;  //边的数量
    private List<Integer>[] adj;  //邻接表，索引与顶点索引对应

    //录入顶点数量，构造空图
    public MyGraphUnweight(int vnum){
        this.VNum = vnum; this.ENum = 0;
        this.adj = (LinkedList<Integer>[]) new LinkedList[VNum];
        //初始化所有邻接表
        for(int i=0; i<VNum; i++){
            adj[i] = new LinkedList<>();
        }
    }
    //录入所有边来构造图，每两个key是一组，作为边。{keya1, keya2, keyb1, keyb2, ...}
    public MyGraphUnweight(ArrayList<Integer> EArr){
        for(int i=0; i<EArr.size(); i=i+2){
            int v1 = EArr.get(i);
            int v2 = EArr.get(i+1);
            addEdge(v1, v2);
        }
    }

    @Override
    public int getVNum() { return VNum; }

    @Override
    public int getENum() { return ENum; }

    @Override
    public void addEdge(int key1, int key2) {
        //一条无向图边要加两个位置
        adj[key1].add(key2);
        adj[key2].add(key1);
        this.ENum++;
    }

    @Override
    public Iterable<Integer> getAdjVertex(int key) {
        return this.adj[key];
    }
}


