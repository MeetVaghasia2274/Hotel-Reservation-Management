import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckInOutManagement extends JFrame {
    private JTextField reservationIdField;
    private JButton checkInButton, checkOutButton;

    public CheckInOutManagement() {
        setTitle("Check-In/Out Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel reservationIdLabel = new JLabel("Reservation ID:");
        reservationIdLabel.setBounds(30, 30, 100, 30);
        reservationIdField = new JTextField();
        reservationIdField.setBounds(150, 30, 200, 30);

        checkInButton = new JButton("Check In");
        checkInButton.setBounds(50, 100, 100, 30);

        checkOutButton = new JButton("Check Out");
        checkOutButton.setBounds(200, 100, 100, 30);

        add(reservationIdLabel);
        add(reservationIdField);
        add(checkInButton);
        add(checkOutButton);

        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkIn();
            }
        });

        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkOut();
            }
        });
    }

    private void checkIn() {
        int reservationId = Integer.parseInt(reservationIdField.getText());

        try (Connection conn = DBConnection.getConnection()) {
            // Check if the reservation exists
            String query = "SELECT room_number FROM reservations WHERE reservation_id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, reservationId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int roomNumber = rs.getInt("room_number");

                // Update room status to "Booked"
                String updateRoomQuery = "UPDATE rooms SET status = 'Booked' WHERE room_number = ?";
                PreparedStatement updateRoomPst = conn.prepareStatement(updateRoomQuery);
                updateRoomPst.setInt(1, roomNumber);
                updateRoomPst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Check-in successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid reservation ID!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void checkOut() {
        int reservationId = Integer.parseInt(reservationIdField.getText());

        try (Connection conn = DBConnection.getConnection()) {
            // Check if the reservation exists
            String query = "SELECT room_number FROM reservations WHERE reservation_id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, reservationId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int roomNumber = rs.getInt("room_number");

                // Update room status to "Available"
                String updateRoomQuery = "UPDATE rooms SET status = 'Available' WHERE room_number = ?";
                PreparedStatement updateRoomPst = conn.prepareStatement(updateRoomQuery);
                updateRoomPst.setInt(1, roomNumber);
                updateRoomPst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Check-out successful!");
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
        new CheckInOutManagement().setVisible(true);
    }
}
