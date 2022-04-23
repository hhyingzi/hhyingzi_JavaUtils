package MyUtils;

/**
 * 计时秒表，记录程序运行时间
 */
public class Stopwatch {
	private long start;
	public void start() {
		start = System.currentTimeMillis();
		System.out.println("########## Start ############");
	}
	
	public void end() {
		long end = System.currentTimeMillis();
		System.out.println("######### Finished! ######### \n Time used " + (end-start)/1000.0);
	}
}
