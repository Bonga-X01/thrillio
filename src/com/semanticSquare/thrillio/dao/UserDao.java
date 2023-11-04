package com.semanticSquare.thrillio.dao;

import com.semanticSquare.thrillio.DataStore;
import com.semanticSquare.thrillio.entities.User;

public class UserDao {
    public User[] getUsers() {
        return DataStore.getUsers();
    }
}
