import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CustomerManagement extends JFrame {
    private JTextField nameField, phoneField, emailField, addressField;
    private JButton addButton;

    public CustomerManagement() {
        setTitle("Customer Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 30, 100, 30);
        nameField = new JTextField();
        nameField.setBounds(150, 30, 200, 30);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(30, 70, 100, 30);
        phoneField = new JTextField();
        phoneField.setBounds(150, 70, 200, 30);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 110, 100, 30);
        emailField = new JTextField();
        emailField.setBounds(150, 110, 200, 30);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(30, 150, 100, 30);
        addressField = new JTextField();
        addressField.setBounds(150, 150, 200, 30);

        addButton = new JButton("Add Customer");
        addButton.setBounds(150, 190, 150, 30);

        add(nameLabel);
        add(nameField);
        add(phoneLabel);
        add(phoneField);
        add(emailLabel);
        add(emailField);
        add(addressLabel);
        add(addressField);
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });
    }

    private void addCustomer() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO customers (name, phone, email, address) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, phone);
            pst.setString(3, email);
            pst.setString(4, address);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Customer added successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new CustomerManagement().setVisible(true);
    }
}

