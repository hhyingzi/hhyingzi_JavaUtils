package MyJavaUtils;

/**
 * ��������ʱ���ʱ��
 * ͳ�����κ��뵥λ�ľ���ʱ�䣨���룩����������1000.0���õ�����ʱ�䣨�룩
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
