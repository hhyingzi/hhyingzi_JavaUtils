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
		ArrayList<Object> arrayList = new ArrayList<>();
		arrayList.add(123);
		arrayList.add("Hello");
		arrayList.forEach((item) -> System.out.println(item));
		/* 以上放入程序 */
		watch.end();
	}

	public void test(){
	}
}
