package MySolutions.LeetCode;

/*
* 斐波那契数 （通常用 F(n) 表示）形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
* F(0) = 0，F(1) = 1
* F(n) = F(n - 1) + F(n - 2)，其中 n > 1
* [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, ...]
* */
public class ProgrammingThoughts_Dynamic_509_fib {
    public int fib(int n) {
        if(n==0) return 0;
        if(n==1) return 1;
        int prev_prev=0, prev=1, curr=-1;
        for(int i=2; i<=n; i++){
            curr = prev_prev + prev;
            prev_prev = prev;
            prev = curr;
        }
        return curr;
    }

    public static void main(String[] args){
        ProgrammingThoughts_Dynamic_509_fib solution = new ProgrammingThoughts_Dynamic_509_fib();

        for(int i=0; i<=10; i++){
            System.out.print(solution.fib(i));
            if(i!=10) System.out.print(" --> ");
        }
    }
}
