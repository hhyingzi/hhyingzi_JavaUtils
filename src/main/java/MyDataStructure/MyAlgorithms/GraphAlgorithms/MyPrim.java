package MyDataStructure.MyAlgorithms.GraphAlgorithms;

import MyDataStructure.BasicStructure.MyGraph.MyGraphEdgeWeighted;

//针对带权无向图的，最小生成树算法Prim
public class MyPrim {
    private MyGraphEdgeWeighted.Edge edgeTo;
    private double[] distTo;  //distTo[w]=edgeTo[w].weight();
    private boolean[] marked;   //如果 v 已经在最小生成树中，则为 true。
//    private IndexMinPQ<double> pq;  //有效的横切边

    public MyPrim(MyGraphEdgeWeighted myGraphEdgeWeighted){

    }

    //将顶点 v 添加到最小生成树中，更新数据。
    private void visit(MyGraphEdgeWeighted G, int v){

    }

    public Iterable<MyGraphEdgeWeighted.Edge> edges(){
        return null;
    }

    public double weight(){
        return -1;
    }
}
