package core;

import javax.swing.*;

public class Helper {

    public static void setTheme() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getName().equals("Nimbus")) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldListEmpty(JTextField[] fields) {
        for (JTextField field : fields) {
            if (isFieldEmpty(field)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmailValid(String email) {
        // contains @, string before @, dot after @, string after dot
        if (email == null || email.trim().isEmpty()) return false;
        if (!email.contains("@")) return false;

        String[] emailParts = email.split("@");
        if (emailParts.length != 2) return false;
        if (emailParts[0].trim().isEmpty() || emailParts[1].trim().isEmpty()) return false;
        if (!emailParts[1].contains(".")) return false;

        return true;
    }

    public static void showAlert(String message) {
        String msg;
        String title;

        switch (message) {
            case "fill":
                msg = "Please fill out all fields.";
                title = "ERROR";
                break;

            case "done":
                msg = "Attempt successful!";
                title = "Success!";
                break;

            case "error":
                msg = "Something went wrong!";
                title = "ERROR";
                break;

            default:
                msg = message;
                title = "Message";
                break;
        }

        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str) {
        String msg;

        if (str.equals("sure")) {
            msg = "Are you sure you want to delete?";
        } else {
            msg = str;
        }

        return JOptionPane.showConfirmDialog(null, msg, "Are you sure?", JOptionPane.YES_NO_OPTION) == 0;
    }
}
