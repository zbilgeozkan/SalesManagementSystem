package view;

import business.UserController;
import core.Helper;
import entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private JPanel container;
    private JPanel panel_top;
    private JLabel label_title;
    private JPanel panel_bottom;
    private JTextField field_mail;
    private JButton button_login;
    private JLabel label_mail;
    private JLabel label_password;
    private JPasswordField field_password;
    private UserController userController;

    public LoginUI() {

        this.userController = new UserController();

        this.add(container);
        this.setTitle("Customer Management System");
        this.setSize(400, 400);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);

        this.setVisible(true);

        this.button_login.addActionListener(e -> {
            JTextField[] checklist = {this.field_mail, this.field_password};
            if (!Helper.isEmailValid(this.field_mail.getText())) {
                Helper.showAlert("Please indicate a valid email.");
            } else if (Helper.isFieldListEmpty(checklist)) {
                Helper.showAlert("fill");
            } else {
                User user = this.userController.findByLogin(this.field_mail.getText(), this.field_password.getText());
                if (user == null) {
                    Helper.showAlert("Invalid Login");
                } else {
                    this.dispose();
                    DashboardUI dashboardUI = new DashboardUI(user);
                }
            }
        });
    }
}
