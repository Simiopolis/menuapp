package com.skkl.menuapp.locu;

import java.util.List;

public class LocuResult {
	
	private List<Business> objects;
	private Meta meta;
	
	public List<Business> getObjects() {
		return objects;
	}

	public void setObjects(List<Business> objects) {
		this.objects = objects;
	}

	public Meta getMeta() {
		return meta;
	}
	
	public void setMeta(Meta meta) {
		this.meta = meta;
	}

}
