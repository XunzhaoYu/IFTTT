package IFTTT;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.border.TitledBorder;

public class ChangeTask extends JFrame{
	protected MainFrame mainFrame;
	private JLayeredPane layeredPane;
	private JPanel titlePanel,selectPanel,
	        ifPanel,ifSubPanel1,ifSubPanel2,
	        thatPanel,thatSubPanel1,thatSubPanel2;
	private JComboBox selectTask;
	private JTextArea newName,timeInput,idText,       //为了添加“重置”按钮的功能，将所有有关输入的变量声明
	        idText1,weiboContent,idText2,idText3,gmailContent;
	private calendar dateInput;
	private String nowTime;
	private JPasswordField passwordText,passwordText1,passwordText2; 
	private JButton sure,reset,cancel;
	private JRadioButton timeSelect,gmailSelect1,weiboSelect,gmailSelect2;
	private Task task;
	
	public ChangeTask(MainFrame frame)		//构造函数
	{
		mainFrame = frame;
		layeredPane = getLayeredPane();
		addBackImage();
		addTitlePanel();
		addSelectPanel();
		addIfPanel();
		addThatPanel();
		addListener();
		addChangeTaskListener();
		setTitle("修改任务");
		setSize(600,410);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	
	}

	private void addBackImage()
	{
		JPanel backImagePanel = new JPanel();
		backImagePanel.setLayout(null);
		
		ImageIcon backImage = new ImageIcon("image/createTask_background.jpg");
		JLabel backImageLabel = new JLabel(backImage); 
		backImageLabel.setBounds(0,0,backImage.getIconWidth(),backImage.getIconHeight());
		
		backImagePanel.setBounds(0,0,600,400); 
		backImagePanel.add(backImageLabel);
		backImagePanel.setVisible(true);
		layeredPane.add(backImagePanel,new Integer(0));
	}
	
	private void addTitlePanel()//标题面板，占据总空间(40,50,60,300)
	{
		titlePanel = new JPanel();
		titlePanel.setLayout(null);
		
		JLabel title = new JLabel("<html>修<br>改<br>任<br>务"); 
		title.setFont(new Font("Dialog",Font.BOLD,20));
		//title.setAlignmentX(TOP_ALIGNMENT)();
		title.setBounds(20,0,50,120);
		
		sure = new JButton("确定");
		//sure.setFont(new Font("Dialog",Font.BOLD,10));
		sure.setBounds(0,150,60,30);
		reset = new JButton("重置");
		reset.setBounds(0,200,60,30);
		cancel = new JButton("取消");
		cancel.setBounds(0,250,60,30);	
		
		titlePanel.setBounds(40,50,60,300);    
		titlePanel.setOpaque(false);
		titlePanel.add(title);
		titlePanel.add(sure);
		titlePanel.add(reset);
		titlePanel.add(cancel);
		layeredPane.add(titlePanel,new Integer(1));
	}
	
	private void addSelectPanel()//修改任务选择面板，占据总空间(120,50,440,30)
	{
		selectPanel = new JPanel();
		selectPanel.setLayout(null);
		
		JLabel selectLabel = new JLabel("选择需要修改的任务:");
		selectLabel.setBounds(0,0,140,20);
		
		selectTask = new JComboBox();
		selectTask.setBounds(140,0,150,20);
		
		newName = new JTextArea("请输入新的名称");
		newName.setBounds(300,0,130,20);
		
		selectPanel.setBounds(120,50,440,30);    
		selectPanel.setOpaque(false);
		selectPanel.add(selectLabel);
		selectPanel.add(selectTask);
		selectPanel.add(newName);
		layeredPane.add(selectPanel,new Integer(1));
	}
	
