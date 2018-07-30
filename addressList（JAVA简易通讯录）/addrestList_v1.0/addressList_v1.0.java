import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.*;
import java.sql.Statement;

public class addressList extends JFrame implements ActionListener {	 
	JLabel lbltitle, lblname, lblphone;
	JButton search,change,insert;
	JTextField tfname, tfphone; 
	JTextArea ta;
	JPanel p;
	String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    String connectionURL = "jdbc:derby:myDB;create=true";
    //建立界面
	addressList(){	
		setTitle("简易数据库");
		setBounds(300,200,400,250);
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		
		p = new JPanel();
		p.setLayout(new FlowLayout());
		lblname = new JLabel("姓名：");
		lblphone = new JLabel("手机号：");
		tfname = new JTextField(11);
		tfphone = new JTextField(11);
		search = new JButton("查询");
		change = new JButton("修改");
		insert = new JButton("添加");
		ta = new JTextArea(8,30);

		p.add(lblname);		
		p.add(tfname);	
		p.add(lblphone);
		p.add(tfphone);
		p.add(search);
		p.add(change);
		p.add(insert);
		p.add(ta);
		setContentPane(p);
		
		//事件驱动
		search.addActionListener(this);
		change.addActionListener(this);
		insert.addActionListener(this);		
	}
	//添加动作 
	public void actionPerformed(ActionEvent e){
		Object obj = e.getSource();   
		String str="";
		if(obj==search) { //查找数据,如果写的是 select phone, rs.getString() 只能写一个，否则出错只输出第一个查找到值
			str ="select * from myTable where name = '"+tfname.getText()+"'";	
		} else if (obj==change){ //改变数据	
			str ="update myTable set phone = "+tfphone.getText()+" where name = '"+tfname.getText()+"'";
		} else if (obj==insert) { //添加数据			
			str = "insert into myTable (name, phone) values ('"+tfname.getText()+"',"+tfphone.getText()+")";
		}
		connect(str);
	}
	//链接数据库
	void connect(String str) {
		String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	    String connectionURL = "jdbc:derby:myDB;create=true";
	    boolean hasName = tfname.getText().length()>0;
        boolean hasPhone = tfphone.getText().length()>0;
		try { 
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(connectionURL);
	        Statement st = connection.createStatement();	
	        
	        st.execute(str);
	        if(!hasName&&!hasPhone||hasName&&hasPhone) str="select * from myTable";
	        
	        //输出
	        ResultSet rs = st.executeQuery(str);	       
	        ta.setText("");
	        while (rs.next()) {
	            ta.append(rs.getString(1)+" ");
	    		ta.append(rs.getString(2)+"\n");
	    	}	        
	        connection.close();
		} catch(Exception e2) {}
	}
	public static void main(String args[]) throws SQLException, ClassNotFoundException {
		addressList frm = new addressList();
		frm.setVisible(true);      
	}
}