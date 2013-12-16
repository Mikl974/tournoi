package com.bretibad.tournoibretibad.utils;

public class StringUtils {
	public static String toCamelCase(String string) {
		StringBuffer sb = new StringBuffer(string);
		sb.replace(0, 1, string.substring(0, 1).toUpperCase());
		return sb.toString();

	}
}