	private void addIfPanel()//THIS面板，占据总空间(120,80,200,270)
	{
		ifPanel = new JPanel();
		ifPanel.setLayout(null);
		BorderFactory.createEtchedBorder();
		TitledBorder ifBorder = new TitledBorder("");
		ifPanel.setBorder(BorderFactory.createTitledBorder(ifBorder,"IF THIS:",TitledBorder.LEFT,TitledBorder.TOP,new Font("Dialog",Font.BOLD,30)));
		//JLabel ifLabel = new JLabel("IF THIS:");
		//ifLabel.setFont(new Font("Dialog",Font.BOLD,30));
		//ifLabel.setBounds(5,0,200,50);
		
		ImageIcon timeImage = new ImageIcon("image/time.png");//if面板选项
		JLabel timeImageLabel = new JLabel(timeImage);
		timeImageLabel.setBounds(20,40,60,60);
		ImageIcon gmailImage = new ImageIcon("image/Gmail.jpg");
		JLabel gmailImageLabel = new JLabel(gmailImage);
		gmailImageLabel.setBounds(100,40,60,60);
		
		timeSelect= new JRadioButton("time");
		timeSelect.setSelected(true);
		timeSelect.setBounds(20,110,80,20);
		timeSelect.setOpaque(false);
		gmailSelect1= new JRadioButton("gmail");
		gmailSelect1.setSelected(false);
		gmailSelect1.setBounds(100,110,80,20);
		gmailSelect1.setOpaque(false);
		ButtonGroup ifButtonGroup = new ButtonGroup();
		ifButtonGroup.add(timeSelect);
		ifButtonGroup.add(gmailSelect1);
		
		//选择if时钟时出现的子面板设置
		ifSubPanel1 = new JPanel();
		ifSubPanel1.setLayout(null);
		
		JLabel date = new JLabel("日期：");
		date.setBounds(0,0,40,20);
		nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		//JTextArea dateInput = new JTextArea(tmp.substring(0, 10));
		//dateInput.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		dateInput = new calendar(this);
		dateInput.setBounds(40,0,120,20);
		
		JLabel time = new JLabel("时间：");
		time.setBounds(0,30,40,20);
		timeInput = new JTextArea(nowTime.substring(11,19));
		//timeInput.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		timeInput.setBounds(40,30,120,20);
		
		ifSubPanel1.setBounds(20,130,160,140); 
		ifSubPanel1.setOpaque(false);
		ifSubPanel1.add(date);
		ifSubPanel1.add(dateInput);
		ifSubPanel1.add(time);
		ifSubPanel1.add(timeInput);
		//选择if时钟时出现的子面板设置结束
		
		//选择ifGmail时出现的子面板设置
		ifSubPanel2 = new JPanel();
		ifSubPanel2.setLayout(null);
		
		JLabel id = new JLabel("邮箱：");
		id.setBounds(0,0,40,20);
		idText = new JTextArea();
		idText.setBounds(40,0,120,20);
		
		JLabel password = new JLabel("密码：");
		password.setBounds(0,30,40,20);
		passwordText = new JPasswordField();
		passwordText.setBounds(40,30,120,20);
		
		ifSubPanel2.setBounds(20,130,160,140); 
		ifSubPanel2.setOpaque(false);
		ifSubPanel2.add(id);
		ifSubPanel2.add(idText);
		ifSubPanel2.add(password);
		ifSubPanel2.add(passwordText);
		//选择ifGmail时出现的子面板设置结束
		
		ifPanel.setBounds(120,80,200,270);    
		ifPanel.setOpaque(false);
		//ifPanel.add(ifLabel);
		ifPanel.add(timeSelect);
		ifPanel.add(timeImageLabel);
		ifPanel.add(gmailSelect1);
		ifPanel.add(gmailImageLabel);
		ifPanel.add(ifSubPanel1);
		layeredPane.add(ifPanel,new Integer(1));
	}
	
