package com.epam.jsf.util;

public class SQLQueryUtil {

	public static String generateQsForIn(int numQs) { 
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < numQs; i++) {
			if (i != 0) {
				builder.append(", ");
			}
			builder.append("?");
		}
		return builder.toString();
	}
}