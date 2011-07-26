package org.noorg.orientdb.test.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.Version;

public class Page {
	
	@Id
	private Long id;
	
	@Version
	private Long version;
	
	private String uuid;

	private String title;

	private Page parentPage;
	
	private List<Page> subPages = new ArrayList<Page>();
	
	public Page() {}

	public Page(String title) {
		this.title = title;
		this.uuid = UUID.randomUUID().toString();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<Page> getSubPages() {
		return subPages;
	}
	
	public void setSubPages(List<Page> subPages) {
		this.subPages = subPages;
	}

	public Page getParentPage() {
		return parentPage;
	}
	
	public void addPage(Page page) {
		if (!subPages.contains(page)) {
			subPages.add(page);
			page.setParentPage(this);
		}
	}
	
	public void setParentPage(Page parentPage) {
		this.parentPage = parentPage;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Page) {
			return ((Page)obj).getUuid().equals(this.getUuid());
		}
		return super.equals(obj);
	}
	
}
