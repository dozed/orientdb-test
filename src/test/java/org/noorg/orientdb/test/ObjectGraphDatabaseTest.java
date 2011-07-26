package org.noorg.orientdb.test;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.noorg.orientdb.test.domain.Page;

public class ObjectGraphDatabaseTest {

	@BeforeClass
	public static void setup() throws IOException {
		Database.setupDatabase();

		Repository<Page> repo = Repository.get(Page.class);
		
		Page root = new Page("root");
		root.addPage(new Page("foo"));
		root.addPage(new Page("bar"));
		root.addPage(new Page("baz"));
		
		repo.save(root);
	}
	
	@Test
	public void test() {
		Repository<Page> repo = Repository.get(Page.class);
		Page root = repo.find("title", "root");

		for (Page p : root.getSubPages()) {
			System.out.println(p.getTitle());
		}
	}


}
