package com.nhsoft.module.report.dto;

import java.io.Serializable;

public class Tuple<K, V> implements Serializable {

	private static final long serialVersionUID = 6272509620718683282L;
	public K first;
	public V second;
	
	public static <K, V> Tuple<K, V> makeTuple(K k, V v) {
		Tuple<K, V> t = new Tuple<K, V>();
		t.first = k;
		t.second = v;
		return t;
	}
}
