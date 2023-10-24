package IFTTT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.border.TitledBorder;
import java.util.Calendar;

public class CreateTask extends JFrame{
	protected MainFrame mainFrame;
	private JLayeredPane layeredPane;
	private JPanel titlePanel,namePanel,
	        ifPanel,ifSubPanel1,ifSubPanel2,
	        thatPanel,thatSubPanel1,thatSubPanel2;
	private JTextArea nameText,                       //Ϊ����ӡ����á���ť�Ĺ��ܣ��������й�����ı�������
	        timeInput,idText,
	        idText1,weiboContent,idText2,idText3,gmailContent;
	private calendar dateInput;
	private String nowTime;
	private JPasswordField passwordText,passwordText1,passwordText2; 
	private JButton sure,reset,cancel;
	private JRadioButton timeSelect,gmailSelect1,weiboSelect,gmailSelect2;
	private Task task;
	
	public CreateTask(MainFrame frame)		//���캯��
	{
		mainFrame = frame;
		layeredPane = getLayeredPane();
		addBackImage();
		addTitlePanel();
		addNamePanel();
		addIfPanel();
		addThatPanel();
		addListener();
		setTitle("�½�����");
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
	
	private void addTitlePanel()//������壬ռ���ܿռ�(40,50,60,300)
	{
		titlePanel = new JPanel();
		titlePanel.setLayout(null);
		
		JLabel title = new JLabel("<html>��<br>��<br>��<br>��"); 
		title.setFont(new Font("Dialog",Font.BOLD,20));
		//title.setAlignmentX(TOP_ALIGNMENT)();
		title.setBounds(20,0,50,120);
		
		sure = new JButton("ȷ��");
		//sure.setFont(new Font("Dialog",Font.BOLD,10));
		sure.setBounds(0,150,60,30);
		reset = new JButton("����");
		reset.setBounds(0,200,60,30);
		cancel = new JButton("ȡ��");
		cancel.setBounds(0,250,60,30);	
		
		titlePanel.setBounds(40,50,60,300);    
		titlePanel.setOpaque(false);
		titlePanel.add(title);
		titlePanel.add(sure);
		titlePanel.add(reset);
		titlePanel.add(cancel);
		layeredPane.add(titlePanel,new Integer(1));
	}
	
	private void addNamePanel()//����������壬ռ���ܿռ�(120,50,440,30)
	{
		namePanel = new JPanel();
		namePanel.setLayout(null);
		
		JLabel nameLabel = new JLabel("��������:");
		nameLabel.setBounds(0,0,60,20);
		
		nameText = new JTextArea("�ڴ������µ�������");
		nameText.setBounds(60,0,380,20);
		
		namePanel.setBounds(120,50,440,30);    
		namePanel.setOpaque(false);
		namePanel.add(nameLabel);
		namePanel.add(nameText);
		layeredPane.add(namePanel,new Integer(1));
	}
	
	private void addIfPanel()//THIS��壬ռ���ܿռ�(120,80,200,270)
	{
		ifPanel = new JPanel();
		ifPanel.setLayout(null);
		BorderFactory.createEtchedBorder();
		TitledBorder ifBorder = new TitledBorder("");
		ifPanel.setBorder(BorderFactory.createTitledBorder(ifBorder,"IF THIS:",TitledBorder.LEFT,TitledBorder.TOP,new Font("Dialog",Font.BOLD,30)));
		//JLabel ifLabel = new JLabel("IF THIS:");
		//ifLabel.setFont(new Font("Dialog",Font.BOLD,30));
		//ifLabel.setBounds(5,0,200,50);
		
		ImageIcon timeImage = new ImageIcon("image/time.png");//if���ѡ��
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
		
		//ѡ��ifʱ��ʱ���ֵ����������
		ifSubPanel1 = new JPanel();
		ifSubPanel1.setLayout(null);
		
		JLabel date = new JLabel("���ڣ�");
		date.setBounds(0,0,40,20);
		nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		//JTextArea dateInput = new JTextArea(tmp.substring(0, 10));
		//dateInput.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
		dateInput = new calendar(this);
		dateInput.setBounds(40,0,120,20);
		
		JLabel time = new JLabel("ʱ�䣺");
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
		//ѡ��ifʱ��ʱ���ֵ���������ý���
		
		//ѡ��ifGmailʱ���ֵ����������
		ifSubPanel2 = new JPanel();
		ifSubPanel2.setLayout(null);
		
		JLabel id = new JLabel("���䣺");
		id.setBounds(0,0,40,20);
		idText = new JTextArea();
		idText.setBounds(40,0,120,20);
		
		JLabel password = new JLabel("���룺");
		password.setBounds(0,30,40,20);
		passwordText = new JPasswordField();
		passwordText.setBounds(40,30,120,20);
		
		ifSubPanel2.setBounds(20,130,160,140); 
		ifSubPanel2.setOpaque(false);
		ifSubPanel2.add(id);
		ifSubPanel2.add(idText);
		ifSubPanel2.add(password);
		ifSubPanel2.add(passwordText);
		//ѡ��ifGmailʱ���ֵ���������ý���
		
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
	
	private void addThatPanel()//THAT��壬ռ���ܿռ�(360,80,200,270)
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
		
		//ѡ��that weiboʱ���ֵ����������
		thatSubPanel1 = new JPanel();
		thatSubPanel1.setLayout(null);
		
		JLabel id1 = new JLabel("ID��");
		id1.setBounds(0,0,40,20);
		idText1 = new JTextArea();
		idText1.setBounds(40,0,120,20);
		
		JLabel password1 = new JLabel("���룺");
		password1.setBounds(0,30,40,20);
		passwordText1 = new JPasswordField();
		passwordText1.setBounds(40,30,120,20);
		
		weiboContent = new JTextArea("���ڴ�����΢������");
		JScrollPane scrollPane1 = new JScrollPane(weiboContent);
		scrollPane1.setBounds(0,60,160,70);
		
		thatSubPanel1.setBounds(20,130,160,140); 
		thatSubPanel1.setOpaque(false);
		thatSubPanel1.add(id1);
		thatSubPanel1.add(idText1);
		thatSubPanel1.add(password1);
		thatSubPanel1.add(passwordText1);
		thatSubPanel1.add(scrollPane1);
		//ѡ��that weiboʱ���ֵ���������ý���
		
		//ѡ��that Gmailʱ���ֵ����������
		thatSubPanel2 = new JPanel();
		thatSubPanel2.setLayout(null);
		
		JLabel id2 = new JLabel("���䣺");
		id2.setBounds(0,0,40,20);
		idText2 = new JTextArea();
		idText2.setBounds(40,0,120,20);
		
		JLabel password2 = new JLabel("���룺");
		password2.setBounds(0,25,40,20);
		passwordText2 = new JPasswordField();
		passwordText2.setBounds(40,25,120,20);
		
		JLabel id3 = new JLabel("Ŀ�����䣺");
		id3.setBounds(0,50,70,20);
		idText3 = new JTextArea();
		idText3.setBounds(70,50,100,20);
		
		gmailContent = new JTextArea("���ڴ������ʼ�����");
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
		//ѡ��that Gmailʱ���ֵ���������ý���
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
	    sure.addActionListener(new ActionListener(){     //Ϊȷ����ť��Ӽ���
	    	public void actionPerformed(ActionEvent e){
	    		task = new Task();
	    		if(taskIsOk() == false)                  //����½��������Ƿ����Ҫ��
	    			return;
	    		mainFrame.tasks.addTask(task);           //���ϵĻ������������ˢ��ҳ��
	    		mainFrame.resetTaskSelection();
	    	}
	    });
	
		reset.addActionListener(new ActionListener(){    //Ϊ���ð�ť��Ӽ���
			public void actionPerformed(ActionEvent e){
				nameText.setText("�ڴ������µ�������");   //�����������������
				//IF���������
				timeSelect.setSelected(true); 
				nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				dateInput.setText(nowTime.substring(0,10));       //ʱ�ӵ�����
				timeInput.setText(nowTime.substring(11,19));
				idText.setText("");                      //�յ��ʼ�ʱ������
				passwordText.setText("");
				//THAT���������
				weiboSelect.setSelected(true);           
				idText1.setText("");                     //����΢��ʱ������
				passwordText1.setText("");
				weiboContent.setText("���ڴ�����΢������");
				idText2.setText("");                     //�����ʼ�ʱ������
				passwordText2.setText("");	
				idText3.setText("");	
				gmailContent.setText("���ڴ������ʼ�����");
			}	
		});
	
		cancel.addActionListener(new ActionListener(){     //Ϊȡ����ť��Ӽ���
			public void actionPerformed(ActionEvent e){
				setVisible(false);
			}
		});
		
	    timeSelect.addItemListener(new ItemListener() {    //ΪIfPanel��time��ѡ�ؼ���Ӽ���  
	    	public void itemStateChanged(ItemEvent e) {
	    		if(e.getSource() == timeSelect)
	    		{
	    			ifPanel.remove(ifSubPanel2);
					ifPanel.add(ifSubPanel1);
					ifPanel.repaint();
	    		}
	    	}
	    });
	    
	    gmailSelect1.addItemListener(new ItemListener(){     //ΪIfPanel��gmail��ѡ�ؼ���Ӽ��� 
	    	public void itemStateChanged(ItemEvent e) {
	    		if(e.getSource() == gmailSelect1)
	    		{
	    			ifPanel.remove(ifSubPanel1);
	    			ifPanel.add(ifSubPanel2);
	    			ifPanel.repaint();
	    		}
	    	}
	    });
		weiboSelect.addItemListener(new ItemListener(){     //ΪThatPanel��weibo��ѡ�ؼ���Ӽ���
			public void itemStateChanged(ItemEvent e) {
	    		if(e.getSource() == weiboSelect)
	    		{
	    			thatPanel.remove(thatSubPanel2);
	    			thatPanel.add(thatSubPanel1);
	    			thatPanel.repaint();
	    		}
			}
		});
		
		gmailSelect2.addItemListener(new ItemListener(){  //ΪThatPanel��gmail��ѡ�ؼ���Ӽ���
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

public boolean taskIsOk()
{   //����������Ƿ�ϸ�
	task.rename(nameText.getText()); 
	if(task.getName().equals(""))
	{
		JOptionPane.showMessageDialog(null,"����������Ϊ��", "Error", JOptionPane.INFORMATION_MESSAGE);
		return false;
	}
	for(int i=0; i<mainFrame.tasks.getTaskNum(); i++)
	{
		if(task.getName().equals(mainFrame.tasks.getTaskName(i)))
		{
			JOptionPane.showMessageDialog(null,"�����������ظ�", "Error", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	//���IF�����Ƿ�ϸ�
	if(timeSelect.isSelected() == true)
	{
		String date = dateInput.getText();
		String time = timeInput.getText();
		if(!time.matches("([0-1][0-9]|2[0-4]):(60|[0-5][0-9]):(60|[0-5][0-9])"))
		{
			JOptionPane.showMessageDialog(null,"ʱ�����!\n��ȷ�ϸ�ʽ�Ƿ���ȷ��hh:mm:ss", "Error", 
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
			JOptionPane.showMessageDialog(null,"�˺Ż����벻��Ϊ��", "Error", 
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		task.ifThis = new RecvMail(gmailId,gmailPassword);
	}
	//���THAT�����Ƿ�ϸ�
	if(weiboSelect.isSelected() == true)
	{
		String weiboId = idText1.getText();
		String weiboPassword = String.valueOf(passwordText1.getPassword());
		String weiboCt = weiboContent.getText();
		if(weiboId.equals("")||weiboPassword.equals("")|| weiboCt.equals(""))
		{
			JOptionPane.showMessageDialog(null,"�˺š������΢�����ݲ���Ϊ��", "Error", 
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
			JOptionPane.showMessageDialog(null,"�˺š����롢Ŀ���ַ���ʼ����ݲ���Ϊ��", "Error", 
				JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		task.thenThat = new SendMail(gmailId2,gmailPassword2,gmailCt,desGmailId);
	}
	return true;
}

public void display()
	{
		setVisible(true);
	}
}