	private void addThatPanel()//THAT面板，占据总空间(360,80,200,270)
	{
		thatPanel = new JPanel();
		thatPanel.setLayout(null);
		TitledBorder ifBorder = new TitledBorder("");
		thatPanel.setBorder(BorderFactory.createTitledBorder(ifBorder,"THAT:",TitledBorder.LEFT,TitledBorder.TOP,new Font("Dialog",Font.BOLD,30)));
		//JLabel thatLabel = new JLabel("THAT:");
		//thatLabel.setFont(new Font("Dialog",Font.BOLD,30));
		//thatLabel.setBounds(5,0,200,50);
		
		ImageIcon weiboImage = new ImageIcon("image/weibo.png");
		JLabel weiboImageLabel = new JLabel(weiboImage);
		weiboImageLabel.setBounds(20,40,60,60);
		ImageIcon gmailImage = new ImageIcon("image/Gmail.jpg");
		JLabel gmailImageLabel = new JLabel(gmailImage);
		gmailImageLabel.setBounds(100,40,60,60);
		
		weiboSelect= new JRadioButton("weibo");
		weiboSelect.setSelected(true);
		weiboSelect.setBounds(20,110,80,20);
		weiboSelect.setOpaque(false);
		gmailSelect2= new JRadioButton("gmail");
		gmailSelect2.setSelected(false);
		gmailSelect2.setBounds(100,110,80,20);
		gmailSelect2.setOpaque(false);
		ButtonGroup thatButtonGroup = new ButtonGroup();
		thatButtonGroup.add(weiboSelect);
		thatButtonGroup.add(gmailSelect2);
		
		//选择that weibo时出现的子面板设置
		thatSubPanel1 = new JPanel();
		thatSubPanel1.setLayout(null);
		
		JLabel id1 = new JLabel("ID：");
		id1.setBounds(0,0,40,20);
		idText1 = new JTextArea();
		idText1.setBounds(40,0,120,20);
		
		JLabel password1 = new JLabel("密码：");
		password1.setBounds(0,30,40,20);
		passwordText1 = new JPasswordField();
		passwordText1.setBounds(40,30,120,20);
		
		weiboContent = new JTextArea("请在此输入微博内容");
		JScrollPane scrollPane1 = new JScrollPane(weiboContent);
		scrollPane1.setBounds(0,60,160,70);
		
		thatSubPanel1.setBounds(20,130,160,140); 
		thatSubPanel1.setOpaque(false);
		thatSubPanel1.add(id1);
		thatSubPanel1.add(idText1);
		thatSubPanel1.add(password1);
		thatSubPanel1.add(passwordText1);
		thatSubPanel1.add(scrollPane1);
		//选择that weibo时出现的子面板设置结束
		
		//选择that Gmail时出现的子面板设置
		thatSubPanel2 = new JPanel();
		thatSubPanel2.setLayout(null);
		
		JLabel id2 = new JLabel("邮箱：");
		id2.setBounds(0,0,40,20);
		idText2 = new JTextArea();
		idText2.setBounds(40,0,120,20);
		
		JLabel password2 = new JLabel("密码：");
		password2.setBounds(0,25,40,20);
		passwordText2 = new JPasswordField();
		passwordText2.setBounds(40,25,120,20);
		
		JLabel id3 = new JLabel("目标邮箱：");
		id3.setBounds(0,50,70,20);
		idText3 = new JTextArea();
		idText3.setBounds(70,50,100,20);
		
		gmailContent = new JTextArea("请在此输入邮件内容");
		gmailContent.setEditable(true);
		//JScrollPane scrollPane2 = new JScrollPane(gmailContent);
		//scrollPane2.setBounds(0,75,160,55);
		gmailContent.setBounds(0,75,160,55);
		
		thatSubPanel2.setBounds(20,130,160,140); 
		thatSubPanel2.setOpaque(false);
		thatSubPanel2.add(id2);
		thatSubPanel2.add(idText2);
		thatSubPanel2.add(password2);
		thatSubPanel2.add(passwordText2);
		thatSubPanel2.add(id3);
		thatSubPanel2.add(idText3);
		//thatSubPanel2.add(scrollPane2);
		thatSubPanel2.add(gmailContent);
		//选择that Gmail时出现的子面板设置结束
		thatPanel.setBounds(360,80,200,270);    
		thatPanel.setOpaque(false);
		//thatPanel.add(thatLabel);
		thatPanel.add(weiboSelect);
		thatPanel.add(weiboImageLabel);
		thatPanel.add(gmailSelect2);
		thatPanel.add(gmailImageLabel);
		thatPanel.add(thatSubPanel1);
		layeredPane.add(thatPanel,new Integer(1));
	}
	
