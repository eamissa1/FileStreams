import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame
{
    private JTextField nameField, descriptionField, idField, costField, recordCountField;
    private JButton addButton, quitButton;
    private int recordCount = 0;

    public RandProductMaker()
    {
        super("Product Data Entry");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(6, 2)); // Keep the original layout dimensions

        // Initialize components
        nameField = new JTextField();
        descriptionField = new JTextField();
        idField = new JTextField();
        costField = new JTextField();
        recordCountField = new JTextField("0");
        recordCountField.setEditable(false);
        addButton = new JButton("Add Product");
        quitButton = new JButton("Quit");

        // Adding components to the JFrame
        add(new JLabel("ID (6 chars max):"));
        add(idField);
        add(new JLabel("Name (35 chars max):"));
        add(nameField);
        add(new JLabel("Description (75 chars max):"));
        add(descriptionField);
        add(new JLabel("Cost:"));
        add(costField);
        add(addButton);
        add(quitButton); // Place the Quit button next to the Add button
        add(new JLabel("Record Count:"));
        add(recordCountField);
        // Action listener for addButton
        addButton.addActionListener(this::addAction);

        // Action listener for quitButton
        quitButton.addActionListener(e -> System.exit(0)); // Terminate the application

        setVisible(true);
    }

    private void addAction(ActionEvent event)
    {
        try {
            String id = idField.getText();
            String name = nameField.getText();
            String description = descriptionField.getText();
            double cost = Double.parseDouble(costField.getText());

            // Validation could be more thorough in a real application
            if (name.isEmpty() || description.isEmpty() || id.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "All fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Product product = new Product(id, name, description, cost);

            try (RandomAccessFile file = new RandomAccessFile("Product.dat", "rw"))
            {
                file.seek(file.length());
                file.write(product.toByteArray());
                recordCount++;
                recordCountField.setText(String.valueOf(recordCount));
                clearFields();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to write to file", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cost must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields()
    {
        // Clear the input fields after adding a product
        idField.setText("");
        nameField.setText("");
        descriptionField.setText("");
        costField.setText("");
    }

    public static void main(String[] args)
    {
        new RandProductMaker();
    }
}
