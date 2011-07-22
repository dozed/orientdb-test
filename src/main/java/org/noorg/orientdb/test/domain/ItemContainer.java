package org.noorg.orientdb.test.domain;

import java.util.ArrayList;
import java.util.List;

public class ItemContainer extends Item {

	private List<Item> items = new ArrayList<Item>();

	ItemContainer() {}
	
	public ItemContainer(String title) {
		super(title);
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
