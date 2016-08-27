package com.infonal.converter;

import com.infonal.model.User;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

public class UserConverter {
	public DBObject toDBObject(User p) {

		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
				.append("name", p.getName()).append("surname", p.getSurname());
		if (p.getId() != null)
			builder = builder.append("_id", new ObjectId(p.getId()));
		return builder.get();
	}

	public User toPerson(DBObject doc) {
		User p = new User();
		p.setName((String) doc.get("name"));
		p.setSurname((String) doc.get("surname"));
		ObjectId id = (ObjectId) doc.get("_id");
		p.setId(id.toString());
		return p;

	}
	
}
