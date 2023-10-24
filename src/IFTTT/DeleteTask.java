package IFTTT;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;

public class DeleteTask extends JFrame{
	protected MainFrame mainFrame;
	private JLayeredPane layeredPane;
	private JTextArea taskInfo;
	private JButton delete,quit;
	private JList taskList;	
	private DefaultListModel listModel;

	public DeleteTask(MainFrame frame)		//���캯��
	{
		mainFrame = frame;
		layeredPane = getLayeredPane();
		addBackImage();
		addTitlePanel();
		addListPanel();
		addInfoPanel();
		addListener();
		setTitle("ɾ������");
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
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(null);
		
		JLabel title = new JLabel("<html>�鿴<br>��<br>ɾ��<br>����"); 
		title.setFont(new Font("Dialog",Font.BOLD,20));
		//title.setAlignmentX(TOP_ALIGNMENT)();
		title.setBounds(10,0,50,120);
		
		delete = new JButton("ɾ��");
		//sure.setFont(new Font("Dialog",Font.BOLD,10));
		delete.setBounds(0,150,60,30);
		quit = new JButton("�˳�");
		quit.setBounds(0,200,60,30);	
		
		titlePanel.setBounds(40,50,60,300);    
		titlePanel.setOpaque(false);
		titlePanel.add(title);
		titlePanel.add(delete);
		titlePanel.add(quit);
		layeredPane.add(titlePanel,new Integer(1));
	}
	
	private void addListPanel()//    �����б���壬ռ���ܿռ�(120,50,200,300)
	{
		JPanel listPanel = new JPanel();
		listPanel.setLayout(null);
		BorderFactory.createEtchedBorder();
		TitledBorder ifBorder = new TitledBorder("");
		listPanel.setBorder(BorderFactory.createTitledBorder(ifBorder,"�����б�:",TitledBorder.LEFT,TitledBorder.TOP,new Font("Dialog",Font.BOLD,18)));
		//JLabel ifLabel = new JLabel("IF THIS:");
		//ifLabel.setFont(new Font("Dialog",Font.BOLD,30));
		//ifLabel.setBounds(5,0,200,50);
		
		listModel = new DefaultListModel();
		taskList = new JList(listModel);
		taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane taskListScroll = new JScrollPane(taskList);
		taskListScroll.setBounds(20,60,160,220);
		
		listPanel.setBounds(120,50,200,300);    
		listPanel.setOpaque(false);
		listPanel.add(taskListScroll);
		layeredPane.add(listPanel,new Integer(1));
	}
	
	private void addInfoPanel()//��Ϣ��壬ռ���ܿռ�(360,50,200,300)
	{
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(null);
		TitledBorder ifBorder = new TitledBorder("");
		infoPanel.setBorder(BorderFactory.createTitledBorder(ifBorder,"ѡ���������ϸ��Ϣ:",TitledBorder.LEFT,TitledBorder.TOP,new Font("Dialog",Font.BOLD,18)));
		//JLabel thatLabel = new JLabel("THAT:");
		//thatLabel.setFont(new Font("Dialog",Font.BOLD,30));
		//thatLabel.setBounds(5,0,200,50);
		
		taskInfo = new JTextArea();
		taskInfo.setBounds(20,60,160,220);
		
		infoPanel.setBounds(360,50,200,300);    
		infoPanel.setOpaque(false);
		infoPanel.add(taskInfo);
		layeredPane.add(infoPanel,new Integer(1));
	}
	private void addListener()
	{
		class taskListListener implements ListSelectionListener
		{
			public void valueChanged(ListSelectionEvent a)
			{
					int index = taskList.getSelectedIndex();
					if(index <0 )
					{
						taskInfo.setText("");
						return;
					}
					String str = mainFrame.taskDescribtion(index);
					taskInfo.setText(str);
			
			}
		}
		taskList.addListSelectionListener(new taskListListener());
		
		delete.addActionListener(new ActionListener(){   //Ϊɾ����ť��Ӽ���
			public void actionPerformed(ActionEvent e){
				int index = taskList.getSelectedIndex();
				mainFrame.tasks.deleteTask(index);
				mainFrame.resetTaskSelection();
				taskList.setSelectedIndex(-1);
			}	
		});
		quit.addActionListener(new ActionListener(){    //Ϊ�˳���ť��Ӽ���
			public void actionPerformed(ActionEvent e){
				setVisible(false);
			}
		});
	}
	
	//�ڲ鿴��ɾ�����ڵ������б���ͬ��������Ϣ
	public void resetTaskList()
	{
		String taskName[] = new String[mainFrame.TaskNameSize];
		listModel.removeAllElements();
		for(int i=0; i < mainFrame.tasks.getTaskNum(); i++)
		{
			taskName[i] = mainFrame.tasks.getTaskName(i);
			listModel.addElement(taskName[i]);
		}
	}
	
	public void display()
	{
		setVisible(true);
	}
}
