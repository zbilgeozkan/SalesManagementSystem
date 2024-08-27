package view;

import business.CustomerController;
import business.ProductController;
import core.Helper;
import core.Item;
import entities.Customer;
import entities.Product;
import entities.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DashboardUI extends JFrame {
    private JPanel container;
    private JLabel label_welcome;
    private JButton button_logout;
    private JTabbedPane tab_menu;
    private JPanel panel_customer;
    private JScrollPane scroll_customer;
    private JTable table_customer;
    private JPanel panel_customer_filter;
    private JTextField field_f_customer_name;
    private JComboBox<Customer.TYPE_ENUM> combo_customer_type;
    private JButton button_f_customer_search;
    private JButton button_f_customer_clear;
    private JButton button_f_customer_add;
    private JLabel label_f_customer_name;
    private JLabel label_f_customer_type;
    private JPanel panel_product;
    private JScrollPane scroll_product;
    private JTable table_product;
    private JPanel panel_product_filter;
    private JTextField field_f_product_name;
    private JTextField field_f_product_code;
    private JComboBox<Item> combo_product_stock;
    private JButton button_product_filter;
    private JButton button_product_clear;
    private JButton button_product_add;
    private JLabel label_f_product_name;
    private JLabel label_f_product_code;
    private JLabel label_f_product_stock;
    private User user;
    private CustomerController customerController;
    private ProductController productController;
    private DefaultTableModel table_model_customer = new DefaultTableModel();
    private DefaultTableModel table_model_product = new DefaultTableModel();
    private JPopupMenu popup_customer = new JPopupMenu();
    private JPopupMenu popup_product = new JPopupMenu();

    public DashboardUI(User user) {
        this.user = user;
        this.customerController = new CustomerController();
        this.productController = new ProductController();

        if (user == null) {
            Helper.showAlert("error");
            dispose();
        }

        this.add(container);
        this.setTitle("Customer Management System");
        this.setSize(1000, 500);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);

        this.setVisible(true);

        this.label_welcome.setText("Welcome: " + this.user.getName());

        this.button_logout.addActionListener(e -> {
            dispose();
            LoginUI loginUI = new LoginUI();
        });

            /*
        button_f_customer_search.addActionListener(e -> {
            loadCustomerTable(null);
        });

         */

        // CUSTOMER TABLE
        loadCustomerTable(null);
        loadCustomerPopupMenu();
        loadCustomerButtonEvent();

        this.combo_customer_type.setModel(new DefaultComboBoxModel<>(Customer.TYPE_ENUM.values()));
        this.combo_customer_type.setSelectedItem(null);

        // PRODUCT TABLE
        loadProductTable(null);
        loadProductPopupMenu();
        loadProductButtonEvent();
        this.combo_product_stock.addItem(new Item(1, "In Stock"));
        this.combo_product_stock.addItem(new Item(2, "Out of Stock"));
        this.combo_product_stock.setSelectedItem(null);

    }

    private void loadProductButtonEvent() {
        this.button_product_add.addActionListener(e -> {
            ProductUI productUI = new ProductUI(new Product());
            productUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                }
            });
        });

        this.button_product_filter.addActionListener(e -> {
            ArrayList<Product> filteredProducts = this.productController.filter(
                    this.field_f_product_name.getText(),
                    this.field_f_product_code.getText(),
                    (Item) this.combo_product_stock.getSelectedItem()
            );

            loadProductTable(filteredProducts);
        });

        this.button_product_clear.addActionListener(e -> {
            this.field_f_product_name.setText(null);
            this.field_f_product_code.setText(null);
            this.combo_product_stock.setSelectedItem(null);
            loadProductTable(null);
        });
    }

    private void loadProductPopupMenu() {
        this.table_product.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = table_product.rowAtPoint(e.getPoint());
                table_product.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        this.popup_product.add("Update").addActionListener(e -> {
            int selectId = Integer.parseInt(this.table_product.getValueAt(this.table_product.getSelectedRow(), 0).toString());
            ProductUI productUI = new ProductUI(this.productController.getById(selectId));
            productUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                }
            });
        });
        this.popup_product.add("Delete").addActionListener(e -> {
            int selectId = Integer.parseInt(this.table_product.getValueAt(this.table_product.getSelectedRow(), 0).toString());
            if (Helper.confirm("sure")) {
                if (this.productController.delete(selectId)) {
                    Helper.showAlert("done");
                    loadProductTable(null);
                } else {
                    Helper.showAlert("error");
                }
            }
        });

        this.table_product.setComponentPopupMenu(this.popup_product);
    }

    private void loadProductTable(ArrayList<Product> products) {
        Object[] columnProduct =  {"ID", "Product Name", "Code", "Price", "Stock"};

        if (products == null) {
            products = this.productController.findAll();
        }

        // Table reset
        DefaultTableModel clearModel = (DefaultTableModel) this.table_product.getModel();
        clearModel.setRowCount(0);

        this.table_model_product.setColumnIdentifiers(columnProduct);
        for (Product product : products) {

            Object[] rowObject = {
                    product.getId(),
                    product.getName(),
                    product.getCode(),
                    product.getPrice(),
                    product.getStock()
            };
            this.table_model_product.addRow(rowObject);
        }

        this.table_product.setModel(table_model_product);
        this.table_product.getTableHeader().setReorderingAllowed(false);
        this.table_product.getColumnModel().getColumn(0).setMaxWidth(50);
        this.table_product.setEnabled(false);
    }

    private void loadCustomerButtonEvent() {
        this.button_f_customer_add.addActionListener(e -> {
            CustomerUI customerUI = new CustomerUI(new Customer());
            customerUI.addWindowListener(new WindowAdapter() {
               @Override
               public void windowClosed(WindowEvent e) {
                   loadCustomerTable(null);
               }
            });
        });

        this.button_f_customer_search.addActionListener(e -> {
            ArrayList<Customer> filteredCustomers = this.customerController.filter(
                    this.field_f_customer_name.getText(),
                    (Customer.TYPE_ENUM) this.combo_customer_type.getSelectedItem()
            );

            loadCustomerTable(filteredCustomers);
        });

        this.button_f_customer_clear.addActionListener(e -> {
            loadCustomerTable(null);
            this.field_f_customer_name.setText(null);
            this.combo_customer_type.setSelectedItem(null);
        });
    }

    private void loadCustomerPopupMenu() {

        // Indicates which one is pressed from the table
        this.table_customer.addMouseListener(new MouseAdapter() {
            @Override
                public void mousePressed(MouseEvent e) {
                int selectedRow = table_customer.rowAtPoint(e.getPoint());
                table_customer.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        this.popup_customer.add("Update").addActionListener(e -> {
            // System.out.println(selectId);
            int selectId = Integer.parseInt(table_customer.getValueAt(table_customer.getSelectedRow(), 0).toString());
            CustomerUI customerUI = new CustomerUI(this.customerController.getById(selectId));
            customerUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCustomerTable(null);
                }
            });
        });

//        JMenuItem menuItem = this.popup_customer.add("Update");
//        menuItem.addActionListener(e -> {
//
//        });
        this.popup_customer.add("Delete").addActionListener(e -> {
            // System.out.println("Delete clicked.");
            int selectId = Integer.parseInt(table_customer.getValueAt(table_customer.getSelectedRow(), 0).toString());
            if (Helper.confirm("sure")) {
                if (this.customerController.delete(selectId)) {
                    Helper.showAlert("done");
                    loadCustomerTable(null);
                } else {
                    Helper.showAlert("error");
                }
            }
        });

        this.table_customer.setComponentPopupMenu(this.popup_customer);
    }

    private void loadCustomerTable(ArrayList<Customer> customers) {
        Object[] columnCustomer =  {"ID", "Customer Name", "Type", "Phone", "Mail", "Address"};

        if (customers == null) {
            customers = this.customerController.findAll();
        }

        // Table reset
        DefaultTableModel clearModel = (DefaultTableModel) this.table_customer.getModel();
        clearModel.setRowCount(0);

        this.table_model_customer.setColumnIdentifiers(columnCustomer);
        for (Customer customer : customers) {

            Object[] rowObject = {
                    customer.getId(),
                    customer.getName(),
                    customer.getType(),
                    customer.getPhone(),
                    customer.getMail(),
                    customer.getAddress()
            };
            this.table_model_customer.addRow(rowObject);
        }

        this.table_customer.setModel(table_model_customer);
        this.table_customer.getTableHeader().setReorderingAllowed(false);
        this.table_customer.getColumnModel().getColumn(0).setMaxWidth(50);
        this.table_customer.setEnabled(false);
    }
}
