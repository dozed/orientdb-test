package org.noorg.orientdb.test;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.noorg.orientdb.test.domain.Page;

public class ObjectGraphDatabaseTest2 {

	@Test
	public void stage1() throws IOException {
		Database.setupDatabase();

		Repository<Page> repo = Repository.get(Page.class);
		
		Page root = new Page("root");
		repo.save(root);
	}
	
	@Test
	public void testFind() throws IOException {
		Repository<Page> repo = Repository.get(Page.class);
		
		Page root = repo.find("title", "root");

		root.addPage(new Page("foo"));
		root.addPage(new Page("bar"));

		Page baz = new Page("baz");
		baz.addPage(new Page("nested"));
		root.addPage(baz);
		
		repo.save(root);
	}

	@Test
	public void testFindAll() {
		Repository<Page> repo = Repository.get(Page.class);
		List<Page> pages = repo.findAll();
		Assert.assertEquals(5, pages.size());
		System.out.println("listing pages:");
		for (Page page : pages) {
			System.out.println(pages.indexOf(page) + ": " + page.getTitle());
		}
		System.out.println("done.");
	}

}
