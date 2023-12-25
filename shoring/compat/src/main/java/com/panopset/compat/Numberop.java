package com.panopset.compat;

public class Numberop {

	public static int parseInt(String value) {
		if (!Stringop.isPopulated(value)) {
			return 0;
		} else {
			String str = value.replace(",", "");
			try {
				return Integer.parseInt(str);
			} catch (NumberFormatException var3) {
				Logop.dspmsg(value);
				Logop.handle(var3);
				return 0;
			}
		}
	}

	public static int parse(String value, Integer base, Integer defaultValue) {
		if (!Stringop.isPopulated(value)) {
			return defaultValue;
		} else {
			String str = value.replace(",", "");
			try {
				return Integer.parseInt(str, base);
			} catch (NumberFormatException var4) {
				Logop.handle(var4);
				return defaultValue;
			}
		}
	}

	public static int parse(String value, Integer base) {
		if (!Stringop.isPopulated(value)) {
			return -1;
		} else {
			String str = value.replace(",", "");

			try {
				return Integer.parseInt(str, base);
			} catch (NumberFormatException var4) {
				Logop.handle(var4);
				return -1;
			}
		}
	}

	public static boolean isNumber(String value) {
		if (value == null) {
			return false;
		}
		return value.matches("[0-9]*");
	}

	public static boolean isInteger(String value) {
		if (value == null) {
			return false;
		}
		return value.matches("-?\\d+");
	}
}
