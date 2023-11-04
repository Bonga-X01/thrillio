package com.semanticSquare.thrillio.managers;

import com.semanticSquare.thrillio.dao.UserDao;
import com.semanticSquare.thrillio.entities.User;

public class UserManager {//Singleton
    private static UserManager instance = new UserManager();
    public static UserDao dao = new UserDao();
    private UserManager() { }
    public static UserManager getInstance() {
        return instance;
    }
    public User createUser (long id, String email, String password, String firstName, String lastName, int gender, String userType) {
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setGender(gender);
        user.setUserType(userType);

        return user;
    }
    public User[] getUsers() {
        return dao.getUsers();
    }
}
