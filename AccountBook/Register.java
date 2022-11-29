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
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Register extends JFrame {

	private JPanel contentPane;
	private JTextField id;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTextField phone;
	private JLabel lblNewLabel_3;
	private JTextField name;
	private JButton submit;
	private JButton cancel;
	private JLabel lblNewLabel_4;
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
    
    public static void insert(String id, String pwd, String phone, String name) throws ClassNotFoundException, SQLException {
        //입력하는 메소드
        Connection conn = getConnection();
        String sql = "insert into users(USER_id, USER_pw, USER_phone, USER_name) values(?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        pstmt.setString(1, id);
        pstmt.setString(2, pwd);
        pstmt.setString(3, phone);
        pstmt.setString(4, name);
        
        int res = pstmt.executeUpdate();
        if(res > 0){
            System.out.println("처리 완료");
        }
        
        if(pstmt != null) 
			pstmt.close();
        if(conn != null) 
			conn.close();
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register frame = new Register();
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
	public Register() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 361, 389);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel.setBounds(47, 73, 57, 15);
		panel.add(lblNewLabel);
		
		id = new JTextField();
		id.setBounds(177, 70, 116, 21);
		panel.add(id);
		id.setColumns(10);
		
		pwd = new JPasswordField();
		pwd.setBounds(177, 125, 116, 21);
		panel.add(pwd);
		
		lblNewLabel_1 = new JLabel("PASSWORD");
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(47, 128, 92, 15);
		panel.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Phone");
		lblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(47, 190, 57, 15);
		panel.add(lblNewLabel_2);
		
		phone = new JTextField();
		phone.setBounds(177, 187, 116, 21);
		panel.add(phone);
		phone.setColumns(10);
		
		lblNewLabel_3 = new JLabel("Name");
		lblNewLabel_3.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(47, 242, 57, 15);
		panel.add(lblNewLabel_3);
		
		name = new JTextField();
		name.setBounds(177, 239, 116, 21);
		panel.add(name);
		name.setColumns(10);
		
		submit = new JButton("\uD655\uC778");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==submit) {
					String uid = id.getText();
					String upw = pwd.getText();
					String uphone = phone.getText();
					String uname = name.getText();
					
					try {
				        Connection conn = getConnection();
						String sql="SELECT COUNT(*) FROM users WHERE USER_id=?";
				        PreparedStatement pstmt = conn.prepareStatement(sql);
				        pstmt.setString(1, uid);
				        ResultSet rs = pstmt.executeQuery();
				        rs.next();
				        int check =rs.getInt(1); //있으면 1, 없으면 0
				        rs.close();
				        
				        //중복확인 및 입력확인
				        if(check == 1) {
							JOptionPane.showMessageDialog(null,  "Duplicate accounts exist.");
				        }
				        else if(id.getText().equals("")) {
							JOptionPane.showMessageDialog(null,  "Enter your ID");
						}
						else if(pwd.getText().equals("")) {
							JOptionPane.showMessageDialog(null,  "Enter your Password");
						}
						else if(phone.getText().equals("")) {
							JOptionPane.showMessageDialog(null,  "Enter your Phone");
						}
						else {
							insert(uid, upw,  uphone,uname);
							setVisible(false);
						}
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		submit.setBounds(42, 302, 97, 23);
		panel.add(submit);
		
		cancel = new JButton("\uCDE8\uC18C");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==cancel) {
					setVisible(false);
				}
			}
		});
		cancel.setBounds(196, 302, 97, 23);
		panel.add(cancel);
		
		lblNewLabel_4 = new JLabel("\uD68C\uC6D0\uAC00\uC785");
		lblNewLabel_4.setFont(new Font("굴림", Font.PLAIN, 20));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(12, 10, 311, 50);
		panel.add(lblNewLabel_4);
	}
}
