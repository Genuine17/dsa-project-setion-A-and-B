import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    JLabel l1, l2, l3;
    JTextField tf;
    JPasswordField pf;
    JPanel p1, p2;
    JButton bt1, bt2;
    Font f1, f2;
    
      // Database connection object
    ConnectionClass connectionClass;

    Login() {
        super("Login PhoneBook");
        setLocation(400, 300);
        setSize(530, 250);

        f1 = new Font("Arial", Font.BOLD, 25);
        f2 = new Font("Arial", Font.BOLD, 25);

        l1 = new JLabel("Welcome to PhoneBook");
        l2 = new JLabel("UserName");
        l3 = new JLabel("Password");

        l1.setHorizontalAlignment(JLabel.CENTER);
        l1.setFont(f1);
        l2.setFont(f2);
        l3.setFont(f2);

        tf = new JTextField();
        pf = new JPasswordField();

        tf.setFont(f2);
        pf.setFont(f2);

        bt1 = new JButton("Login");
        bt2 = new JButton("Cancel");
        bt1.addActionListener(this);
        bt2.addActionListener(this);
        bt1.setFont(f2);
        bt2.setFont(f2);

        // Add components to panel p1 (Username, Password, Login, Cancel)
        p1 = new JPanel();
        p1.setLayout(new GridLayout(3, 2, 10, 10));
        p1.add(l2);
        p1.add(tf);
        p1.add(l3);
        p1.add(pf);
        p1.add(bt1);
        p1.add(bt2);

        // Add Welcome message to panel p2
        p2 = new JPanel();
        p2.setLayout(new GridLayout(1, 1, 10, 10));
        p2.add(l1);

        // Set layout and add panels to the frame
        setLayout(new BorderLayout(10, 20));
        add(p2, BorderLayout.NORTH); // Welcome label at the top
        add(p1, BorderLayout.CENTER); // Username, password, login, and cancel in the center

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Initialize the connection class
        connectionClass = new ConnectionClass();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt1) {
            
             // Check credentials when login button is clicked
            String username = tf.getText();
            String password = new String(pf.getPassword());
            
              if (checkCredentials(username, password)) {
                // Open Phonebook if login is successful
                new Phonebook().setVisible(true);
                dispose(); // Close the login window
            } else {
                // Show error message if login fails
                JOptionPane.showMessageDialog(this, "Invalid Username or Password. Please try again.");
            }    
                
        } else if (e.getSource() == bt2) {
            // Close the application when cancel button is clicked
            System.exit(0);
        }
    }

     private boolean checkCredentials(String username, String password) {
        boolean isValid = false;
        try {
            String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = connectionClass.con.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            isValid = rs.next(); // If there's a result, the credentials are valid
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return isValid;
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}
