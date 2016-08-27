package com.infonal.dao;

import com.infonal.converter.UserConverter;
import com.infonal.model.User;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

	private UserConverter userConverter;
	private DBCollection col;

	public UserDAO(MongoClient mongo, UserConverter userConverter) {
		this.col = mongo.getDB("infonalUsers").getCollection("Users");
		this.userConverter = userConverter;
	}

	protected UserDAO(DBCollection col, UserConverter userConverter) {
		this.col = col;
		this.userConverter = userConverter;
	}

	public User createUser(User user) {
		DBObject doc = userConverter.toDBObject(user);
		this.col.insert(doc);
		ObjectId id = (ObjectId) doc.get("_id");
		user.setId(id.toString());
		return user;
	}

	public void updateUser(User user) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(user.getId())).get();
		this.col.update(query, userConverter.toDBObject(user));
	}

	public List<User> readAllUser() {
		List<User> data = new ArrayList<User>();
		DBCursor cursor = col.find();
		while (cursor.hasNext()) {
			DBObject doc = cursor.next();
			User p = userConverter.toPerson(doc);
			data.add(p);
		}
		return data;
	}

	public void deleteUser(User user) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(user.getId())).get();
		this.col.remove(query);
	}

	public User readUser(User user) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(user.getId())).get();
		DBObject data = this.col.findOne(query);
		return userConverter.toPerson(data);
	}

}
