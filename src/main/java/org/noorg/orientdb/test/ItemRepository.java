package org.noorg.orientdb.test;

import java.util.List;

import org.noorg.orientdb.test.domain.Item;
import org.noorg.orientdb.test.domain.ItemContainer;

import com.orientechnologies.orient.core.db.object.ODatabaseObjectTx;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

public class ItemRepository {

	public Item createItem(String title) {
		Item item = new Item(title);
		ODatabaseObjectTx db = Database.get();
		db.save(item);
		db.close();
		return item;
	}
	
	public ItemContainer createItemContainer(String title) {
		ItemContainer itemContainer = new ItemContainer(title);
		ODatabaseObjectTx db = Database.get();
		db.save(itemContainer);
		db.close();
		return itemContainer;
	}
	
	public ItemContainer findItemContainerByTitle(String title) {
		ODatabaseObjectTx db = Database.get();
		List<ItemContainer> list = db.query(new OSQLSynchQuery<ItemContainer>("select from ItemContainer where title = '" + title + "'"));
		db.close();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
}
