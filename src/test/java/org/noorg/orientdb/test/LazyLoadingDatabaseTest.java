package org.noorg.orientdb.test;

import java.io.IOException;
import java.util.List;

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
	}

	@Test
	public void testCreate() {
		ItemRepository itemRepository = new ItemRepository();
		Item item1 = itemRepository.createItem("foo");
		Item item2 = itemRepository.createItem("bar");
		Item item3 = itemRepository.createItem("baz");

		ItemContainer container = itemRepository.createItemContainer("container");
		container.getItems().add(item1);
		container.getItems().add(item2);
		container.getItems().add(item3);

		// update container
		ODatabaseObjectTx db = Database.get();
		db.save(container);
		db.close();
	}

	@Test
	public void testFind() {
		ItemRepository itemRepository = new ItemRepository();
		ItemContainer container = itemRepository.findItemContainerByTitle("container");
		assert (container.getItems().size() == 3);
	}

	@Test
	public void testManualFind() {
		ODatabaseObjectTx db = Database.get();
		List<ItemContainer> result = db.query(new OSQLSynchQuery<ItemContainer>("select from ItemContainer where title = 'container'").setFetchPlan("*:-1"));
		db.close();

		for (ItemContainer c : result) {
			for (Item i : c.getItems()) {
				System.out.println("title: " + i.getTitle());
			}
		}
	}

}
