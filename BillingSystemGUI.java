
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
public class BillingSystemGUI extends JFrame {
    private JComboBox<String> categoryComboBox;
    private JComboBox<String> itemComboBox;
    private JLabel itemNameLabel;
    private JLabel discountLabel;
    private JLabel priceLabel;
    private double totalBill = 0;
    public BillingSystemGUI() {
        setTitle("Billing System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1));
        JLabel selectCategoryLabel = new JLabel("Select Category:");
        add(selectCategoryLabel);
        String[] categories = {"Casual", "Formal", "Footwear"};
        categoryComboBox = new JComboBox<>(categories);
        add(categoryComboBox);
        JLabel selectItemLabel = new JLabel("Select Item:");
        add(selectItemLabel);
        itemComboBox = new JComboBox<>();
        add(itemComboBox);
        JLabel itemLabel = new JLabel("Item:");
        add(itemLabel);
        itemNameLabel = new JLabel();
        add(itemNameLabel);
        JLabel discountTextLabel = new JLabel("Discount:");
        add(discountTextLabel);
        discountLabel = new JLabel();
        add(discountLabel);
        JLabel priceTextLabel = new JLabel("Price:");
        add(priceTextLabel);
        priceLabel = new JLabel();
        add(priceLabel);
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });
        add(addToCartButton);
        JButton printBillButton = new JButton("Print Bill");
        printBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printBill();
            }
        });
        add(printBillButton);
        categoryComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                if (selectedCategory.equals("Casual")) {
                    String[] casualItems = {"Shirts", "Pants", "Jeans"};
                    itemComboBox.setModel(new DefaultComboBoxModel<>(casualItems));
                } else if (selectedCategory.equals("Formal")) {
                    String[] formalItems = {"Shirts", "Pants"};
                    itemComboBox.setModel(new DefaultComboBoxModel<>(formalItems));
                } else if (selectedCategory.equals("Footwear")) {
                    String[] footwearItems = {"Casual", "Sneakers", "Street wear"};
                    itemComboBox.setModel(new DefaultComboBoxModel<>(footwearItems));
                }
            }
        });
    }
    private void addToCart() {
        String selectedItem = (String) itemComboBox.getSelectedItem();
        double discount = 5;
        double price = 1000;
        totalBill += price - (price * discount / 100);
        DecimalFormat df = new DecimalFormat("#.##");
        itemNameLabel.setText(selectedItem);
        discountLabel.setText(df.format(discount) + "%");
        priceLabel.setText("₹" + df.format(price - (price * discount / 100)));
    }
    private void printBill() {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        try (PrintWriter writer = new PrintWriter(new FileWriter("bills.txt", true))) {
            writer.println("Select Category: " + selectedCategory);
            writer.println("Item: " + itemNameLabel.getText());
            writer.println("Discount: " + discountLabel.getText());
            writer.println("Price: " + priceLabel.getText());
            writer.println("Total Bill: ₹" + totalBill);
            writer.println(); 
            totalBill = 0; 
            JOptionPane.showMessageDialog(this, "Bill printed successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to print bill!");
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BillingSystemGUI().setVisible(true);
            }
        });
    }
}