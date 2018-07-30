import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.*;
import java.sql.Statement;
import java.util.Enumeration;

public class addressList extends JFrame implements ActionListener {	
	JPanel p;
	JLabel lbltitle, lblname, lblphone;
	JButton search,change,insert, delete;
	JTextField tfname, tfphone; 
	JTextArea ta;
	JScrollPane scrollPane;
	JMenuBar bar;
	JMenu file, help;
	JMenuItem refresh, usage, about;
    // 建立界面
	addressList(){	
		setTitle("简易数据库");// 设置标题	
		this.setSize(436, 720);// 设置宽高  
		this.setLocationRelativeTo(null);// 窗体居中显示 
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
		
		// 界面初始化				
		p = new JPanel();
		lblname = new JLabel("姓名：");
		lblphone = new JLabel("手机号：");
		tfname = new JTextField(12);
		tfphone = new JTextField(12);
		search = new JButton("查询");
		change = new JButton("修改");
		insert = new JButton("添加");
		delete = new JButton("删除");
		// 设置菜单栏
		bar = new JMenuBar();
		file = new JMenu("选项");
		help = new JMenu("帮助");
		refresh = new JMenuItem("初始化");		
		usage= new JMenuItem("使用帮助");	
		about = new JMenuItem("关于");
		
		file.add(refresh);
		help.add(usage);
		help.add(about);
		bar.add(file);
		bar.add(help);
		
		// 设置文本框及滚动条
		ta = new JTextArea(25,41);
		ta.setLineWrap(true);		
		scrollPane = new JScrollPane(ta);			
		scrollPane.setVerticalScrollBarPolicy(                
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// 加入面板
		p.add(bar);		
		this.setJMenuBar(bar);
		p.add(lblname);	
		p.add(tfname);	
		p.add(lblphone);
		p.add(tfphone);		
		p.add(search);
		p.add(change);
		p.add(delete);
		p.add(insert);	
		p.add(scrollPane);		
		
		setContentPane(p);
		
		// 作为提示
		ta.append("\n  初始使用请点击【帮助】的【使用帮助】~");
		
		// 事件驱动
		search.addActionListener(this);
		change.addActionListener(this);
		insert.addActionListener(this);		
		refresh.addActionListener(this);
		delete.addActionListener(this);
		usage.addActionListener(this);
		about.addActionListener(this);		
	}
	
	// 添加动作	
	public void actionPerformed(ActionEvent e){
		Object obj = e.getSource();   
		String str="";
		String name = tfname.getText();
		String phone = tfphone.getText();
		
		// 查找数据
		if(obj==search) { 
			if(name.length()==0&&phone.length()==0) {
				str ="select * from myTable";						
			} else if(name.length()>0&&phone.length()==0){
				str ="select * from myTable where name = '"+name+"'";								
			} else if(name.length()==0&&phone.length()>0) {
				str ="select * from myTable where phone = "+phone;				
			} else {
				str ="select * from myTable where name = '"+name+"' and phone = "+phone;			
			}			
			connect(str);
		} 
		// 改变数据
		else if (obj==change){ 	
			str ="update myTable set phone = "+phone+" where name = '"+name+"'";
			connect(str);			
			connect("select * from myTable");
		} 
		// 添加数据
		else if (obj==insert) { 
			if(name.length()!=0||phone.length()!=0) {
				str = "insert into myTable (name, phone) values ('"+name+"',"+phone+")";
				connect(str);
				connect("select * from myTable");
			}		
		} 
		// 初始化，新建表
		else if(obj==refresh) { 
			if(choice()) {
				str = "create table myTable(name varchar(20), phone int )";
				connect(str);
				connect("delete from myTable");
				tfname.setText("");
				tfphone.setText("");
				ta.setText("\n 初始化成功！");
			}			
		} 
		// 删除数据
		else if(obj==delete) {
			if(choice()) { // 如果有填写姓名和手机，就只会删除此条数据，否则会删除对应条件下的所有数据
				if(name.length()>0&&phone.length()==0){
					str ="delete from myTable where name = '"+name+"'";								
				} else if(name.length()==0&&phone.length()>0) {
					str ="delete from myTable where phone = "+phone;				
				} else {
					str ="delete from myTable where name = '"+name+"' and phone = "+phone;			
				}
				connect(str);
				connect("select * from myTable");
			}		
		} 
		// 使用帮助
		else if(obj==usage) { 
			ta.setText("\n");
			ta.append("【使用帮助】\n\n");
			ta.append("1. 第一次使用时，在【选项】中选择【初始化】, 新建数据库。此过程大概十秒钟。"
					+ "另外数据存储在，与此软件同一目录下的 myDB 文件夹与 derby.log 文件中。\n\n");
			ta.append("2. 【查询】功能详解：\n"
					+ "\n①当姓名与手机号均空时，输出所有数据；"
					+ "\n②当姓名不空、手机号空时，输出有关此姓名的所有数据；"
					+ "\n③当姓名空、手机号不空时，输出有关此手机号的所有数据。"
					+ "\n\n");
			ta.append("3. 【修改】功能详解：\n"
					+ "\n当姓名与电话均不空时，更改此姓名的手机号（目前只能依据姓名来修改手机号）。"
					+ "\n\n");
			ta.append("4. 【添加】功能详解：\n"
					+ "\n当姓名与电话均不空时，添加此姓名与手机号。"
					+ "\n\n");
			ta.append("5. 【删除】 功能详解：\n"
					+ "\n①当姓名与手机均不空时，删除此条数据；"
					+ "\n②当姓名不空、手机号空时，删除有关此姓名的所有数据；"
					+ "\n②当姓名空、手机号不空时，删除有关此手机号的所有数据；"
					+ "\n③如需删除所有数据，在菜单栏的【选项】中【初始化】。"
					+ "\n\n");
			ta.setCaretPosition(0);// 使滚动条定位在最上面
		}
		// 关于软件
		else if(obj==about) { 
			ta.setText("\n");
			ta.append("【关于】\n\n"); 
			ta.append(" 软件是由 evenIfAlsoGo 制作。\n\n"
					+ " Even If Also Go.\n\n"
					+ " 个人公众号：随风前行。\n\n"
					+ " 软件源码：https://github.com/evenIfAlsoGo \n\n");
		}		
	}	
	
	// 链接数据库
	void connect(String str) {
		String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	    String connectionURL = "jdbc:derby:myDB;create=true";
		try { 
			Class.forName(driver);
			Connection connection = DriverManager.getConnection(connectionURL);
	        Statement st = connection.createStatement();	
	        
	        st.execute(str);        
	        ta.setText("");
	        ResultSet rs = st.executeQuery(str);
			while (rs.next()) {
				ta.append(" "+rs.getString(1)+" ");
				ta.append(" "+rs.getString(2)+"\n");
			}
		    connection.close();
		} catch(Exception e2) {}
	}	
	
	// 确认框
	boolean choice() {
		int n = JOptionPane.showConfirmDialog(this,"确认这样做吗？","确认对话框",  
	               JOptionPane.YES_NO_OPTION );  
		if(n==JOptionPane.YES_OPTION) return true;  
		return false;
	}
	
	// 设置全局字体
	public static void initGlobalFontSetting(Font fnt){
	    FontUIResource fontRes = new FontUIResource(fnt);
	    for(Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();){
	        Object key = keys.nextElement();
	        Object value = UIManager.get(key);
	        if(value instanceof FontUIResource)
	            UIManager.put(key, fontRes);
	    }
	}
	
	// 主入口
	public static void main(String args[]) throws SQLException, ClassNotFoundException {
		initGlobalFontSetting(new Font("宋体",Font.PLAIN,18));
		addressList frm = new addressList();
		frm.setVisible(true);
	}
}

