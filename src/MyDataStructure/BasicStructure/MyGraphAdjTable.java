package MyDataStructure.BasicStructure;

import sun.awt.image.ImageWatched;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 图，邻接表结构
 * 顶点由 int 表示，边由 Edge(key1, key2) 表示。
 * 顶点集是以 int 值作为 key 的 HashMap。
 * 边集是以顶点的 key 作为 key 的 HashMap ，对应的值是一个 Linkedlist<Edge> 邻接表，存储了该顶点作为出度的所有边，
 */

class Vertex{
    int key;  //顶点 key
    int value;  //顶点存储的数据
    LinkedList<Edge> adj = new LinkedList<>();  //边集（邻接表）
    public Vertex(){}
    public Vertex(int key){ this.key = key; }
}

class Edge{
    int v1, v2;  //
    int data;  //边含有的数据
    int weight;  //边的权重
    public Edge(){}
    public Edge(int v1, int v2){ this.v1 = v1; this.v2 = v2; }
}

public class MyGraphAdjTable {
    public int VNum;  //顶点数目
    public int ENum;  //边的数目
    public HashMap<Integer, Vertex> vertices = new HashMap<>();  //顶点集

    public LinkedList<Vertex> resultVertexList = new LinkedList<Vertex>();

    //增删顶点
    public void addVertex(int key){ if(!vertices.containsKey(key)){ VNum++; } vertices.put(key, new Vertex(key)); } //添加不携带数据value的顶点

    //删除顶点与相邻的所有边
    public void delVertex(int key) {
        if (!vertices.containsKey(key)) return;  //没有此顶点
        Vertex vertex = vertices.get(key);
        VNum--;  //更新总顶点数
        ENum = ENum - vertex.adj.size();  //更新总边数

        //遍历 vertices 的邻接表，找到每个相连的 v2 顶点
        for (Edge tempEdge : vertex.adj) {
            if (tempEdge.v2 != key) {
                //从顶点v2的邻接表中删除边 Edge(v2, v1)
                LinkedList<Edge> edgeList = vertices.get(tempEdge.v2).adj;
                Iterator<Edge> edgeIter = edgeList.iterator();
                while(edgeIter.hasNext()){
                    if (edgeIter.next().v2 == key) edgeIter.remove();
                }
            }
        }
        vertices.remove(key);
    }

    //无向无权图加边
    public void addEdge(int v1, int v2){
        if(!vertices.containsKey(v1) || !vertices.containsKey(v2)){ System.out.println("未录入相应顶点"); return; }
        //生成 Edge 对象
        Edge newEdge = new Edge(v1, v2);
        //将新边 newEdge 加入图中，并更新边数量
        vertices.get(v1).adj.add(newEdge); vertices.get(v2).adj.add(newEdge); this.ENum++;
    }

    /********** 算法部分 **********/
    public void visit(Vertex vertex){ resultVertexList.add(vertex); }

    private HashSet<Vertex> visitedKey = new HashSet<>();
    //深度优先搜索（递归版）
    public void DFSRecur(Vertex root){
        //检查是否访问过
        if(root == null || visitedKey.contains(root)) return;  //访问过
        else visitedKey.add(root);  //未访问过，则加入set
        visit(root);  //访问顶点
        //根据邻接表，递归访问 v2 顶点
        for(Edge edge: root.adj){
            DFSRecur(vertices.get(edge.v2));
        }
    }
    //广度优先搜索
    public void BFS(){}
    //寻找和顶点 key 连通的所有顶点，结果存入 resultVertexList。思路：建立顶点 key 所在的极大连通子图（通过并查集或BFS或DFS搜索）。
    public void otherVerticesConnected(int key){ }

    public static void main(String[] args){
        /**
         * 0 - 1 - 3 - 4
         *   - 2
         */
        MyGraphAdjTable graph1 = new MyGraphAdjTable();
        for(int i=0; i<5; i++){  //录入顶点 0 1 2 3
            graph1.addVertex(i);
        }
        graph1.addEdge(0, 1); graph1.addEdge(0, 2); graph1.addEdge(1, 3); graph1.addEdge(3, 4);
        graph1.resultVertexList.clear();
        graph1.DFSRecur(graph1.vertices.get(0));
        for(Vertex vertex: graph1.resultVertexList)
            System.out.println(vertex.key + " ");
    }
}