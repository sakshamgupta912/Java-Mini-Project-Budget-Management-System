import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;


public class MonthButtons extends JFrame implements ActionListener {
	private javax.swing.JButton janButton,febButton,marButton, aprButton,mayButton,junButton,julButton,augButton,sepButton,octButton,novButton,decButton,annualButton;
	private JLabel usernameLabel;
	String username;
	public MonthButtons(String username) {
    	
    	this.username=username;
        setTitle("Month Buttons");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 960, 600);
        setResizable(false);
        
        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        contentPane.setLayout(null);
        
        // create buttons for each month
        janButton = new JButton("January");
        janButton.setBounds(51, 51, 203, 88);
        janButton.setFont(new Font("Arial", Font.PLAIN, 20));
        janButton.addActionListener(this);
        contentPane.add(janButton);
        
        febButton = new JButton("February");
        febButton.setBounds(264, 51, 203, 88);
        febButton.setFont(new Font("Arial", Font.PLAIN, 20));
        febButton.addActionListener(this);
        contentPane.add(febButton);
        
        marButton = new JButton("March");
        marButton.setBounds(477, 51, 203, 88);
        marButton.setFont(new Font("Arial", Font.PLAIN, 20));
        marButton.addActionListener(this);
        contentPane.add(marButton);
        
         aprButton = new JButton("April");
        aprButton.setBounds(690, 51, 203, 88);
        aprButton.setFont(new Font("Arial", Font.PLAIN, 20));
        aprButton.addActionListener(this);
        contentPane.add(aprButton);
        
        mayButton = new JButton("May");
        mayButton.setBounds(51, 168, 203, 88);
        mayButton.setFont(new Font("Arial", Font.PLAIN, 20));
        mayButton.addActionListener(this);
        contentPane.add(mayButton);
        
        junButton = new JButton("June");
        junButton.setBounds(264, 168, 203, 88);
        junButton.setFont(new Font("Arial", Font.PLAIN, 20));
        junButton.addActionListener(this);
        contentPane.add(junButton);
        
        julButton = new JButton("July");
        julButton.setBounds(477, 168, 203, 88);
        julButton.setFont(new Font("Arial", Font.PLAIN, 20));
        julButton.addActionListener(this);
        contentPane.add(julButton);
        
        augButton = new JButton("August");
        augButton.setBounds(690, 168, 203, 88);
        augButton.setFont(new Font("Arial", Font.PLAIN, 20));
        augButton.addActionListener(this);
        contentPane.add(augButton);
        
        sepButton = new JButton("September");
        sepButton.setBounds(51, 285, 203, 88);
        sepButton.setFont(new Font("Arial", Font.PLAIN, 20));
        sepButton.addActionListener(this);
        contentPane.add(sepButton);
        
        octButton = new JButton("October");
        octButton.setBounds(264, 285, 203, 88);
        octButton.setFont(new Font("Arial", Font.PLAIN, 20));
        octButton.addActionListener(this);
        contentPane.add(octButton);
        
        novButton = new JButton("November");
        novButton.setBounds(477, 285, 203, 88);
        novButton.setFont(new Font("Arial", Font.PLAIN, 20));
        novButton.addActionListener(this);
        contentPane.add(novButton);
        
        decButton = new JButton("December");
        decButton.setBounds(690, 285, 203, 88);
        decButton.setFont(new Font("Arial", Font.PLAIN, 20));
        decButton.addActionListener(this);
        contentPane.add(decButton);
        
        // create Annual Expenditure button
        annualButton = new JButton("Annual Expenditure");
        annualButton.setBounds(51, 484, 270, 47);
        annualButton.setFont(new Font("Arial", Font.PLAIN, 20));
        annualButton.setBackground(new Color(255, 182, 193));
        annualButton.addActionListener(this);
        contentPane.add(annualButton);
        
        
        

        
        setContentPane(contentPane);
        
        JLabel Welcome = new JLabel("Welcome! "+username);
        Welcome.setHorizontalAlignment(SwingConstants.CENTER);
        Welcome.setFont(new Font("Arial", Font.PLAIN, 20));
        Welcome.setBounds(51, 10, 842, 31);
        contentPane.add(Welcome);
        
