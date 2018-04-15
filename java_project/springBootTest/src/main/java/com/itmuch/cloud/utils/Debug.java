package com.itmuch.cloud.utils;

public class Debug {
	/**
	 * 格式化输出，打印信息到控制台
	 * 
	 * @param format
	 * @param args
	 * @author xxj 2017年4月26日
	 */
	public static void printFormat(String format, Object... args) {
		if (args == null) {
			System.out.println(format);
		}
		System.out.println(java.text.MessageFormat.format(format, args));
	}

	/**
	 * 格式化输出，打印信息到控制台
	 * 
	 * @param format
	 * @param args
	 * @author xxj 2017年4月26日
	 */
	public static void print(Object... msg) {
		if (msg == null) {
			return;
		}
		for (Object x : msg) {
			System.out.print(x);
			// System.out.print(' ');
		}
		System.out.println();
	}

	/**
	 * 格式化输出，打印信息到控制台
	 * 
	 * @param format
	 * @param args
	 * @author xxj 2017年4月26日
	 */
	public static void println(Object... msg) {
		if (msg == null) {
			return;
		}
		for (Object x : msg)
			System.out.println(x);
	}

	/**
	 * 打印当前线程的调用堆栈
	 * 
	 * @author xxj 2017年4月26日
	 */
	public static void printTrack() {
		StackTraceElement[] st = Thread.currentThread().getStackTrace();
		if (st == null) {
			System.out.println("无堆栈...");
			return;
		}
		StringBuffer sbf = new StringBuffer();
		sbf.append(StringExtend.format("调用堆栈[{0}]：", StringExtend.getString(new java.util.Date())));
		for (StackTraceElement e : st) {
			sbf.append(StringExtend.format(" {0}.{1}() {2} <- {3}", e.getClassName(), e.getMethodName(),
					e.getLineNumber(), StringExtend.getEnterMark()));
		}
		System.out.println(sbf.toString());
	}
}
