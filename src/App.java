import business.UserController;
import core.DB;
import core.Helper;
import entities.User;
import view.DashboardUI;
import view.LoginUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {

        Helper.setTheme();
        // LoginUI loginUI = new LoginUI();
        UserController userController = new UserController();
        User user = userController.findByLogin("bilge@mail.com", "bilge123");
        DashboardUI dashboardUI = new DashboardUI(user);
    }
}
