package org.noorg.orientdb.test.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.Version;

import com.google.common.collect.ImmutableList;

public class Page {

	@Id
	private Long id;

	@Version
	private Long version;

	private String uuid;

	private String title;

	private Page parentPage;

	private List<Page> subPages = new ArrayList<Page>();

	public Page() {
	}

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
		return ImmutableList.copyOf(subPages);
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

	protected void setParentPage(Page page) {
		if (parentPage != null && parentPage != page) {
			parentPage.removePage(this);
		}
		parentPage = page;
	}

	protected void removePage(Page page) {
		subPages.remove(page);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Page) {
			return ((Page) obj).getUuid().equals(this.getUuid());
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "(id=" + this.getId() + ", title=" + this.getTitle() + ", parentPage="
				+ (this.getParentPage() != null ? this.getParentPage().getId() : "null") + ")";
	}

	public Long getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}
	
}
