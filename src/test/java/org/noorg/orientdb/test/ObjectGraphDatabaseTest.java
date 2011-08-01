package org.noorg.orientdb.test;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.noorg.orientdb.test.domain.Page;

public class ObjectGraphDatabaseTest {

	@BeforeClass
	public static void setup() throws IOException {
		Database.setupDatabase();

		Repository<Page> repo = Repository.get(Page.class);
		
		Page root = new Page("root");
		repo.save(root);

		root.addPage(new Page("foo"));
		root.addPage(new Page("bar"));

		Page baz = new Page("baz");
		baz.addPage(new Page("nested"));
		root.addPage(baz);
		
		repo.save(root);
	}
	
	@Test
	public void test() {
		Repository<Page> repo = Repository.get(Page.class);
		Page root = repo.find("title", "root");
		goDown(root, 0);
	}
	
	@Test
	public void testFindAll() {
		Repository<Page> repo = Repository.get(Page.class);
		List<Page> pages = repo.findAll();
		Assert.assertEquals(pages.size(), 5);
	}

	@Test
	public void testFind() {
		Repository<Page> repo = Repository.get(Page.class);
		Page baz = repo.find("title", "baz");
		baz.addPage(new Page("nested2"));
		repo.save(baz);

		Page nested = repo.find("title", "nested2");
		Assert.assertNotNull(nested);
	}

	private void goDown(Page page, int level) {
		for (int i = 0; i < level; i++ ) {
			System.out.print("--");
		}
		System.out.println(page.getTitle());
		for (Page p : page.getSubPages()) {
			goDown(p, level+1);
		}
	}


}