	private void addListener()
	{
	 sure.addActionListener(new ActionListener(){          //为确定按钮添加监听
	    	public void actionPerformed(ActionEvent e){
	    		task = new Task();
	    		int index = selectTask.getSelectedIndex();
	    		if(modifyIsOk(index) == false)            //检查修改的任务是否符合要求
	    			return;
	    		mainFrame.tasks.modifyTask(task,index);    //符合的话，添加新任务并刷新页面
	    		mainFrame.resetTaskSelection();
	    	}
	    });
	
		reset.addActionListener(new ActionListener(){    //为重置按钮添加监听
			public void actionPerformed(ActionEvent e){
				showTaskInfo();
			}	
		});
		cancel.addActionListener(new ActionListener(){    //为取消按钮添加监听
			public void actionPerformed(ActionEvent e){
				setVisible(false);
			}
		});
	
	    timeSelect.addItemListener(new ItemListener() {    //为IfPanel的time单选控件添加监听  
	    	public void itemStateChanged(ItemEvent e) {
	    		if(e.getSource() == timeSelect)
	    		{
	    			ifPanel.remove(ifSubPanel2);
					ifPanel.add(ifSubPanel1);
					ifPanel.repaint();
	    		}
	    	}
	    });
	    
	    gmailSelect1.addItemListener(new ItemListener(){     //为IfPanel的gmail单选控件添加监听 
	    	public void itemStateChanged(ItemEvent e) {
	    		if(e.getSource() == gmailSelect1)
	    		{
	    			ifPanel.remove(ifSubPanel1);
	    			ifPanel.add(ifSubPanel2);
	    			ifPanel.repaint();
	    		}
	    	}
	    });
		weiboSelect.addItemListener(new ItemListener(){     //为ThatPanel的weibo单选控件添加监听
			public void itemStateChanged(ItemEvent e) {
	    		if(e.getSource() == weiboSelect)
	    		{
	    			thatPanel.remove(thatSubPanel2);
	    			thatPanel.add(thatSubPanel1);
	    			thatPanel.repaint();
	    		}
			}
		});
		
		gmailSelect2.addItemListener(new ItemListener(){  //为ThatPanel的gmail单选控件添加监听
			public void itemStateChanged(ItemEvent e) {
				if(e.getSource() == gmailSelect2)
				{
					thatPanel.remove(thatSubPanel1);
					thatPanel.add(thatSubPanel2);
					thatPanel.repaint();
				}
			}
		});
	}	
	private void addChangeTaskListener()
	{
		class showTask implements ItemListener{

			public void itemStateChanged(ItemEvent e){
				if(e.getStateChange() == ItemEvent.SELECTED){
					showTaskInfo();
				}
			}	
		}
		selectTask.addItemListener(new showTask());
	}
	
