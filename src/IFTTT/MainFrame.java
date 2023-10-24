package IFTTT;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class MainFrame extends JFrame{
	final int MaxTaskSize = 100;
	final int TaskNameSize = 100;
	private JLayeredPane layeredPane;
	private CreateTask createTaskFrame;		    //新建任务窗口
	private ChangeTask changeTaskFrame;	        //修改任务窗口
	private DeleteTask deleteTaskFrame;	        //任务查看/删除窗口
    protected TaskQueue tasks;	                //任务队列
    private JComboBox select;
    private JTextArea describe,output;
    private Runnable runnable;                  //处理任务的线程
    private int doing;                          //有任务在运行时表示正在运行的任务下标，无任务或暂停时为-2
    private IfSubTask ifTask;
	
	public MainFrame()
	{
		//Container container = getContentPane(); 
		layeredPane = getLayeredPane();		
		tasks = new TaskQueue(MaxTaskSize);     //初始化任务队列
		//创建各项子窗口
		createTaskFrame = new CreateTask(this);
		changeTaskFrame = new ChangeTask(this);
		deleteTaskFrame = new DeleteTask(this);
		//布置主窗口,插入各个框架
		addBackImage();      //设定背景图片
		addMenuBar();        //插入菜单		
		addEastPanel();      //插入右侧说明界面
		addWestPanel();      //插入左侧说明界面
		doing = -2;
		//container.add(layeredPane, BorderLayout.CENTER); 
	}
	//添加主窗口界面
	private void addBackImage()
	{
		JPanel backImagePanel = new JPanel();
		backImagePanel.setLayout(null);
		
		ImageIcon backImage = new ImageIcon("image/mainFrame_background.jpg");
		JLabel backImageLabel = new JLabel(backImage); 
		backImageLabel.setBounds(0,0,backImage.getIconWidth(),backImage.getIconHeight());
		
		backImagePanel.setBounds(0,20,600,400); 
		backImagePanel.add(backImageLabel);
		backImagePanel.setVisible(true);
		layeredPane.add(backImagePanel,new Integer(0));
	}
	
	private void addMenuBar()
	{
		JMenuBar bar = new JMenuBar();
		JMenuItem items[] = new JMenuItem[11];
		
		JMenu taskItem = new JMenu("任务");
		JMenu ctrItem = new JMenu("控制");
		JMenu helpItem = new JMenu("帮助");
		
		taskItem.add(items[0] = new JMenuItem("新建任务"));
		taskItem.add(items[1] = new JMenuItem("修改任务"));
		taskItem.add(items[2] = new JMenuItem("删除任务"));
		taskItem.add(items[3] = new JMenuItem("查看所有任务"));
		
		ctrItem.add(items[5] = new JMenuItem("开始任务"));
		ctrItem.add(items[6] = new JMenuItem("暂停任务"));
		ctrItem.add(items[7] = new JMenuItem("结束任务"));
		
		helpItem.add(items[10] = new JMenuItem("信息"));
		
		bar.add(taskItem);
		bar.add(ctrItem);
		bar.add(helpItem);
		setJMenuBar(bar);
		items[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				createTaskFrame.display();
			}
		});
		items[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				changeTaskFrame.display();
			}					
		});
		items[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				deleteTaskFrame.display();	
			}
		});
		items[3].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				deleteTaskFrame.display();
			}
		});
		items[5].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int index = select.getSelectedIndex();
				if(index<0 || select.getItemAt(index).toString().equals(""))
					return;
				if(runnable != null)
				{
					if(doing != -2)   //已经有另一个任务在运行中
					{
						JOptionPane.showMessageDialog(null,"请先停止当前正在运行的任务", "Error", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					else                //继续之前暂停的任务
					{
						ifTask.setSuspend(false);
						doing = index;
						output.setText(output.getText() + "\n任务继续");
						return;
					}
				}
				ifTask = (IfSubTask)(tasks.taskQueue[index].ifThis);
				ifTask.initIfTask();
				doing = index;
				output.setText("任务开始");
				runnable = new Runnable() {            //新建一个线程不断地检查任务的if条件是否达成
					public void run() { 
						int index = select.getSelectedIndex();
						ThenSubTask thatTask = (ThenSubTask)(tasks.taskQueue[index].thenThat);
						String str = taskDescribtion(index);
						describe.setText(str);
						repaint();
						while(true)
						{
							if(ifTask.ifThisSatisfied() == true)          //一旦得到ifThis子任务返回的if条件达成信息，立刻执行that
							{
								output.setText(output.getText() + "\n"+ tasks.taskQueue[index].getThisOutput());
								thatTask.thenDoThat();
								output.setText(output.getText() + "\n"+ tasks.taskQueue[index].getThatOutput());
								break;
							}
						}
						output.setText(output.getText() + "\n任务完成");
					}
				};
				new Thread(runnable).start();  
			}
		});
		items[6].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int index = select.getSelectedIndex();
				if(index<0 || select.getItemAt(index).toString().equals(""))
					return;
				if(doing != index )
				{
					JOptionPane.showMessageDialog(null,"当前所选任务并不在运行", "Error", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				//ifTask = (IfSubTask)(tasks.taskQueue[index].ifThis);
				ifTask.setSuspend(true);
				//output.setText(output.getText()+ (runnable == null));
				output.setText(output.getText()+"\n任务已暂停");
				doing = -2;
			}
		});
		items[7].addActionListener(new ActionListener(){   //结束当前正在进行的任务，而不论主窗口是否选择了当前正在进行的任务。
			public void actionPerformed(ActionEvent e){
				if(doing == -2  &&  runnable == null)
				{
					JOptionPane.showMessageDialog(null,"当前没有任务在运行", "Error", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				int index = select.getSelectedIndex();
				if(index<0 || select.getItemAt(index).toString().equals(""))
					return;
				runnable = null;
				doing = -2;
				output.setText(output.getText()+"\n任务已结束");
			}
		});
		/*		mainFrame.setJMenuBar(bar);	*/
	}
	
	private void addEastPanel()
	{
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(null);                  //要设置绝对位置一定要添加这句，因为默认的布局管理器是BorderLayout。
		
		JLabel iftttLabel = new JLabel("单机版IFTTT");
		Font fontTitle = new Font("Dialog",Font.BOLD,30);      
        iftttLabel.setFont(fontTitle);
		iftttLabel.setBounds(0,0,200,50);
		//iftttLabel.setPreferredSize(new Dimension(200,50));
		
		JTextArea introduce = new JTextArea();
		introduce.setText("使用说明：\n" +
				          "1、首先用户新建任务\n" +
				          "2、然后再选择控制->运行任务来开始任务\n" +
			        	  "3、可在下面文本域内查看当前任务的完整描述\n");
		introduce.setEditable(false);
		introduce.setLineWrap(true);
		introduce.setWrapStyleWord(true);
		introduce.setBounds(0,50,200,130);
		//introduce.setPreferredSize(new Dimension(200,150));
		introduce.setOpaque(false);
		
		eastPanel.setBounds(360,70,200,280);           //相对背景图片调整
		eastPanel.setOpaque(false);
		eastPanel.add(iftttLabel);
		eastPanel.add(introduce);
		layeredPane.add(eastPanel, new Integer(1));
	}
	
	private void addWestPanel()
	{
		JPanel westPanel = new JPanel();
		westPanel.setLayout(null);
		
		JLabel selectLabel = new JLabel("选择需要运行的任务：");
		selectLabel.setBounds(0,0, 150, 20);
		//selectLabel.setPreferredSize(new Dimension(150,20));
		
		select = new JComboBox();
		select.setBounds(150,0,150,20);
		//select.setPreferredSize(new Dimension(150,20));
		
		JLabel describeLabel = new JLabel("正在运行的任务描述：");
		describeLabel.setBounds(0,20,300,20);
		//describeLabel.setPreferredSize(new Dimension(300,20));
		
		describe = new JTextArea();
		describe.setEditable(false);
		describe.setLineWrap(true);
		describe.setWrapStyleWord(true);
		JScrollPane desScrollPane = new JScrollPane(describe);
		desScrollPane.setBounds(0,40,300,80);
		//desScrollPane.setPreferredSize(new Dimension(300,80));
		
		JLabel outputLabel = new JLabel("输出信息:");
		outputLabel.setBounds(0,120,300,20);
		//outputLabel.setPreferredSize(new Dimension(300,20));
		
		output = new JTextArea();
		output.setEditable(false);
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		JScrollPane outScrollPane = new JScrollPane(output);
		outScrollPane.setBounds(0,140,300,80);
		//outScrollPane.setPreferredSize(new Dimension(300,80));
		
		westPanel.setBounds(40,70,300,280);
		westPanel.setOpaque(false);
		westPanel.add(selectLabel);
		westPanel.add(select);
		westPanel.add(describeLabel);
		westPanel.add(desScrollPane);
		westPanel.add(outputLabel);
		westPanel.add(outScrollPane);
		layeredPane.add(westPanel, new Integer(2));
	}
	
	//在主窗口的任务选择栏中同步任务信息
	protected void resetTaskSelection()		
	{
		String taskName[] = new String[TaskNameSize];
		select.removeAllItems();
		for(int i=0; i < tasks.top; i++)
		{
			taskName[i] = tasks.taskQueue[i].getName();
			select.addItem(taskName[i]);
		}
		changeTaskFrame.resetTaskChangeList();
		deleteTaskFrame.resetTaskList();
	}
	
	public String taskDescribtion(int i)		//获取对任务的描述字符
	{
		String td = null, ifTd = null, thatTd = null;
		nowDate date;
		nowTime time;
		Acount ifDesGmail,weibo, gmail;
		String desGmail,weiboCt, gmailCt;
		switch(tasks.taskQueue[i].ifThis.getType())
		{
			case 0:	date = new nowDate();
					time = new nowTime();
					((OrderTime)tasks.taskQueue[i].ifThis).getDateAndTime(date, time);
					ifTd = "任务条件：\n  " +
						   "当时间到达"+date.year+"年"+date.month+"月"+date.day+"日，"+time.hour+":"+time.minute+":"+time.seconde+"  之时\n";
					//date = null;
					//time = null;
					break;
			case 1: ifDesGmail = new Acount();
					((RecvMail)tasks.taskQueue[i].ifThis).getRecvMailAcount(ifDesGmail);
					ifTd = "任务条件： \n  " +
						   "当邮箱:"+ifDesGmail.id+"收到邮件之时\n";

					break;	
		}
		switch(tasks.taskQueue[i].thenThat.getType())
		{
			case 0:	weibo = new Acount();
					weiboCt = ((SendWeibo)tasks.taskQueue[i].thenThat).getSendWeiboInfo(weibo);
					thatTd  = "任务目标：  \n " +
							  "微博账号："+weibo.id+"发送一条微博：\n"+
							  weiboCt;
					break;
			case 1:	gmail = new Acount();
					gmailCt = ((SendMail)tasks.taskQueue[i].thenThat).getSendGmailInfoWithoutRa(gmail);
					desGmail = ((SendMail)tasks.taskQueue[i].thenThat).getRecvAddress();
					thatTd = "任务目标：  \n"+
							 "使用邮箱："+gmail.id+" \n"+
							 "发送一份邮件到"+desGmail+" :\n"+
							 gmailCt;
					break;
		}
		td = ifTd+thatTd ;
		return td;
	}
} 
