package business;

import core.Helper;
import dao.UserDao;
import entities.User;

public class UserController {
    private final UserDao userDao = new UserDao();

    public User findByLogin(String mail, String password) {
        if (!Helper.isEmailValid(mail)) return null;    // for security check
        return this.userDao.findByLogin(mail, password);
    }
}
