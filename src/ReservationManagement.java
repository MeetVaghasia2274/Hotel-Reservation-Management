import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReservationManagement extends JFrame {
    private JTextField customerIdField, roomNumberField, checkInField, checkOutField;
    private JButton reserveButton;

    public ReservationManagement() {
        setTitle("Reservation Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel customerIdLabel = new JLabel("Customer ID:");
        customerIdLabel.setBounds(30, 30, 100, 30);
        customerIdField = new JTextField();
        customerIdField.setBounds(150, 30, 200, 30);

        JLabel roomNumberLabel = new JLabel("Room Number:");
        roomNumberLabel.setBounds(30, 70, 100, 30);
        roomNumberField = new JTextField();
        roomNumberField.setBounds(150, 70, 200, 30);

        JLabel checkInLabel = new JLabel("Check-In Date (YYYY-MM-DD):");
        checkInLabel.setBounds(30, 110, 200, 30);
        checkInField = new JTextField();
        checkInField.setBounds(150, 140, 200, 30);

        JLabel checkOutLabel = new JLabel("Check-Out Date (YYYY-MM-DD):");
        checkOutLabel.setBounds(30, 170, 200, 30);
        checkOutField = new JTextField();
        checkOutField.setBounds(150, 200, 200, 30);

        reserveButton = new JButton("Reserve Room");
        reserveButton.setBounds(150, 240, 150, 30);

        add(customerIdLabel);
        add(customerIdField);
        add(roomNumberLabel);
        add(roomNumberField);
        add(checkInLabel);
        add(checkInField);
        add(checkOutLabel);
        add(checkOutField);
        add(reserveButton);

        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reserveRoom();
            }
        });
    }

    private void reserveRoom() {
        int customerId = Integer.parseInt(customerIdField.getText());
        int roomNumber = Integer.parseInt(roomNumberField.getText());
        String checkIn = checkInField.getText();
        String checkOut = checkOutField.getText();

        try (Connection conn = DBConnection.getConnection()) {
            // Check room availability
            String roomQuery = "SELECT status FROM rooms WHERE room_number = ?";
            PreparedStatement roomPst = conn.prepareStatement(roomQuery);
            roomPst.setInt(1, roomNumber);
            ResultSet rs = roomPst.executeQuery();
            if (rs.next()) {
                String status = rs.getString("status");
                if (!status.equals("Available")) {
                    JOptionPane.showMessageDialog(null, "Room is not available!");
                    return;
                }
            }

            // Insert into reservations
            String query = "INSERT INTO reservations (customer_id, room_number, check_in_date, check_out_date) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, customerId);
            pst.setInt(2, roomNumber);
            pst.setString(3, checkIn);
            pst.setString(4, checkOut);
            pst.executeUpdate();

            // Update room status
            String updateRoomQuery = "UPDATE rooms SET status = 'Booked' WHERE room_number = ?";
            PreparedStatement updateRoomPst = conn.prepareStatement(updateRoomQuery);
            updateRoomPst.setInt(1, roomNumber);
            updateRoomPst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Room reserved successfully!");
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
        new ReservationManagement().setVisible(true);
    }
}

