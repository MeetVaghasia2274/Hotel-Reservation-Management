import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JButton customerButton;
    private JButton roomButton;
    private JButton reservationButton;
    private JButton checkInOutButton;
    private JButton paymentButton;

    public MainMenu() {
        setTitle("Hotel Reservation System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        customerButton = new JButton("Customer Management");
        customerButton.setBounds(100, 30, 200, 30);
        roomButton = new JButton("Room Management");
        roomButton.setBounds(100, 70, 200, 30);
        reservationButton = new JButton("Reservations");
        reservationButton.setBounds(100, 110, 200, 30);
        checkInOutButton = new JButton("Check In/Out");
        checkInOutButton.setBounds(100, 150, 200, 30);
        paymentButton = new JButton("Payments");
        paymentButton.setBounds(100, 190, 200, 30);

        add(customerButton);
        add(roomButton);
        add(reservationButton);
        add(checkInOutButton);
        add(paymentButton);

        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CustomerManagement().setVisible(true);
            }
        });

        roomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RoomManagement().setVisible(true);
            }
        });

        reservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReservationManagement().setVisible(true);
            }
        });

        checkInOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CheckInOutManagement().setVisible(true);
            }
        });

        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PaymentManagement().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        new MainMenu().setVisible(true);
    }
}
