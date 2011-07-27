package org.noorg.orientdb.test;

import java.util.ArrayList;
import java.util.List;

import com.orientechnologies.orient.core.db.object.ODatabaseObjectTx;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

public class Repository<T> {

	public static <T> Repository<T> get(Class<T> clazz) {
		return new Repository<T>(clazz);
	}

	private Class<?> targetClass;
	
	public Repository(Class<?> clazz) {
		targetClass = clazz;
	}
	
	public List<T> findAll() {
		List<T> result = new ArrayList<T>();
		ODatabaseObjectTx db = Database.get();
		try {
			String className = targetClass.getSimpleName();
			result = db.query(new OSQLSynchQuery<T>("select from " + className).setFetchPlan("*:-1"));
		} finally {
			db.close();
		}
		return result;
	}

	public T find(String uuid) {
		return find("uuid", uuid);
	}
	
	public T find(String key, String value) {
		ODatabaseObjectTx db = Database.get();
		List<T> result = new ArrayList<T>();
		try {
			result = db.query(new OSQLSynchQuery<T>("select from " + targetClass.getSimpleName() + " where " + key + " = '" + value + "'").setFetchPlan("*:-1"));
		} finally {
			db.close();
		}
		if (result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}
	
	public void save(T object) {
		ODatabaseObjectTx db = Database.get();
		try {
			db.save(object);
		} finally {
			db.close();
		}
	}
	
}
