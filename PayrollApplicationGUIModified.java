import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class PayrollApplicationGUIModified extends JFrame {
    private JLabel idLabel, nameLabel, salaryLabel;
    private JTextField idField, nameField, salaryField;
    private JButton calculateButton;
    private JTextArea resultArea;

    private ResourceBundle rb;
    private Locale locale;

    public PayrollApplicationGUIModified() {
        locale = Locale.getDefault();
        setTitle("Payroll Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        idLabel = new JLabel("Employee ID:");
        nameLabel = new JLabel("Name:");
        salaryLabel = new JLabel("Basic Salary:");
        idField = new JTextField(10);
        nameField = new JTextField(20);
        salaryField = new JTextField(10);
        calculateButton = new JButton("Calculate");
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(idLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(salaryLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(salaryField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(calculateButton);

        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultPanel.add(resultArea);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.SOUTH);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computeSalary();
            }
        });

        setVisible(true);
    }

    private void computeSalary() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            double basicSalary = Double.parseDouble(salaryField.getText());

            double hra = basicSalary * 0.50;
            double ta = basicSalary * 0.40;
            double ma = basicSalary * 0.25;
            double da = basicSalary * 0.20;
            double pf = basicSalary * 0.05;
            double gs = basicSalary + hra + da + ta + ma;
            double tax = computeTax(gs);
            double ns = gs - pf - tax;

            resultArea.setText("");
            resultArea.append("Date: " + dateFormat() + "\n");
            resultArea.append("Employee ID: " + id + "\n");
            resultArea.append("Employee Name: " + properCase(name) + "\n");
            resultArea.append("Allowances:\n");
            resultArea.append("HRA: " + currencyFormat(hra) + "\n");
            resultArea.append("DA: " + currencyFormat(da) + "\n");
            resultArea.append("MA: " + currencyFormat(ma) + "\n");
            resultArea.append("TA: " + currencyFormat(ta) + "\n");
            resultArea.append("Gross Salary: " + currencyFormat(gs) + "\n");
            resultArea.append("Deductions:\n");
            resultArea.append("PF: " + currencyFormat(pf) + "\n");
            resultArea.append("Tax: " + currencyFormat(tax) + "\n");
            resultArea.append("Net Salary: " + currencyFormat(ns) + "\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for ID and Salary.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String properCase(String name) {
        String fullName = "";
        String names[] = name.split(" ");
        for (int i = 0; i < names.length; i++) {
            String n = String.valueOf(names[i].charAt(0)).toUpperCase() +
                    names[i].substring(1).toLowerCase();
            fullName = fullName + n + " ";
        }
        return fullName.trim();
    }

    private String currencyFormat(double val) {
        NumberFormat obj = NumberFormat.getCurrencyInstance(locale);
        return obj.format(val);
    }

    private String dateFormat() {
        Date date = new Date();
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        return df.format(date);
    }

    private double computeTax(double gs) {
        double annual = gs * 12;
        if (annual > 500000 && annual < 700000) {
            return (annual * 0.10) / 12;
        } else if (annual > 700000 && annual < 900000) {
            return (annual * 0.20) / 12;
        } else if (annual > 900000) {
            return (annual * 0.30) / 12;
        }
        return 0.0;
    }

    public static void main(String[] args) {
        new PayrollApplicationGUIModified();
    }
}
