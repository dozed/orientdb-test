package org.noorg.orientdb.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.noorg.orientdb.test.domain.Item;
import org.noorg.orientdb.test.domain.ItemContainer;
import org.noorg.orientdb.test.domain.Page;

import com.orientechnologies.orient.core.db.object.ODatabaseObjectPool;
import com.orientechnologies.orient.core.db.object.ODatabaseObjectTx;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import com.orientechnologies.orient.core.serialization.serializer.object.OObjectSerializer;
import com.orientechnologies.orient.core.serialization.serializer.object.OObjectSerializerContext;

public class Database {

	public static ODatabaseObjectTx get() {
		ODatabaseObjectTx db = ODatabaseObjectPool.global().acquire("local:target/orient", "admin", "admin");
		registerClasses(db);
		return db;
	}
	
	public static void initDatabase() {
		ODatabaseObjectTx db = ODatabaseObjectPool.global().acquire("local:target/orient", "admin", "admin");
		registerClasses(db);
		setupSerializers(db);
		db.close();
	}
	
	public static void setupDatabase() throws IOException {
		FileUtils.deleteDirectory(new File("target/orient"));
		ODatabaseObjectTx db = new ODatabaseObjectTx("local:target/orient").create();
		createClasses(db);
		registerClasses(db);
		setupSerializers(db);
		db.close();
	}

	private static void registerClasses(ODatabaseObjectTx db) {
		db.getEntityManager().registerEntityClasses("org.noorg.orientdb.test.domain");
		db.getEntityManager().registerEntityClass(Item.class);
		db.getEntityManager().registerEntityClass(ItemContainer.class);
		db.getEntityManager().registerEntityClass(Page.class);
	}
	
	private static void createClasses(ODatabaseObjectTx db) {
		createClass(db, "Item");
		createClass(db, "ItemContainer");
		createClass(db, "Page");
	}
	
	private static void createClass(ODatabaseObjectTx db, String className) {
		OSchema schema = db.getMetadata().getSchema();
		if (!schema.existsClass(className)) {
			schema.createClass(className);
		}
	}

	private static void setupSerializers(ODatabaseObjectTx db) {
		OObjectSerializerContext serializerContext = new OObjectSerializerContext();
		serializerContext.bind(new OObjectSerializer<DateTime, Long>() {
			public Long serializeFieldValue(Object iPojo, String iFieldName, DateTime iFieldValue) {
				return iFieldValue.getMillis();
			}

			public DateTime unserializeFieldValue(Object iPojo, String iFieldName, Long iFieldValue) {
				return new DateTime(iFieldValue);
			}
		});
	}
	
}
