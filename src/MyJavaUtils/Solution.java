package MyJavaUtils;

public class Solution {
    public void run(){
        yanghui(12);
    }

    public void yanghui(int rowNum){
        int[][] all = new int[rowNum][rowNum];
        int maxColOfRow=1;
        int spacenumOfRow = rowNum;
        for(int i=0; i<rowNum; i++){
            for(int spaceNum = 0; spaceNum<spacenumOfRow; spaceNum++)
                System.out.print(" ");
            for(int j=0; j<maxColOfRow; j++){
                if(j==0){
                    all[i][j] = 1;
                    System.out.print(1);
                }
                else if(j==maxColOfRow-1){
                    all[i][j] = 1;
                    System.out.print(" " + 1);
                }
                else{
                    int temp = all[i-1][j-1] + all[i-1][j];
                    all[i][j] = temp;
                    System.out.print(" " + temp);
                }
            }
            System.out.println();
            maxColOfRow++;
            spacenumOfRow--;
        }
    }
}
