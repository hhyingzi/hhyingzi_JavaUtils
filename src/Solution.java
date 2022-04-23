import java.util.Scanner;
public class Solution {
        public static void main(String[] args) {
            // please define the JAVA input here. For example: Scanner s = new Scanner(System.in);
            // please finish the function body here.
            // please define the JAVA output here. For example: System.out.println(s.nextInt());
            Scanner sc = new Scanner(System.in);
            int M = sc.nextInt();
            int N = sc.nextInt();
            int aIndex = 0, aId=1;
            int tailIndex = 1;
            char[] chs = new char[N];

            // 输出项
            int index=0, id=0;

            for(int i=0; i<N; i++){
                chs[i] = sc.next().charAt(0);
                if(chs[i] == 'A'){
                    //不换A芯片
                    if(aId < 4){
                        if(aIndex==0){
                            if(tailIndex > M){
                                System.out.println(0);
                                System.out.println(0);
                                return;
                            }
                            aIndex = tailIndex;
                            tailIndex++;
                        }
                        id = aId;
                        index = aIndex;

                        aId++;
                    }
                    //更换A芯片
                    else if(tailIndex <= M){
                        index = aIndex = tailIndex;
                        tailIndex++;
                        id = 1;
                        aId = 2;
                    }
                    else{
                        System.out.println(0);
                        System.out.println(0);
                        return;
                    }
                }
                else if(chs[i] == 'B'){
                    if(tailIndex <= M){
                        index = tailIndex;
                        tailIndex++;
                        id = 1;
                    }
                    else{
                        System.out.println(0);
                        System.out.println(0);
                        return;
                    }
                }
            }//for
            System.out.println(index);
            System.out.println(id);
        }
}
