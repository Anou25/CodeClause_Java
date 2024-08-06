import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class JDBC_GUI_APP extends JFrame implements ActionListener{
	private JPanel contentPanel;
	private boolean trigger = false;
	private JLabel connection_creation_success_Prompt,insertion_success_Prompt,data_fetch_success_Prompt;
	private JTable view_data_table;
	private JTextField tablename_Line,id_Line,fullname_Line,age_Line,phone_Line;
	private JButton create_connect_Button,data_submit_Button;
	private Connection con;
	private String[][] data = {
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" },
            { "", "", "","" }
        };
	
	public void create_connect_tableinDB() {
		try {
			String table_name = tablename_Line.getText();
			System.out.print(table_name);
			if(table_name.isEmpty() || table_name.isBlank()) {
				connection_creation_success_Prompt.setForeground(new Color(237, 21, 21));
				connection_creation_success_Prompt.setText("Table not Found");
			}
			else {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sys as sysdba","TRIsha25");
			
				Statement smt = con.createStatement();
				smt.executeUpdate("create table "+ tablename_Line.getText() +"(id_no number(10) primary key, "
						+ "name varchar2(40),age number(3),phone number(10))");
			
				connection_creation_success_Prompt.setForeground(new Color(14, 150, 23));
				connection_creation_success_Prompt.setText("Table Created/Connected");
				trigger=true;
			}
		}
		catch(java.sql.SQLSyntaxErrorException e1) {
			System.out.print(e1);
			connection_creation_success_Prompt.setForeground(new Color(14, 150, 23));
			connection_creation_success_Prompt.setText("Table Created/Connected");
			trigger=true;
		}
		catch(Exception e) {
			System.out.print(e);
		}
	}
	
	public void insert_data_inTable() {
		try {
			String command = "insert into " + tablename_Line.getText() + " values(?,?,?,?)";
			PreparedStatement psmt = con.prepareStatement(command);
			
			int id_no = Integer.parseInt(id_Line.getText());
			String full_name = fullname_Line.getText();
			int age = Integer.parseInt(age_Line.getText());
			long ph_no  = Long.parseLong(phone_Line.getText());
			
			if (!full_name.contains("0") && !full_name.contains("1") && !full_name.contains("2") &&
				!full_name.contains("3") && !full_name.contains("4") && !full_name.contains("5") &&
				!full_name.contains("6") && !full_name.contains("7") && !full_name.contains("8") &&
				!full_name.contains("9")) 
			{
			
				psmt.setInt(1, id_no);
				psmt.setString(2, full_name);
				psmt.setInt(3, age);
				psmt.setLong(4, ph_no);
				psmt.executeUpdate();
			
				insertion_success_Prompt.setForeground(new Color(14, 150, 23));
				insertion_success_Prompt.setText("Data Inserted Successfully");
			}
			else {
				insertion_success_Prompt.setForeground(new Color(237, 21, 21));
				insertion_success_Prompt.setText("Invalid Name value");
			}
			display_data_fromTable();
		}
		catch(java.sql.SQLRecoverableException e1) {
			insertion_success_Prompt.setForeground(new Color(237, 21, 21));
			insertion_success_Prompt.setText("Reconnect Table");
		}
		catch(java.sql.SQLIntegrityConstraintViolationException e2) {
			insertion_success_Prompt.setForeground(new Color(237, 21, 21));
			insertion_success_Prompt.setText("ID already in use");
		}
		catch(java.sql.SQLDataException e1){
			insertion_success_Prompt.setForeground(new Color(237, 21, 21));
			insertion_success_Prompt.setText("Data value too long");
		}
		catch(Exception e) {
			insertion_success_Prompt.setForeground(new Color(237, 21, 21));
			insertion_success_Prompt.setText("Please re-enter all values");
		}
	}
	
	public void display_data_fromTable() {
		try {
			Statement smt = con.createStatement();
			ResultSet rset = smt.executeQuery("select * from "+ tablename_Line.getText());
			int i = 0;
	
			while(rset.next()) {
				String[] row = {"","","",""};
				row[0] = Integer.toString(rset.getInt(1));
				row[1] = rset.getString(2);
				row[2] = Integer.toString(rset.getInt(3));
				row[3] = Long.toString(rset.getLong(4));
				data[i] = row;
				i = i+1;
			}
			
			view_data_table.repaint();
			data_fetch_success_Prompt.setForeground(new Color(14, 150, 23));
			data_fetch_success_Prompt.setText("Data Fetched Successfully");
		}
		catch(java.sql.SQLRecoverableException e1) {
			data_fetch_success_Prompt.setForeground(new Color(237, 21, 21));
			data_fetch_success_Prompt.setText("Reconnect Table");
		}
		catch(java.sql.SQLException e2) {
			data_fetch_success_Prompt.setForeground(new Color(237, 21, 21));
			data_fetch_success_Prompt.setText("Data Fetch Failure");
		}
		catch(Exception e) {
			System.out.print(e);
		}
	}
	
	public void actionPerformed(ActionEvent a){
		if(a.getSource()==create_connect_Button) {
			create_connect_tableinDB();
		}
		else if(a.getSource()==data_submit_Button && trigger== true) {
			insert_data_inTable();
		}
	}
	
	public JDBC_GUI_APP() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,1366,768);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5,5,5,5));
		contentPanel.setLayout(new BorderLayout(0,0));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		
		JLabel title = new JLabel("JDBC GUI APPLICATION");
		title.setFont(new Font("Verdana",Font.BOLD,35));
		title.setBounds(450,10,500,50);
		
		JLabel create_connect_Prompt = new JLabel("Create or Connect to a Table: ");
		create_connect_Prompt.setFont(new Font("Verdana",Font.BOLD,20));
		create_connect_Prompt.setBounds(50,80,450,50);
		
		JLabel tablename_Prompt = new JLabel("Enter Table name: ");
		tablename_Prompt.setFont(new Font("Verdana",Font.BOLD,16));
		tablename_Prompt.setBounds(100,120,300,50);
		
		tablename_Line = new JTextField(300);
		tablename_Line.setBounds(400,132,250,30);
		
		create_connect_Button = new JButton("Create/Connect");
		create_connect_Button.setBounds(850,125,130,40);
		create_connect_Button.addActionListener(this);
		
		connection_creation_success_Prompt = new JLabel("");
		connection_creation_success_Prompt.setFont(new Font("Verdana",Font.BOLD,20));
		connection_creation_success_Prompt.setBounds(1030,120,450,50);
		
		JLabel insert_data_Prompt = new JLabel("Enter Data in Table: ");
		insert_data_Prompt.setFont(new Font("Verdana",Font.BOLD,20));
		insert_data_Prompt.setBounds(50,180,300,50);
		
		JLabel insert_idno_Prompt = new JLabel("Enter ID no: ");
		insert_idno_Prompt.setFont(new Font("Verdana",Font.BOLD,16));
		insert_idno_Prompt.setBounds(100,220,150,50);
		
		id_Line = new JTextField(300);
		id_Line.setBounds(255,231,150,30);
		
		JLabel insert_fullname_Prompt = new JLabel("Enter Full Name: ");
		insert_fullname_Prompt.setFont(new Font("Verdana",Font.BOLD,16));
		insert_fullname_Prompt.setBounds(100,260,150,50);
		
		fullname_Line = new JTextField(300);
		fullname_Line.setBounds(255,273,150,30);
		
		JLabel insert_age_Prompt = new JLabel("Enter Age : ");
		insert_age_Prompt.setFont(new Font("Verdana",Font.BOLD,16));
		insert_age_Prompt.setBounds(450,220,150,50);
		
		age_Line = new JTextField(300);
		age_Line.setBounds(610,231,150,30);
		
		JLabel insert_phone_Prompt = new JLabel("Enter Phone no: ");
		insert_phone_Prompt.setFont(new Font("Verdana",Font.BOLD,16));
		insert_phone_Prompt.setBounds(450,260,150,50);
		
		phone_Line = new JTextField(300);
		phone_Line.setBounds(610,273,150,30);
		
		data_submit_Button = new JButton("Submit Data");
		data_submit_Button.setBounds(850,250,130,40);
		data_submit_Button.addActionListener(this);
		
		insertion_success_Prompt = new JLabel("");
		insertion_success_Prompt.setFont(new Font("Verdana",Font.BOLD,20));
		insertion_success_Prompt.setBounds(1030,245,450,50);
		
		JLabel view_data_Prompt = new JLabel("View Table Data : ");
		view_data_Prompt.setFont(new Font("Verdana",Font.BOLD,20));
		view_data_Prompt.setBounds(50,320,250,50);
		
		String[] columnNames = { "ID NO", "NAME", "AGE", "PHONE" };
		
		view_data_table = new JTable(data, columnNames);
		view_data_table.setFont(new Font("Arial",Font.LAYOUT_RIGHT_TO_LEFT,15));
		view_data_table.setBounds(30, 40, 200, 300);
		
		JScrollPane sp = new JScrollPane(view_data_table);
		sp.setBounds(100,380,880,300);
		
		data_fetch_success_Prompt = new JLabel("");
		data_fetch_success_Prompt.setFont(new Font("Verdana",Font.BOLD,20));
		data_fetch_success_Prompt.setBounds(1030,475,450,50);
		
		contentPanel.add(title);
		contentPanel.add(create_connect_Prompt);
		contentPanel.add(tablename_Prompt);
		contentPanel.add(tablename_Line);
		contentPanel.add(create_connect_Button);
		contentPanel.add(connection_creation_success_Prompt);
		contentPanel.add(insert_data_Prompt);
		contentPanel.add(insert_idno_Prompt);
		contentPanel.add(id_Line);
		contentPanel.add(insert_fullname_Prompt);
		contentPanel.add(fullname_Line);
		contentPanel.add(insert_phone_Prompt);
		contentPanel.add(phone_Line);
		contentPanel.add(insert_age_Prompt);
		contentPanel.add(age_Line);
		contentPanel.add(data_submit_Button);
		contentPanel.add(insertion_success_Prompt);
		contentPanel.add(view_data_Prompt);
		contentPanel.add(sp);
		contentPanel.add(data_fetch_success_Prompt);
	}
	
	public static void main(String[] args) {
			try {
				JDBC_GUI_APP frame = new JDBC_GUI_APP();
				frame.setVisible(true);
			}
			catch(Exception e) {
				System.out.println(e);
			}
	}
}