	public boolean modifyIsOk(int index)            //检查修改的任务是否合格
	{   //检查任务名是否合格
		task.rename(newName.getText()); 
		if(task.getName().equals(""))
		{
			JOptionPane.showMessageDialog(null,"任务名不能为空", "Error", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		for(int i=0; i<mainFrame.tasks.getTaskNum(); i++)
		{
			if(task.getName().equals(mainFrame.tasks.getTaskName(i)) && i != index)
			{
				JOptionPane.showMessageDialog(null,"任务名不能重复", "Error", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		//检查IF条件是否合格
		if(timeSelect.isSelected() == true)
		{
			String date = dateInput.getText();
			String time = timeInput.getText();
			if(!time.matches("([0-1][0-9]|2[0-4]):(60|[0-5][0-9]):(60|[0-5][0-9])"))
			{
				JOptionPane.showMessageDialog(null,"时间错误!\n请确认格式是否正确：hh-mm-ss", "Error", 
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}	
			nowDate d = new nowDate(date.substring(0,4),date.substring(5,7),date.substring(8,10));
			nowTime t = new nowTime(time.substring(0,2),time.substring(3,5),time.substring(6,8));
			task.ifThis = new OrderTime(d,t);
		}
		else
		{
			String gmailId = idText.getText();
			String gmailPassword = String.valueOf(passwordText.getPassword());
			if(gmailId.equals("") || gmailPassword.equals(""))
			{
				JOptionPane.showMessageDialog(null,"检查收件的邮箱账号或密码不能为空", "Error", 
						JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			task.ifThis = new RecvMail(gmailId,gmailPassword);
		}
		//检查THAT任务是否合格
		if(weiboSelect.isSelected() == true)
		{
			String weiboId = idText1.getText();
			String weiboPassword = String.valueOf(passwordText1.getPassword());
			String weiboCt = weiboContent.getText();
			if(weiboId.equals("")||weiboPassword.equals("")|| weiboCt.equals(""))
			{
				JOptionPane.showMessageDialog(null,"账号、密码或微博内容不能为空", "Error", 
					JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			task.thenThat = new SendWeibo(weiboId,weiboPassword,weiboCt);
		}
		else
		{
			String gmailId2 = idText2.getText();
			String desGmailId = idText3.getText();
			String gmailPassword2 = String.valueOf(passwordText2.getPassword());
			String gmailCt = gmailContent.getText();
			if(gmailId2.equals("")||gmailPassword2.equals("")||desGmailId.equals("")||gmailCt.equals(""))
			{
				JOptionPane.showMessageDialog(null,"账号、密码、目标地址或邮件内容不能为空", "Error", 
					JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			task.thenThat = new SendMail(gmailId2,gmailPassword2,gmailCt,desGmailId);
		}
		return true;
	}
	
	private void showTaskInfo()                                            //显示当前所选的任务的信息
	{
		int i = selectTask.getSelectedIndex();
		if(i<0 || selectTask.getItemAt(i).equals(""))
			return;
		if(mainFrame.tasks.taskQueue[i].ifThis.getType() == 0)              //获取ifThis任务的类型
		{
			timeSelect.setSelected(true);
			nowDate date = new nowDate();
			nowTime time = new nowTime();
			((OrderTime)mainFrame.tasks.taskQueue[i].ifThis).getDateAndTime(date, time);
			dateInput.setText(date.year+"-"+date.month+"-"+date.day);
			timeInput.setText(time.hour+":"+time.minute+":"+time.seconde);
		}
		else
		{
			gmailSelect1.setSelected(true);
			Acount ifDesGmail = new Acount();
			((RecvMail)mainFrame.tasks.taskQueue[i].ifThis).getRecvMailAcount(ifDesGmail);
			idText.setText(ifDesGmail.id);
			passwordText.setText(ifDesGmail.password);
		}
		if(mainFrame.tasks.taskQueue[i].thenThat.getType() == 0)           //获取themThat任务的类型
		{
			weiboSelect.setSelected(true);
			Acount weibo = new Acount();
			String weiboCt = new String();
			weiboCt = ((SendWeibo)mainFrame.tasks.taskQueue[i].thenThat).getSendWeiboInfo(weibo);
			idText1.setText(weibo.id);
			passwordText1.setText(weibo.password);
			weiboContent.setText(weiboCt);
		}
		else
		{
			gmailSelect2.setSelected(true);
			Acount gmail = new Acount();
			String desGmail = new String("");
			String mailCt = new String();
			mailCt = ((SendMail)mainFrame.tasks.taskQueue[i].thenThat).getSendGmailInfoWithoutRa(gmail);
			desGmail = ((SendMail)mainFrame.tasks.taskQueue[i].thenThat).getRecvAddress();
			idText2.setText(gmail.id);
			idText3.setText(desGmail);
			passwordText2.setText(gmail.password);
			gmailContent.setText(mailCt);
		}
		repaint();
	}
	
	//在修改窗口的任务选择栏中同步任务信息
	public void resetTaskChangeList()
	{
		String taskName[] = new String[mainFrame.TaskNameSize];
		selectTask.removeAllItems();
		for(int i=0; i < mainFrame.tasks.getTaskNum(); i++)
		{
			taskName[i] = mainFrame.tasks.getTaskName(i);
			selectTask.addItem(taskName[i]);
		}
		showTaskInfo();
	}
	public void display()
	{
		setVisible(true);
	}
}

