package MyJavaUtils;

/**
 * 程序运行时间计时器
 * 统计两次毫秒单位的绝对时间（毫秒），相减后除以1000.0，得到运行时间（秒）
 */
public class Stopwatch {
	private long start;
	public void start() {
		start = System.currentTimeMillis();
		System.out.println("Program Running...");
	}
	
	public void end() {
		long end = System.currentTimeMillis();
		System.out.println("Finished! ");
		System.out.println("Time used " + (end-start)/1000.0);
	}
}
