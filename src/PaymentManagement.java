import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentManagement extends JFrame {
    private JTextField reservationIdField, paymentAmountField, paymentMethodField;
    private JButton addPaymentButton;

    public PaymentManagement() {
        setTitle("Payment Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel reservationIdLabel = new JLabel("Reservation ID:");
        reservationIdLabel.setBounds(30, 30, 100, 30);
        reservationIdField = new JTextField();
        reservationIdField.setBounds(150, 30, 200, 30);

        JLabel paymentAmountLabel = new JLabel("Payment Amount:");
        paymentAmountLabel.setBounds(30, 70, 100, 30);
        paymentAmountField = new JTextField();
        paymentAmountField.setBounds(150, 70, 200, 30);

        JLabel paymentMethodLabel = new JLabel("Payment Method:");
        paymentMethodLabel.setBounds(30, 110, 100, 30);
        paymentMethodField = new JTextField();
        paymentMethodField.setBounds(150, 110, 200, 30);

        addPaymentButton = new JButton("Add Payment");
        addPaymentButton.setBounds(150, 150, 150, 30);

        add(reservationIdLabel);
        add(reservationIdField);
        add(paymentAmountLabel);
        add(paymentAmountField);
        add(paymentMethodLabel);
        add(paymentMethodField);
        add(addPaymentButton);

        addPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPayment();
            }
        });
    }

    private void addPayment() {
        int reservationId = Integer.parseInt(reservationIdField.getText());
        double amount = Double.parseDouble(paymentAmountField.getText());
        String paymentMethod = paymentMethodField.getText();

        try (Connection conn = DBConnection.getConnection()) {
            // Check if the reservation exists
            String query = "SELECT total_amount FROM reservations WHERE reservation_id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, reservationId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Insert payment record
                String paymentQuery = "INSERT INTO payments (reservation_id, payment_date, amount, payment_method) VALUES (?, CURDATE(), ?, ?)";
                PreparedStatement paymentPst = conn.prepareStatement(paymentQuery);
                paymentPst.setInt(1, reservationId);
                paymentPst.setDouble(2, amount);
                paymentPst.setString(3, paymentMethod);
                paymentPst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Payment recorded successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid reservation ID!");
            }
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
        new PaymentManagement().setVisible(true);
    }
}
