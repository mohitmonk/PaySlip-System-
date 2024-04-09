import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class PayrollApplicationGUI extends JFrame {
    private JLabel idLabel, nameLabel, salaryLabel, companyInfoLabelName,companyInfoLabelAddress,companyInfoLabelEmail ,dateLabel;
    private JTextField idField, nameField, salaryField;
    private JButton calculateButton;
    private JLabel grossSalaryLabel, hraLabel, taLabel, maLabel, daLabel, pfLabel, taxLabel, netSalaryLabel;

    public PayrollApplicationGUI() {
        setTitle("Payroll Application");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setResizable(false);

        initComponents();
        initLayout();
        addActionListeners();

        setVisible(true);
    }

    private void initComponents() {
        idLabel = new JLabel("Employee ID:");
        nameLabel = new JLabel("Name:");
        salaryLabel = new JLabel("Basic Salary:");
        idField = new JTextField(10);
        nameField = new JTextField(20);
        salaryField = new JTextField(10);
        calculateButton = new JButton("Calculate");

        grossSalaryLabel = new JLabel("Gross Salary:");
        hraLabel = new JLabel("HRA:");
        taLabel = new JLabel("TA:");
        maLabel = new JLabel("MA:");
        daLabel = new JLabel("DA:");
        pfLabel = new JLabel("PF:");
        taxLabel = new JLabel("Tax:");
        netSalaryLabel = new JLabel("Net Salary:");

        // Pre-filled company information
        companyInfoLabelName = new JLabel("Company Name: DIT University");
        companyInfoLabelEmail = new JLabel("Company: acad9@dituniversity.edu.in ");
        companyInfoLabelAddress = new JLabel("Address: Mussoorie, Diversion Road, Uttarakhand 248009");
        dateLabel = new JLabel("Date: " + getCurrentDate());
    }

    private void initLayout() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(salaryLabel);
        inputPanel.add(salaryField);
        
        JPanel calculatePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        calculatePanel.add(calculateButton);
        
        JPanel outputPanel = new JPanel(new GridLayout(12, 2, 5, 5)); // Increased rows to accommodate all payroll elements
        outputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outputPanel.add(grossSalaryLabel);
        outputPanel.add(new JLabel()); 
        outputPanel.add(hraLabel);
        outputPanel.add(new JLabel()); 
        outputPanel.add(taLabel);
        outputPanel.add(new JLabel()); 
        outputPanel.add(maLabel);
        outputPanel.add(new JLabel()); 
        outputPanel.add(daLabel);
        outputPanel.add(new JLabel()); 
        outputPanel.add(pfLabel);
        outputPanel.add(new JLabel()); 
        outputPanel.add(taxLabel);
        outputPanel.add(new JLabel()); 
        outputPanel.add(netSalaryLabel);
        outputPanel.add(new JLabel()); 
        
        JPanel infoPanel = new JPanel(new GridLayout(4, 1));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10)); 
        infoPanel.add(companyInfoLabelName);
        infoPanel.add(companyInfoLabelEmail);
        infoPanel.add(companyInfoLabelAddress);
        infoPanel.add(dateLabel);
        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator1.setPreferredSize(new Dimension(400, 1));
        JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
        separator2.setPreferredSize(new Dimension(400, 1));
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(separator1, BorderLayout.CENTER);
        mainPanel.add(calculatePanel, BorderLayout.CENTER);
        mainPanel.add(outputPanel, BorderLayout.AFTER_LAST_LINE);
        mainPanel.add(separator2, BorderLayout.SOUTH);
        mainPanel.add(infoPanel, BorderLayout.WEST); 
        
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(mainPanel, BorderLayout.NORTH);
    
        setSize(800, 700);
        
        add(containerPanel);
    }
    
    

    private void addActionListeners() {
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computeSalary();
            }
        });
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

            setLabelText(grossSalaryLabel, currencyFormat(gs));
            setLabelText(hraLabel, currencyFormat(hra));
            setLabelText(taLabel, currencyFormat(ta));
            setLabelText(maLabel, currencyFormat(ma));
            setLabelText(daLabel, currencyFormat(da));
            setLabelText(pfLabel, currencyFormat(pf));
            setLabelText(taxLabel, currencyFormat(tax));
            setLabelText(netSalaryLabel, currencyFormat(ns));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for ID and Salary.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
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

    private void setLabelText(JLabel label, String text) {
        label.setText(label.getText() + " " + text);
    }

    private String currencyFormat(double val) {
        NumberFormat obj = NumberFormat.getCurrencyInstance(Locale.US);
        return obj.format(val);
    }

    private String getCurrentDate() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
        return dateFormat.format(new Date());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PayrollApplicationGUI();
            }
        });
    }
}
