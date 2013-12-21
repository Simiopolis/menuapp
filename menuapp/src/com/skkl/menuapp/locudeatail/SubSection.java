package com.skkl.menuapp.locudeatail;

import java.util.List;

public class SubSection {

	private String subsection_name;
	private List<Content> contents;
	
	public String getSubsection_name() {
		return subsection_name;
	}
	
	public void setSubsection_name(String subsection_name) {
		this.subsection_name = subsection_name;
	}
	
	public List<Content> getContents() {
		return contents;
	}
	
	public void setContents(List<Content> contents) {
		this.contents = contents;
	}
	
}
