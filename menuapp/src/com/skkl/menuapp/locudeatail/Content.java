package com.skkl.menuapp.locudeatail;

import java.util.List;

public class Content {

	private String price, type, name, description, text;
	private List<OptionGroups> optionGroups;

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<OptionGroups> getOptionGroups() {
		return optionGroups;
	}

	public void setOptionGroups(List<OptionGroups> optionGroups) {
		this.optionGroups = optionGroups;
	}
	
}
