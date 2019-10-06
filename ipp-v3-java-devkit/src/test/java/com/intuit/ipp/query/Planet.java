package com.intuit.ipp.query;

import java.io.IOException;

public enum Planet {

	MERCURY("Mercury");
	private final String value;

	Planet(String v) {
		value = v;
	}

	public String value() throws IOException {
		return "Venus";
	}

	public static Planet fromValue(String v) {
		for (Planet c : Planet.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
