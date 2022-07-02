/******************************************************************************
 *  Compilation:  javac UF.java
 *  Execution:    java UF < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/15uf/tinyUF.txt
 *                https://algs4.cs.princeton.edu/15uf/mediumUF.txt
 *                https://algs4.cs.princeton.edu/15uf/largeUF.txt
 *
 *  Weighted quick-union by rank with path compression by halving.
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

package TempProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UF {
    private int[] parent;  // parent[i] = parent of i
    private byte[] rank;   // rank[i] = rank of subtree rooted at i (never more than 31)
    private int count;     // number of components

    public UF(int n) {
        if (n < 0) throw new IllegalArgumentException();
        count = n;
        parent = new int[n];
        rank = new byte[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int p) {
        validate(p);
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];    // path compression by halving
            p = parent[p];
        }
        return p;
    }

    public int count() {
        return count;
    }

    @Deprecated
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make root of smaller rank point to root of larger rank
        if      (rank[rootP] < rank[rootQ]) parent[rootP] = rootQ;
        else if (rank[rootP] > rank[rootQ]) parent[rootQ] = rootP;
        else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        count--;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
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


