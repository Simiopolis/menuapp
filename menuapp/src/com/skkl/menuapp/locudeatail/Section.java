package com.skkl.menuapp.locudeatail;

import java.util.List;

public class Section {
	
	private List<Subsection> subsections;
	private String section_name;

	public List<Subsection> getSubsections() {
		return subsections;
	}

	public void setSubsections(List<Subsection> subsections) {
		this.subsections = subsections;
	}

	public String getSection_name() {
		return section_name;
	}

	public void setSection_name(String section_name) {
		this.section_name = section_name;
	}
	
}
