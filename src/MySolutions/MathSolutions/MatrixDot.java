package MySolutions.MathSolutions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 矩阵相乘算法
 * 实现一：矩阵乘以向量的标准算法
 * 实现二：矩阵乘以向量的稀疏矩阵解决方案。
 *      原理：矩阵有M行N列，则每行用一个 HashMap 来存放元素，每个元素的 key 是秩， value 是值，由此构成稀疏矩阵。
 *      在计算时，对每一行 a[i] 的 HashMap ，遍历其中所有的 key，每一个key都用 a[i].get(Key) 与 x 对应的 x[key] 相乘，最后加起来的 sum 就是这一行的结果元素。
 *      好处是稀疏矩阵 a 的每一行可能只有少量元素，对每个元素进行计算，不存在的元素无需计算，大大节省了时间。
 */
public class MatrixDot {
    //a[M][N] * x[N] = b[N]
    /**
     *   | a11, a12, ..., a1N |        |x1|         | a11*x1 + a12*x2 + ... + a1N*xN |
     *   | a21, a22, ..., a2N |        |x2|         | a21*x1 + a22*x2 + ... + a2N*xN |
     *   | ...                |  *      ...   =     | ...                            |
     *   | am1, am2, ..., amN |        |xN|         | am1*x1 + am2*x2 + ... + amN*xN |
     */
    int M, N;  //M行，N列
    public HashMap<Integer, Double>[] a;  //稀疏矩阵 a ，由 M 行 HashMap 组成。每个元素代表当前行的第 key（且key不重复） 个元素，值为 value。
    public double[] x;  //x向量
    public double[] b;  //结果向量
    public MatrixDot(){}
    public MatrixDot(int M, int N){  //构造一个 M行的矩阵 a
        this.M = M; this.N = N;
        a = new HashMap[M];
        for(int i=0; i<M; i++) a[i] = new HashMap<>();
        b = new double[M];
    }

    //基于稀疏向量的矩阵相乘，默认 稀疏矩阵a, 向量b 已经初始化好。
    public void MatrixDotSparseVector(){
        for(int i=0; i<M; i++){
            double sum = 0.0;
            for(int key: a[i].keySet()){
                sum += a[i].get(key) * x[key];
            }
            b[i] = sum;
        }
        System.out.println(Arrays.toString(b));
    }

    //基于二维数组的矩阵相乘
    //a[M][N] * x[N] = b[N]
    /**
     *   | a11, a12, ..., a1N |        |x1|         | a11*x1 + a12*x2 + ... + a1N*xN |
     *   | a21, a22, ..., a2N |        |x2|         | a21*x1 + a22*x2 + ... + a2N*xN |
     *   | ...                |  *      ...   =     | ...                            |
     *   | am1, am2, ..., amN |        |xN|         | am1*x1 + am2*x2 + ... + amN*xN |
     */
    public void MatrixDotArray(double[][] a, double[] x, double[] b){
        int M = a.length;  //a 有 M 行数据
        int N = x.length;  //a的每行 或 x的每列 有多少元素
        for(int i=0; i<M; i++){
            double sum = 0.0;
            for(int j = 0; j < N; j++){
                sum+=a[i][j] * x[j];
            }
            b[i] = sum;
        }
    }

    public static void main(String[] args){
        //测试数组矩阵相乘
        int M = 3, N = 4;
        double[][] a = new double[][]{{1, 2, 3, 4},
                                      {1, 2, 3, 4},
                                      {1, 2, 3, 4}};
        double[] x = new double[]{1, 2, 3, 4};
        double[] b = new double[M];
        MatrixDot matrixDot1 = new MatrixDot();
        matrixDot1.MatrixDotArray(a, x, b);
        System.out.println(Arrays.toString(b));
        System.out.println("=============");

        //测试稀疏矩阵相乘
        int M2 = 3, N2 = 4;
        MatrixDot matrixDot2 = new MatrixDot(M2, N2);
        //初始化矩阵a
        for(int i=0; i<M2; i++){
            for(int j=0; j<N2; j++){
                matrixDot2.a[i].put(j, j+1.0);  //对每行依次录入M2个数据 1.0, 2.0, 3.0, 4.0
            }
        }
        //初始化矩阵x
        matrixDot2.x = new double[]{1.0, 2.0, 3.0, 4.0};
        matrixDot2.MatrixDotSparseVector();
    }
}
