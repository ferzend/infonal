package com.infonal.dao;

import com.infonal.converter.UserConverter;
import com.infonal.model.User;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOTest {

    @Mock
    DBCollection dbCollection;

    @Mock
    DBObject dbObject;

    UserConverter userConverter = new UserConverter();

    private UserDAO userDAO;

    private User user;

    @Before
    public void beforeClass() {
        userDAO = new UserDAO(dbCollection, userConverter);
        user = new User();
        user.setId("57c1d2c744aebb0e690368fd");
    }

    @Test
    public void shouldInsertUser () {
        userDAO.createUser(user);
        verify(dbCollection, times(1)).insert(any(DBObject.class));
    }

    @Test
    public void shouldUpdateUser () {
        userDAO.updateUser(user);
        verify(dbCollection, times(1)).update(any(DBObject.class), any(DBObject.class));
    }

    @Test
    public void shouldDeleteUser() {
        userDAO.deleteUser(user);
        verify(dbCollection, times(1)).remove(any(DBObject.class));
    }

    @Test
    public void shouldReadUser () {
        ObjectId objectId = new ObjectId();

        when(dbObject.get("name")).thenReturn("");
        when(dbObject.get("surname")).thenReturn("");
        when(dbObject.get("_id")).thenReturn(objectId);
        when(dbCollection.findOne(any(DBObject.class))).thenReturn(dbObject);
        userDAO.readUser(user);
        verify(dbCollection, times(1)).findOne(any(DBObject.class));
    }
}