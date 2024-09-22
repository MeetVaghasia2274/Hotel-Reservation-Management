import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RoomManagement extends JFrame {
    private JTextField roomNumberField, roomTypeField, roomPriceField;
    private JButton addButton;

    public RoomManagement() {
        setTitle("Room Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel roomNumberLabel = new JLabel("Room Number:");
        roomNumberLabel.setBounds(30, 30, 100, 30);
        roomNumberField = new JTextField();
        roomNumberField.setBounds(150, 30, 200, 30);

        JLabel roomTypeLabel = new JLabel("Room Type:");
        roomTypeLabel.setBounds(30, 70, 100, 30);
        roomTypeField = new JTextField();
        roomTypeField.setBounds(150, 70, 200, 30);

        JLabel roomPriceLabel = new JLabel("Room Price:");
        roomPriceLabel.setBounds(30, 110, 100, 30);
        roomPriceField = new JTextField();
        roomPriceField.setBounds(150, 110, 200, 30);

        addButton = new JButton("Add Room");
        addButton.setBounds(150, 150, 150, 30);

        add(roomNumberLabel);
        add(roomNumberField);
        add(roomTypeLabel);
        add(roomTypeField);
        add(roomPriceLabel);
        add(roomPriceField);
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRoom();
            }
        });
    }

    private void addRoom() {
        int roomNumber = Integer.parseInt(roomNumberField.getText());
        String roomType = roomTypeField.getText();
        double roomPrice = Double.parseDouble(roomPriceField.getText());

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO rooms (room_number, type, price) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, roomNumber);
            pst.setString(2, roomType);
            pst.setDouble(3, roomPrice);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Room added successfully!");
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
        new RoomManagement().setVisible(true);
    }
}
