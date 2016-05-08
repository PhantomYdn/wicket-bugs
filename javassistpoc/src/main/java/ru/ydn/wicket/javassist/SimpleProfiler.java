package ru.ydn.wicket.javassist;

import java.util.Stack;

import org.apache.wicket.util.string.Strings;

public class SimpleProfiler {
	
	private static final ThreadLocal<Stack<Long>> STACK = new ThreadLocal<Stack<Long>>() {
		protected java.util.Stack<Long> initialValue() {
			return new Stack<>();
		};
	};
	
	public static void push(String name, long time) {
		STACK.get().push(time);
	}
	
	public static void pop(String name, long time) {
		Stack<Long> stack = STACK.get();
		long start = stack.pop();
		StringBuilder sb = new StringBuilder(64);
		for(int i=0; i<stack.size();i++)sb.append(' ');
		sb.append(name).append(" call took ").append(time-start).append("ns");
		System.out.println(sb);
	}
	
}
