package MyDataStructure.BasicStructure.MyGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//《算法第四版》 加权无向图
public class MyGraphEdgeWeighted{
    public class Edge{
        final int v1;
        final int v2;
        final double weight;  //权重
        public Edge(int v1, int v2, double weight){
            this.v1 = v1;
            this.v2 = v2;
            this.weight = weight;
        }
        public double getWeight(){ return this.weight; }
        public int getVertex(){ return v1; }
        public int otherVertex(int vertex){ return (vertex==v1)?v2:v1;}
    }

    private int VNum;  //顶点数量
    private int ENum;  //边的数量
    private List<Integer>[] adj;  //邻接表，索引与顶点索引对应

    //录入顶点数量，构造空图
    public MyGraphEdgeWeighted(int vnum){
        this.VNum = vnum; this.ENum = 0;
        this.adj = (LinkedList<Integer>[]) new LinkedList[VNum];
        //初始化所有邻接表
        for(int i=0; i<VNum; i++){
            adj[i] = new LinkedList<>();
        }
    }

    public int getVNum() { return VNum; }
    public int getENum() { return ENum; }

    public void addEdge(Edge e) {
        int v1 = e.getVertex();
        int v2 = e.otherVertex(v1);
        adj[v1].add(v2); adj[v2].add(v1);
        this.ENum++;
    }

    public Iterable<Integer> getAdjVertex(int key) {
        return this.adj[key];
    }
}
