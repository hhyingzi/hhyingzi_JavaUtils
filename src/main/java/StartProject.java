import MyUtils.*;



public class StartProject {
	public static void main(String[] args) {
		Stopwatch watch = new Stopwatch();
		StartProject startProject = new StartProject();
		watch.start();
		/* 以下放入程序 */
		System.out.println("hello");

	}

	public int test(int i){
		i++;
		for(int j=0; j<3; j++) i++;
		return i;
	}
}