        JButton btnNewButton = new JButton("Reset Data");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
					Connection conn = DBConnection.getConnection();
					Statement stmt = conn.createStatement();
					String[] months = {"January", "February", "March", "April", "May", "June", 
		                    "July", "August", "September", "October", "November", "December"};
					
					for (String month : months) { // iterate through months
					String tableName = username + "_" + month;
					String sql = "TRUNCATE TABLE " + tableName;
					stmt.execute(sql);
					
					}
					stmt.close();
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
        	}
        });
        btnNewButton.setBounds(753, 483, 140, 47);
        contentPane.add(btnNewButton);
        setLocationRelativeTo(null);
//        setVisible(true);
    }

	

	

	//private javax.swing.JButton janButton,febButton,marButton, aprButton,mayButton,junButton,julButton,augButton,sepButton,octButton,novButton,decButton,annualButton;
    
//    public static void main(String[] args) {
//        new MonthButtons();
//    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == janButton) {
            // handle January button action
        	
            System.out.println("January button clicked");
            
			MonthBudgetForm Form=new MonthBudgetForm(username,"January");
            Form.titleLabel.setText("January");
            setVisible(false);
            
        } else if (e.getSource() == febButton) {
            // handle February button action
            System.out.println("February button clicked");
            MonthBudgetForm Form=new MonthBudgetForm(username,"Febraury");
            Form.titleLabel.setText("Febraury");
            setVisible(false);
            
        } else if (e.getSource() == marButton) {
            // handle March button action
            System.out.println("March button clicked");
            
            MonthBudgetForm Form=new MonthBudgetForm(username,"March");
            Form.titleLabel.setText("March");
            setVisible(false);
            
        } else if (e.getSource() == aprButton) {
            // handle April button action
            System.out.println("April button clicked");
            MonthBudgetForm Form=new MonthBudgetForm(username,"April");
            Form.titleLabel.setText("April");
            setVisible(false);
            
        } else if (e.getSource() == mayButton) {
            // handle May button action
            System.out.println("May button clicked");
            MonthBudgetForm Form=new MonthBudgetForm(username,"May");
            Form.titleLabel.setText("May");
            setVisible(false);
            
        } else if (e.getSource() == junButton) {
            // handle June button action
            System.out.println("June button clicked");
            MonthBudgetForm Form=new MonthBudgetForm(username,"June");
            Form.titleLabel.setText("June");
            setVisible(false);
            
        } else if (e.getSource() == julButton) {
            // handle July button action
            System.out.println("July button clicked");
            MonthBudgetForm Form=new MonthBudgetForm(username,"July");
            Form.titleLabel.setText("July");
            setVisible(false);
            
        } else if (e.getSource() == augButton) {
            // handle August button action
            System.out.println("August button clicked");
            MonthBudgetForm Form=new MonthBudgetForm(username,"August");
            Form.titleLabel.setText("August");
            setVisible(false);
            
        } else if (e.getSource() == sepButton) {
            // handle September button action
            System.out.println("September button clicked");
            MonthBudgetForm Form=new MonthBudgetForm(username,"September");
            Form.titleLabel.setText("September");
            setVisible(false);
            
        } else if (e.getSource() == octButton) {
            // handle October button action
            System.out.println("October button clicked");
            MonthBudgetForm Form=new MonthBudgetForm(username,"October");
            Form.titleLabel.setText("October");
            setVisible(false);
            
        } else if (e.getSource() == novButton) {
            // handle November button action
            System.out.println("November button clicked");
            MonthBudgetForm Form=new MonthBudgetForm(username,"November");
            Form.titleLabel.setText("November");
            setVisible(false);
            
        } else if (e.getSource() == decButton) {
            // handle December button action
            System.out.println("December button clicked");
            MonthBudgetForm Form=new MonthBudgetForm(username,"December");
            Form.titleLabel.setText("December");
            setVisible(false);
            
        } else if (e.getSource() == annualButton) {
            // handle Annual Expenditure button action
            System.out.println("Annual Expenditure button clicked");
            Annual_Exp annualObj=new Annual_Exp(username);
            annualObj.setVisible(true);
        }
    }
}
