package org.noorg.orientdb.test;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.noorg.orientdb.test.domain.Item;
import org.noorg.orientdb.test.domain.ItemContainer;

import com.orientechnologies.orient.core.db.object.ODatabaseObjectTx;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

public class LazyLoadingDatabaseTest {

	@BeforeClass
	public static void setup() throws IOException {
		Database.setupDatabase();

		// create first item container
		ItemContainer container = new ItemContainer("container");
		container.getItems().add(new Item("foo"));
		container.getItems().add(new Item("bar"));
		container.getItems().add(new Item("baz"));

		ODatabaseObjectTx db = Database.get();
		db.save(container);
		db.close();

		// create second item container with repository
		ItemRepository itemRepository = new ItemRepository();
		ItemContainer container2 = itemRepository.createItemContainer("container 2");
		container2.getItems().add(itemRepository.createItem("a"));
		container2.getItems().add(itemRepository.createItem("b"));
		container2.getItems().add(itemRepository.createItem("c"));
		
		db = Database.get();
		db.save(container2);
		db.close();
	}

	@Test
	public void testFind() {
		ItemRepository itemRepository = new ItemRepository();
		ItemContainer container = itemRepository.findItemContainerByTitle("container");
		Assert.assertEquals(3, container.getItems().size());
		for (Item i : container.getItems()) {
			System.out.println("title: " + i.getTitle());
		}
	}

	@Test
	public void testManualFind() {
		ODatabaseObjectTx db = Database.get();
		List<ItemContainer> result = db.query(new OSQLSynchQuery<ItemContainer>("select from ItemContainer where title = 'container'"));
		for (ItemContainer c : result) {
			for (Item i : c.getItems()) {
				System.out.println("title: " + i.getTitle());
			}
		}
		db.close();
	}

	@Test
	public void testFind2() {
		ItemRepository itemRepository = new ItemRepository();
		ItemContainer container = itemRepository.findItemContainerByTitle("container 2");
		Assert.assertEquals(3, container.getItems().size());
		for (Item i : container.getItems()) {
			System.out.println("title: " + i.getTitle());
		}
	}

	@Test
	public void testFindDuplicates() {
		ODatabaseObjectTx db = Database.get();
		List<ItemContainer> result = db.query(new OSQLSynchQuery<ItemContainer>("select from ItemContainer where title = 'container 2'"));
		db.close();
		Assert.assertEquals(1, result.size());
	}

}
