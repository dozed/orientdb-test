package org.noorg.orientdb.test.domain;

import javax.persistence.Id;
import javax.persistence.Version;

public class Item {

	@Id
	private Long id;
	
	@Version
	private Long version;

	private String title;

	Item() {}
	
	public Item(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public Long getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

}
