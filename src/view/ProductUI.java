package view;

import business.ProductController;
import core.Helper;
import entities.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductUI extends JFrame {
    private JPanel container;
    private JLabel label_title;
    private JTextField field_product_name;
    private JTextField field_product_code;
    private JTextField field_product_price;
    private JTextField field_product_stock;
    private JButton button_product;
    private JLabel label_product_name;
    private JLabel label_product_code;
    private JLabel label_product_price;
    private JLabel label_product_stock;
    private Product product;
    private ProductController productController;

    public ProductUI(Product product) {
        this.product = product;
        this.productController = new ProductController();

        this.add(container);
        this.setTitle("Add/Update Product");
        this.setSize(300, 350);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);

        this.setVisible(true);

        if (this.product.getId() == 0) {
            this.label_title.setText("Add Product");
        } else {
            this.label_title.setText("Edit Product");
            this.field_product_name.setText(this.product.getName());
            this.field_product_code.setText(this.product.getCode());
            this.field_product_price.setText(String.valueOf(this.product.getPrice()));
            this.field_product_stock.setText(String.valueOf(this.product.getStock()));
        }

        button_product.addActionListener(e -> {
            JTextField[] checkList = {
                    this.field_product_name,
                    this.field_product_code,
                    this.field_product_price,
                    this.field_product_stock
            };

            if (Helper.isFieldListEmpty(checkList)) {
                Helper.showAlert("fill");
            } else {
                this.product.setName(this.field_product_name.getText());
                this.product.setCode(this.field_product_code.getText());
                this.product.setPrice(Integer.parseInt(this.field_product_price.getText()));
                this.product.setStock(Integer.parseInt(this.field_product_stock.getText()));

                boolean result;
                if (this.product.getId() == 0) {
                    result = this.productController.save(this.product);
                } else {
                    result = this.productController.update(this.product);
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
