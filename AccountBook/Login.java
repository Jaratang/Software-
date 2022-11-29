package AccountBook;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField id;
	public String idck;
	private JPasswordField pwd;

    public static Connection getConnection() throws ClassNotFoundException, SQLException  {
        
        String url = "jdbc:mysql://localhost:3306/accountbook";
        String user = "root";
        String pwd = "aa9509481";
        Connection conn = null;
        
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, pwd);
            
        return conn;
    }
    
    public static int login(String id, String pwd) throws ClassNotFoundException, SQLException {
        Connection con = getConnection();
        String sql = "select * from users where USER_id = ? and USER_pw = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, id);
        pstmt.setString(2, pwd);
        ResultSet res = pstmt.executeQuery();
        int tf = 0;
		
        if(res.next()) {
        	tf = 1;
        }
        else {
        	tf = 0;
        }
        return tf;
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 356, 261);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel.setBounds(30, 58, 57, 15);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("PASSWORD");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(30, 117, 91, 15);
		panel.add(lblNewLabel_1);
		
		id = new JTextField();
		id.setBounds(132, 55, 169, 21);
		panel.add(id);
		id.setColumns(10);
		
		pwd = new JPasswordField();
		pwd.setBounds(133, 114, 168, 21);
		panel.add(pwd);
		
		JButton login = new JButton("\uB85C\uADF8\uC778");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==login) {
					String uid = id.getText();
					String upw = pwd.getText();
					try {
						if(login(uid, upw) == 1) {
							idck = id.getText();
							MainPage frame = new MainPage(idck);
							frame.setTitle("User Page");
							frame.setVisible(true);
					        setVisible(false);
						}
						else if(login(uid, upw) == 0) {
							JOptionPane.showMessageDialog(null,  "Check your ID or Password.");
						}
					} catch (ClassNotFoundException | SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		login.setFont(new Font("굴림", Font.PLAIN, 15));
		login.setBounds(204, 167, 97, 23);
		panel.add(login);
		
		JButton register = new JButton("\uD68C\uC6D0\uAC00\uC785");
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==register) {
					Register frame = new Register();
					frame.setTitle("Register");
					frame.setVisible(true);
				}
			}
		});
		register.setFont(new Font("굴림", Font.PLAIN, 15));
		register.setBounds(98, 167, 97, 23);
		panel.add(register);
		
		JLabel lblNewLabel_2 = new JLabel("\uAC00\uACC4\uBD80");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(12, 10, 306, 31);
		panel.add(lblNewLabel_2);
	}
}
