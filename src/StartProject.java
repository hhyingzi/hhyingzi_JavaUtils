import MyDataStructure.BasicStructure.MyGraphAdjTable;
import MyUtils.*;

import java.util.*;

public class StartProject {

	public static void main(String[] args) {
		Stopwatch watch = new Stopwatch();
		watch.start();
		/* 以下放入程序 */

		MyIO myIO = new MyIO();
		myIO.MyFileToString();

		/* 以上放入程序 */
		watch.end();
	}

	public void test(){
		for(;;){
			for(int i=0; i<9600000; i++) ;
			try{
				Thread.sleep(10);
			}catch (Exception e){}
		}
	}
}
