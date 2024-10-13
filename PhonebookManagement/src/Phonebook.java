import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Phonebook extends JFrame {
    private ArrayList<Contact> phonebook;
    private JTextArea displayArea;
    private JTextField nameField, numberField;
    private JButton insertButton, searchButton, deleteButton, updateButton, sortButton,displayButton;

    public Phonebook() {
        phonebook = new ArrayList<>();
        initUI();
    }

    private void initUI() {
        // Panel for input fields and buttons
        JPanel panel = new JPanel(new GridLayout(3, 2));
        
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel numberLabel = new JLabel("Number:");
        numberField = new JTextField();
        
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(numberLabel);
        panel.add(numberField);

        // Buttons for operations
        insertButton = new JButton("Insert");
        searchButton = new JButton("Search");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update");
        sortButton = new JButton("Sort");
        displayButton =new JButton("Display All");

        panel.add(insertButton);
        panel.add(searchButton);
        panel.add(deleteButton);
        panel.add(updateButton);
        panel.add(sortButton);
        panel.add(displayButton);

        // Text area to display contacts
        displayArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        // Adding components to frame
        setLayout(new BorderLayout());
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button action listeners
        insertButton.addActionListener(e -> insertContact());
        searchButton.addActionListener(e -> searchContact());
        deleteButton.addActionListener(e -> deleteContact());
        updateButton.addActionListener(e -> updateContact());
        sortButton.addActionListener(e -> sortContacts());
        displayButton.addActionListener(e -> displayContacts());

        setTitle("Phonebook Application");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void insertContact() {
        String name = nameField.getText();
        String number = numberField.getText();

        if (name.isEmpty() || number.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and number.");
            return;
        }

        phonebook.add(new Contact(name, number));
        displayContacts();
        clearFields();
    }

    private void searchContact() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name to search.");
            return;
        }

        for (Contact contact : phonebook) {
            if (contact.getName().equalsIgnoreCase(name)) {
                displayArea.setText("Contact found: \n" + contact);
                return;
            }
        }
        displayArea.setText("Contact not found.");
    }

    private void deleteContact() {
        String name = nameField.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name to delete.");
            return;
        }

        for (Contact contact : phonebook) {
            if (contact.getName().equalsIgnoreCase(name)) {
                phonebook.remove(contact);
                displayContacts();
                clearFields();
                return;
            }
        }
        displayArea.setText("Contact not found.");
    }

    private void updateContact() {
        String name = nameField.getText();
        String newNumber = numberField.getText();

        if (name.isEmpty() || newNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and new number.");
            return;
        }

        for (Contact contact : phonebook) {
            if (contact.getName().equalsIgnoreCase(name)) {
                contact.setNumber(newNumber);
                displayContacts();
                clearFields();
                return;
            }
        }
        displayArea.setText("Contact not found.");
    }

    private void sortContacts() {
        Collections.sort(phonebook, Comparator.comparing(Contact::getName));
        displayContacts();
    }

    private void displayContacts() {
        displayArea.setText("Phonebook Contacts:\n");
        for (Contact contact : phonebook) {
            displayArea.append(contact.toString() + "\n");
        }
    }

    private void clearFields() {
        nameField.setText("");
        numberField.setText("");
    }

    // Efficiency Analysis for Search Operation
    private void analyzeSearchEfficiency(String name) {
        long startTime = System.nanoTime();

        for (Contact contact : phonebook) {
            if (contact.getName().equalsIgnoreCase(name)) {
                break;
            }
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Search time (ns): " + duration);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Phonebook().setVisible(true));
    }

    // Contact class definition
    class Contact {
        private String name;
        private String number;

        public Contact(String name, String number) {
            this.name = name;
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return name + ": " + number;
        }
    }
}