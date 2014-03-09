package org.osbook.jobstore.utils;

public abstract class NumberUtils {

	public static boolean isNumber(String idOrName) {
		try {
			Long.parseLong(idOrName);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
