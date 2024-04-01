import javax.swing.*;
import java.awt.*;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public class RandProductSearch extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;

    public RandProductSearch() {
        createUI();
    }

    private void createUI() {
        setTitle("Product Search Engine");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.add(new JLabel("Partial Product Name:"));
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchProducts());
        panel.add(searchField);
        panel.add(searchButton);

        add(panel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void searchProducts() {
        resultArea.setText("");
        // Assuming a fixed record size for demonstration purposes
        final int RECORD_SIZE = 126; // Adjust based on actual serialization
        try (RandomAccessFile file = new RandomAccessFile("Product.dat", "r")) {
            byte[] recordBytes = new byte[RECORD_SIZE];
            while (file.read(recordBytes) != -1) {
                String record = new String(recordBytes, StandardCharsets.UTF_8);
                String name = record.substring(6, 41).trim(); // Adjust indices based on your serialization format

                if (name.contains(searchField.getText().trim())) {
                    String id = record.substring(0, 6).trim();
                    String description = record.substring(41, 116).trim(); // Adjust indices accordingly
                    String costStr = record.substring(116).trim(); // Assuming cost is at the end
                    double cost = Double.parseDouble(costStr);
                    resultArea.append(String.format("%s, %s, %s, $%.2f\n", id, name, description, cost));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading file.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RandProductSearch::new);
    }
}
