package AccountBook;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.awt.event.ActionEvent;

public class AccountEdit extends JFrame {

	private JPanel contentPane;
	private JTextField enterDate;
	private JTextField amount;
	private JTextField Detail;
	private static JTextField incomeSum;
	private static JTextField expendSum;
	private JTable table;
    public static String idck;
    static JRadioButton income;
    static JRadioButton expend;
    static String sql;
    static String sql2;
    static DefaultTableModel model;
    static String check[][];
	String type = null;
	static int incomeAvg[] = new int[255];
	static int iSum = 0;
	static int expendAvg[] = new int[255];
	static int eSum = 0;
	
	

    public static Connection getConnection() throws ClassNotFoundException, SQLException  {
        
        String url = "jdbc:mysql://localhost:3306/accountbook";
        String user = "root";
        String pwd = "aa9509481";
        Connection conn = null;
        
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, pwd);
            
        return conn;
    }
    
    public static void refresh(String idck) throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();
        String sql = "SELECT * FROM Account WHERE AC_USER_id = " + "'" + idck + "'";
        String sql2 = "SELECT * FROM Detail WHERE DE_USER_id = " + "'" + idck + "'";
        ResultSet rs = null;
        Statement stmt = null;
        ResultSet rs2 = null;
        Statement stmt2 = null;
        int count = 0;
        String contents[] = new String[5];
        
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        stmt2 = conn.createStatement();
        rs2 = stmt2.executeQuery(sql2);
        
        while(rs.next() && rs2.next()) {
        	check[count][0] = rs.getString("AC_id");
            contents[0] = rs.getString("AC_id");
            
        	check[count][1] = rs.getString("AC_type");
            if(rs.getString("AC_type").equals("1")) {
            	contents[1] = "수입";
            }
            else if(rs.getString("AC_type").equals("0")) {
            	contents[1] = "지출";
            }
        	
        	check[count][2] = rs.getString("AC_money");
        	contents[2] = rs.getString("AC_money");
        	
        	check[count][3] = rs.getString("AC_date");
        	contents[3] = rs.getString("AC_date");
        	
        	check[count][4] = rs2.getString("DE_desc");
        	contents[4] = rs2.getString("DE_desc");
        	
            count++;
        	model.addRow(contents);
        }
        
        if(rs != null) 
			rs.close();
        if(stmt != null) 
			stmt.close();
        if(rs2 != null) 
			rs2.close();
        if(stmt2 != null) 
			stmt2.close();
        if(conn != null) 
			conn.close();
    }
    
    public static void average(String idck) throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();
        String sql = "SELECT AC_type FROM Account Where AC_USER_id = " + "'" + idck + "'";
        String sql2 = "SELECT AC_money FROM Account Where AC_USER_id = " + "'" + idck + "'";
        ResultSet rs = null;
        Statement stmt = null;
        ResultSet rs2 = null;
        Statement stmt2 = null;
        
        iSum=0;
        eSum=0;
        
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        stmt2 = conn.createStatement();
        rs2 = stmt2.executeQuery(sql2);
        
        while(rs.next() && rs2.next()) {
        	if(rs.getString("AC_type").equals("1")) {
        		incomeAvg[0] = Integer.parseInt(rs2.getString("AC_money"));
        		for(int i=0; i<incomeAvg.length; i++) {
            		iSum = incomeAvg[i] + iSum;
        		}
				incomeSum.setText(("" + iSum).toString());
        	}
        	else if(rs.getString("AC_type").equals("0")) {
        		expendAvg[0] = Integer.parseInt(rs2.getString("AC_money"));
        		for(int i=0; i<expendAvg.length; i++) {
            		eSum = expendAvg[i] + eSum;
        		}
				expendSum.setText(("" + eSum).toString());
        	}
        }
        
        if(rs != null) 
			rs.close();
        if(stmt != null) 
			stmt.close();
        if(rs2 != null) 
			rs2.close();
        if(stmt2 != null) 
			stmt2.close();
        if(conn != null) 
			conn.close();
    }
    
    public static void delete(Object date) throws ClassNotFoundException, SQLException {
        //삭제기능
        Connection conn = getConnection();
        
        String sql = "delete from Account where AC_date = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1, date);
        int res = pstmt.executeUpdate();
        
        if(res > 0){
    		average(idck);
			JOptionPane.showMessageDialog(null,  "삭제됨");
        } else {
    		average(idck);
			JOptionPane.showMessageDialog(null,  "삭제 실패");
		}
    }
    
    public static void insert(String USER_id, String type, String date, String money, String desc) throws ClassNotFoundException, SQLException {
        //입력하는 메소드
        Connection conn = getConnection();
        sql = "insert into Account(AC_USER_id, AC_type, AC_date, AC_money) values(?,?,?,?)";
        sql2 = "insert into Detail(DE_USER_id, DE_date, DE_desc) values(?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        PreparedStatement pstmt2 = conn.prepareStatement(sql2);
        
        pstmt.setString(1, USER_id);
        pstmt.setString(2, type);
        pstmt.setString(3, date);
        pstmt.setString(4, money);

        pstmt2.setString(1, USER_id);
        pstmt2.setString(2, date);
        pstmt2.setString(3, desc);
        
        int res = pstmt.executeUpdate();
        int res2 = pstmt2.executeUpdate();
        if(res > 0){
            System.out.println("처리 완료");
        }
        if(res2 > 0){
			model.setNumRows(0);
        	refresh(idck);
			average(idck);
			JOptionPane.showMessageDialog(null, "저장 완료");
        }
        
        if(pstmt != null) 
			pstmt.close();
        if(pstmt2 != null) 
			pstmt2.close();
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
					AccountEdit frame = new AccountEdit(idck);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public AccountEdit(String idck) throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();
        String sql = "SELECT * FROM Account WHERE AC_USER_id = " + "'" + idck + "'";
        String sql2 = "SELECT * FROM Detail WHERE DE_USER_id = " + "'" + idck + "'";
        ResultSet rs = null;
        Statement stmt = null;
        ResultSet rs2 = null;
        Statement stmt2 = null;
        int count = 0;
        Object items[][] = null;
        String contents[] = new String[5];
        check = new String[5000][5];
		String header[] = {"순차", "수입/지출", "금액", "일자", "상세내역"};
		model = new DefaultTableModel(items, header);
        
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        stmt2 = conn.createStatement();
        rs2 = stmt2.executeQuery(sql2);
        
        while(rs.next() && rs2.next()) {
        	check[count][0] = rs.getString("AC_id");
            contents[0] = rs.getString("AC_id");
            
        	check[count][1] = rs.getString("AC_type");
            if(rs.getString("AC_type").equals("1")) {
            	contents[1] = "수입";
            }
            else if(rs.getString("AC_type").equals("0")) {
            	contents[1] = "지출";
            }
        	
        	check[count][2] = rs.getString("AC_money");
        	contents[2] = rs.getString("AC_money");
        	
        	check[count][3] = rs.getString("AC_date");
        	contents[3] = rs.getString("AC_date");
        	
        	check[count][4] = rs2.getString("DE_desc");
        	contents[4] = rs2.getString("DE_desc");
        	
            count++;
        	model.addRow(contents);
        }
        
        if(rs != null) 
			rs.close();
        if(stmt != null) 
			stmt.close();
        if(rs2 != null) 
			rs2.close();
        if(stmt2 != null) 
			stmt2.close();
        if(conn != null) 
			conn.close();
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 789, 465);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uAE30\uC785\uC77C\uC790 : ");
		lblNewLabel.setBounds(16, 48, 74, 15);
		panel.add(lblNewLabel);
		
		enterDate = new JTextField();
		enterDate.setText("20YY-MM-DD HH-MM-SS");
		enterDate.setBounds(91, 45, 182, 21);
		panel.add(enterDate);
		enterDate.setColumns(10);
		
		ButtonGroup group = new ButtonGroup();
		
		income = new JRadioButton("\uC218\uC785");
		income.setBounds(63, 96, 57, 23);
		group.add(income);
		panel.add(income);
		
		expend = new JRadioButton("\uC9C0\uCD9C");
		expend.setBounds(149, 96, 57, 23);
		group.add(expend);
		panel.add(expend);
		
		JLabel lblNewLabel_1 = new JLabel("\uAE08\uC561 : ");
		lblNewLabel_1.setBounds(33, 159, 57, 15);
		panel.add(lblNewLabel_1);
		
		amount = new JTextField();
		amount.setBounds(102, 156, 133, 21);
		panel.add(amount);
		amount.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("\uC0C1\uC138 \uB0B4\uC5ED");
		lblNewLabel_2.setBounds(33, 195, 57, 15);
		panel.add(lblNewLabel_2);
		
		Detail = new JTextField();
		Detail.setBounds(33, 220, 202, 113);
		panel.add(Detail);
		Detail.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("\uC218\uC775 \uD569\uACC4 : ");
		lblNewLabel_3.setBounds(30, 376, 74, 15);
		panel.add(lblNewLabel_3);
		
		incomeSum = new JTextField();
		incomeSum.setEditable(false);
		incomeSum.setBounds(99, 373, 116, 21);
		panel.add(incomeSum);
		incomeSum.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("\uC9C0\uCD9C \uD569\uACC4 : ");
		lblNewLabel_4.setBounds(227, 376, 74, 15);
		panel.add(lblNewLabel_4);
		
		expendSum = new JTextField();
		expendSum.setEditable(false);
		expendSum.setBounds(296, 373, 116, 21);
		panel.add(expendSum);
		expendSum.setColumns(10);
		
		JButton delete = new JButton("\uC0AD\uC81C");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==delete) {
					try {
						delete(table.getValueAt(table.getSelectedRow(), 3));
						model.removeRow(table.getSelectedRow());
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		delete.setBounds(436, 372, 97, 23);
		panel.add(delete);
		
		JButton submit = new JButton("\uD655\uC778");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==submit) {
					String USER_id = idck;
					if (income.isSelected() == true) {
						type = "1";
					}
					else if (expend.isSelected() == true) {
						type = "0";
					}
					String date = enterDate.getText();
					String money = amount.getText();
					String desc = Detail.getText();
					
					try {
				        if(enterDate.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Enter DateTime");
						}
						else if(amount.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Enter Amount");
						}
						else if(!enterDate.getText().equals("") & !amount.getText().equals("")) {
							insert(USER_id, type, date, money, desc);
							enterDate.setText("20YY-MM-DD HH-MM-SS");
							amount.setText("");
							Detail.setText("");
							income.setSelected(false);
							expend.setSelected(false);
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
		submit.setBounds(545, 372, 97, 23);
		panel.add(submit);
		
		JButton back = new JButton("\uB4A4\uB85C");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==back) {
					MainPage frame = new MainPage(idck);
					frame.setTitle("Main");
					frame.setVisible(true);
			        setVisible(false);
				}
			}
		});
		back.setBounds(654, 372, 97, 23);
		panel.add(back);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(285, 34, 466, 308);
		panel.add(scrollPane);

		table = new JTable(model);
		scrollPane.setViewportView(table);
		
		average(idck);
	}
}
