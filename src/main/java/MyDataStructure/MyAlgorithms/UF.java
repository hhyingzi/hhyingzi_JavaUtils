/******************************************************************************
 *  Data files:   https://algs4.cs.princeton.edu/15uf/tinyUF.txt
 *                https://algs4.cs.princeton.edu/15uf/mediumUF.txt
 *                https://algs4.cs.princeton.edu/15uf/largeUF.txt
 *
 *  % java UF < tinyUF.txt
 *  4 3
 *  3 8
 *  6 5
 *  9 4
 *  2 1
 *  5 0
 *  7 2
 *  6 1
 *  2 components
 *
 ******************************************************************************/

package MyDataStructure.MyAlgorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 并查集Java实现，来源于 《算法第四版》。
 * 功能：初始化一些顶点，并输入将两个顶点相连的边。将这些顶点组织成多个连通分量。
 * this.count 获取连通分量的数量，find(int p) 查找顶点所在的连通分量的根，connected(int p, int q) 查看两个顶点是否相联通
 */
public class UF {
    private int[] parent;  // 连通后指向父节点，可沿着父节点一路找到连通分量的根
    private int[] size;   // 每个连通分量所含节点（也可改为权重）
    private int count;     // 连通分量的数量

    //初始化n个节点 [0, n-1]，每个节点初始的的分量根节点parent是其自己。
    public UF(int n) {
        count = n;
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int count() {
        return count;
    }

    //给定一个节点，跟随其parent一路找到连通分量的根节点
    public int find(int p) {
        //如果p不是根，就一直往上走，顺便更新一次p的父节点
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];  //p的父节点每次访问只提一层，如果经常访问p才会越来越接近分量根。
            p = parent[p];
        }
        return p;
    }

    //检查节点 p 和 q 是否是连通的
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    //连通p和q节点（将二者所在的分量，合并为一个分量）。
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        //将小树的根节点连接到大树的根节点
        if(size[rootP] < size[rootQ]){
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];  //只需更新真正的根 rootQ 的 size 即可。
        }
        else{
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }

    public static void main(String[] args) {
        String tinyFilePath = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\tinyUF.txt";
        String mediumFilePath = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\mediumUF.txt";
        String largeFilePath = "D:\\code\\java\\hhyingzi_JavaUtils\\src\\Datas\\largeUF.txt";
        String filePath = tinyFilePath;
        try {
            File dataFile = new File(filePath);  //数据文件 dataFile
            Scanner sc = new Scanner(dataFile);
            int n = sc.nextInt();
            UF uf = new UF(n); //初始化UF类
            while (sc.hasNext()) {
                int p = sc.nextInt();
                int q = sc.nextInt();
                if (uf.find(p) == uf.find(q)) continue;  //如果已经连通，则 continue 掉。
                uf.union(p, q); //连通 p,q
                System.out.println(p + " " + q);
            }
            System.out.println(uf.count() + " components");
        } catch (FileNotFoundException e) {
            System.out.println("HH捕获异常，找不到文件：" + filePath);
        }
    }
}


