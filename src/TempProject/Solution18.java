package TempProject;
//思路：X -> H 相差为 diff
//如果 X == H
//如果 X > H, X-1 直到相等。使用while
//如果 X < H， X = X*2直到相等。使用while。

public class Solution18 {
    public static void main(String[] args){
        test(15, 5);
    }
    public static void test(int X, int H){
        int diff = H - X;
        int execNum=0;
        while(X != H){
            if(X > H){
                X -= 1;
                execNum++;
            }
            else if(X < H){
                X *= 2;
                execNum++;
            }
        }
        System.out.println(execNum);
    }
}
