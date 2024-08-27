package view;

import business.CustomerController;
import core.Helper;
import entities.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerUI extends JFrame {
    private JPanel container;
    private JLabel label_title;
    private JLabel lable_customer_name;
    private JTextField field_customer_name;
    private JLabel label_customer_type;
    private JComboBox<Customer.TYPE_ENUM> combo_customer_type;
    private JLabel label_customer_phone;
    private JTextField field_customer_phone;
    private JLabel label_customer_mail;
    private JTextField field_customer_mail;
    private JLabel label_customer_address;
    private JTextArea area_customer_address;
    private JButton button_customer_save;
    private Customer customer;
    private CustomerController customerController;

    public CustomerUI(Customer customer) {
        this.customer = customer;
        this.customerController = new CustomerController();

        this.add(container);
        this.setTitle("Add/Update Customer");
        this.setSize(300, 500);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);

        this.setVisible(true);

        this.combo_customer_type.setModel(new DefaultComboBoxModel<>(Customer.TYPE_ENUM.values()));

        if (this.customer.getId() == 0) {
            this.label_title.setText("Add Customer");
        } else {
            this.label_title.setText("Update Customer");
            this.field_customer_name.setText(this.customer.getName());
            this.field_customer_phone.setText(this.customer.getPhone());
            this.field_customer_mail.setText(this.customer.getMail());
            this.area_customer_address.setText(this.customer.getAddress());
            this.combo_customer_type.getModel().setSelectedItem(this.customer.getType());
        }

        this.button_customer_save.addActionListener(e -> {
            JTextField[] checkList = { this.field_customer_name, this.field_customer_phone };
            if (Helper.isFieldListEmpty(checkList)) {
                Helper.showAlert("fill");
            } else if (!Helper.isFieldEmpty(this.field_customer_mail) && !Helper.isEmailValid(this.field_customer_mail.getText())) {
                Helper.showAlert("Please indicate a valid email.");
            } else {
                boolean result = false;
                this.customer.setName(this.field_customer_name.getText());
                this.customer.setPhone(this.field_customer_phone.getText());
                this.customer.setMail(this.field_customer_mail.getText());
                this.customer.setAddress(this.area_customer_address.getText());
                this.customer.setType((Customer.TYPE_ENUM) this.combo_customer_type.getSelectedItem());

                if (this.customer.getId() == 0) {
                    result = this.customerController.save(this.customer);
                } else {
                    result = this.customerController.update(this.customer);
                }

                if (result) {
                    Helper.showAlert("done");
                    dispose();
                } else {
                    Helper.showAlert("error");
                }
            }
        });
    }
}
