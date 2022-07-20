import MyUtils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StartProject {

	public static void main(String[] args) {
		Stopwatch watch = new Stopwatch();
		watch.start();
		/* 以下放入程序 */

		test();

		/* 以上放入程序 */
		watch.end();
	}

	public static void test(){
		List<Person> list = new ArrayList<>(100);
		list.add(new Person("Person1", 20));
		list.add(new Person("Person2", 10));
		list.add(new Person("Person3", 30));
		list.forEach(System.out::println);
		System.out.println("自然顺序排序：");
		Collections.sort(list);
		list.forEach(System.out::println);
		System.out.println("使用比较器逆序排序：");
		Collections.sort(list, new DescAgeComparator());
		list.forEach(System.out::println);
	}

}
