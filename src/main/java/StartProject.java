import MyUtils.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.*;

public class StartProject {
	public static void main(String[] args) {
		Stopwatch watch = new Stopwatch();
		StartProject startProject = new StartProject();
		watch.start();
		/* 以下放入程序 */
		int i=0;
		i = startProject.test(i);
		System.out.println(i);
		/* 以上放入程序 */
		watch.end();

		int x = 5;

		if(true) x = 10;

		System.out.println("Outside runnable: " + x);
	}

	public int test(int i){
		i++;
		for(int j=0; j<3; j++) i++;
		return i;
	}
}
