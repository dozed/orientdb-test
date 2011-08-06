package org.noorg.orientdb.test;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.noorg.orientdb.test.domain.Page;

public class RewiringTest {

	@BeforeClass
	public static void setup() throws IOException {
		Database.setupDatabase();

		Repository<Page> repo = Repository.get(Page.class);
		Page root = new Page("root");
		root.addPage(new Page("foo"));
		root.addPage(new Page("bar"));
		Page baz = new Page("baz");
		baz.addPage(new Page("nested"));
		root.addPage(baz);
		repo.save(root);

		PagePrinter.print(repo.find("title", "root"));
	}
	
	@Test
	public void testMovePage() {
		Repository<Page> repo = Repository.get(Page.class);
		
		Page nested = repo.find("title", "nested");
		Page foo = repo.find("title", "foo");
		Page formerParent = foo.getParentPage();
		
		int formerParentSize = formerParent.getSubPages().size();
		
		nested.addPage(foo);
		repo.save(formerParent);
		repo.save(nested);
		
		Page root = repo.find("title", "root");
		PagePrinter.print(root);
		
		formerParent = repo.find("title", formerParent.getTitle());
		Assert.assertEquals(formerParentSize - 1, formerParent.getSubPages().size());
	}

}
