package com.matson.cas.cache.sample.vo;

public class MyCachedItem {

	private String key;
	private String value;
	
	public MyCachedItem(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String toString() {
		return "[key=" + key + ", value=" + value + "]";
	}
}
