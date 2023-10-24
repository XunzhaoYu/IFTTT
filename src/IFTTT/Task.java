package IFTTT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

class SubTask
{
	private int type;
	protected String subtaskOutput;
	public SubTask()
	{
		type = 0;
		subtaskOutput = "...";
	}
	public void changeType(int t)	  //改变任务类型
	{
		type = t;
	}
	public int getType()		      //获取任务类型
	{
		return type;
	}
	protected String getSubOutput()
	{
		return subtaskOutput;
	}
	protected void updateSubOutput(String update)
	{
			subtaskOutput = update;
	}
}

public class Task {
	private String name;    	      //任务名
	public SubTask ifThis,thenThat;

	public Task()
	{
		name = "";
		ifThis = new SubTask();
		thenThat = new SubTask();
	}
	public void rename(String n)	     //任务重命名
	{
		name = n;
	}
	public String getName()		         //获取任务名
	{
		return name;
	}
	public String getThisOutput()
	{
		return ifThis.getSubOutput();
	}
	public String getThatOutput()
	{
		return thenThat.getSubOutput();
	}
}

class TaskQueue         		              //任务队列类
{
    Task taskQueue[];         	              //任务数组
	int top;	                              //数组头
	
	public TaskQueue(int len)
	{
		taskQueue = new Task[len];
		top = 0;
	}
	public int getTaskNum()		            //获取任务总数
	{		
		return top;
	}
	public String getTaskName(int index)	//获取任务名称
	{
		return taskQueue[index].getName();
	}
	public void addTask(Task t)
	{
		taskQueue[top] = t;
		top ++;
	}
	public void deleteTask(int index)
	{
		if(index >= top || index < 0)
			return;
		taskQueue[index] = null;
		for(int i=index; i<top-1; i++)
		{
			taskQueue[i] = taskQueue[i+1];
		}
		top--;
	}
	public void modifyTask(Task t,int index)
	{
		taskQueue[index] = null;
		taskQueue[index] = t;
	}
}

abstract class IfSubTask extends SubTask	 	//ifThis任务类接口
{
	protected boolean satisfied;                  //if条件是否已经满足
	protected Timer timer = null;
	protected TimerTask timerTask = null;
	protected boolean suspend = false;           //ifThis任务是否暂停
	

	public boolean getSuspend()                  //获取当前任务状态（运行/暂停）
	{
		return suspend;
	}
	public void setSuspend(boolean s)            //使任务继续/暂停
	{
		if(suspend == s)
			return ;
		suspend = s;
	}
	public abstract void initIfTask();
	public abstract boolean ifThisSatisfied();     //ifThis任务的条件已满足
}

abstract class ThenSubTask extends SubTask	    	//thenThat任务类接口
{
	public abstract boolean thenDoThat();
}

class nowDate	//日期结构
{
	String year, month, day;
	
	public nowDate()
	{
		year = "2013";
		month = "11";
		day = "18";
	}
	public nowDate(String y, String m ,String d)
	{
		year = y;
		month = m;
		day = d;
	}	
	public nowDate(nowDate d)
	{
		year = d.year;
		month = d.month;
		day = d.day;
	}
}

class nowTime		//时间结构
{
	String hour, minute, seconde;
	
	public nowTime()
	{
		hour = "00";
		minute = "00";
		seconde = "00";
	}
	public nowTime(String h, String m, String s)
	{
		hour = h;
		minute = m;
		seconde = s;
	}
	public nowTime(nowTime t)
	{
		hour = t.hour;
		minute = t.minute;
		seconde = t.seconde;	
	}
}

class Acount	//账号结构
{
	String id, password;
	
	public Acount()
	{
		id = "";
		password = "";
	}
	
	public Acount(String i, String p)
	{
		id = i;
		password = p;		
	}
	public Acount(Acount a)
	{
		id = a.id;
		password = a.password;
	}
}


class OrderTime extends IfSubTask		//定时任务类
{
	private nowDate date;
	private nowTime time;
	private Date startTime;            //预订的时间
	
	public OrderTime()
	{
		super.changeType(0);
		date = new nowDate();
		time = new nowTime();
		satisfied = false;
	}
	public OrderTime(nowDate d, nowTime t)
	{
		super.changeType(0);
		date = new nowDate(d);
		time = new nowTime(t);
		satisfied = false;
	}
	public void getDateAndTime(nowDate d, nowTime t)
	{
		d.year = date.year;
		d.month = date.month;
		d.day = date.day;
		t.hour = time.hour;
		t.minute = time.minute;
		t.seconde = time.seconde;
	}
	protected void startTimer()
	{  
        if(timer == null)  
            timer = new Timer();  
        if (timerTask == null) 
        {  
            timerTask = new TimerTask() {   
            	public void run()
    			{
            		satisfied = true;
    			}      
            };  
        }
        if(timer != null && timerTask != null )  
       	try
		{
        	timer.schedule(timerTask,startTime);
		}catch(IllegalStateException e){}
       
    }  
	public void initIfTask()
	{
		//subtaskOutput = "";
		timer = new Timer(true);         //true指明timer的优先级低,程序结束timer也自动结束
		timerTask = new TimerTask()      //run中是schedule函数在到达预订时间时执行的函数
		{
			public void run()
			{
				satisfied = true;         
			}
		};
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			startTime = dt.parse(date.year+"-"+date.month+"-"+date.day+" "
				+time.hour+":"+time.minute+":"+time.seconde);
			timer.schedule(timerTask ,startTime);                  //新建Date实例获得任务定时的时间，一旦时间到了就触发run()
		}catch(ParseException e){}
	}
	public boolean ifThisSatisfied()
	{
		try
		{		
				Thread.sleep(1000);                                //由于调用一次schedule函数就创建了一个线程，所以可以用sleep调整该线程的查询频率。
				if(!suspend)
					if(satisfied == true)                               
					{
						updateSubOutput("时间已到，开始执行That...");
						return true;
					}
		}catch(Exception e){}
		return false;
	}
}

class RecvMail extends IfSubTask		//收取邮件类
{
	private Acount mailAcount;
	private long period = 1000;         //设置每隔period ms（1000ms）检查一次邮箱
	
	public RecvMail()
	{
		mailAcount = new Acount();
		super.changeType(1);
		satisfied = false;
	}
	public RecvMail(String i,String p)
	{
		mailAcount = new Acount(i,p);
		super.changeType(1);
		satisfied = false;
	}
	public RecvMail(Acount a)
	{
		mailAcount = new Acount(a);
		super.changeType(1);
		satisfied = false;
	}
	public void getRecvMailAcount(Acount a)
	{
		a.id = mailAcount.id;
		a.password = mailAcount.password;
	}
	protected void startTimer()
	{  
        if(timer == null)  
            timer = new Timer();  
        if (timerTask == null) 
        {  
            timerTask = new TimerTask() {   
            	public void run()
    			{
            		satisfied = true;
            		//updateSubOutput("时间已到，开始执行That...");
    			}      
            };  
        }  
        if(timer != null && timerTask != null )  
        	
        	
            timer.schedule(timerTask,new Date(),period); 
        //updateSubOutput("继续准备接受邮件");
    }  
	public void initIfTask()
	{
		timer = new Timer(true);         //true指明timer的优先级低,程序结束timer也自动结束
		timerTask = new TimerTask()      //run中不断检查是否有新邮件到达，有的话立刻将satisfied置true
		{
			public void run()
			{
				try 
				{
					if(GmailTask.recvGmail(mailAcount.id, mailAcount.password) == true)
					{
						updateSubOutput("发现新邮件...");
						satisfied = true;          
					}
				}catch(Exception e){e.printStackTrace();}
			}
		};
		timer.schedule(timerTask ,new Date(),period);               
		updateSubOutput("准备接受邮件" + satisfied);
	}
	public boolean ifThisSatisfied()	//如果收到邮件则返回true，否则返回false
	{
		try
		{
			Thread.sleep(1000); 
			if(!suspend)
				if(satisfied == true)     
				{
					updateSubOutput("收到新的邮件，执行that");
					return true;
				}
		}catch(Exception e){}		
		return false;
	}
}

class SendWeibo extends ThenSubTask	//发送微博类
{
	Acount weiboAcount;
	String weiboContent;
	
	public SendWeibo()
	{
		weiboAcount = new Acount();
		weiboContent = "";
		super.changeType(0);
	}
	public SendWeibo(String i,String p,String c)
	{
		weiboAcount = new Acount(i,p);
		weiboContent = c;
		super.changeType(0);
	}
	public SendWeibo(Acount a, String c)
	{
		weiboAcount = new Acount(a);
		weiboContent = c;
		super.changeType(0);
	}
	public String getSendWeiboInfo(Acount a)
	{
		a.id = weiboAcount.id;
		a.password = weiboAcount.password;
		return weiboContent;
	}
	public boolean thenDoThat()	//如果发送成功则返回true,否则返回false
	{
		try
		{
			updateSubOutput("发送微博...");
			WeiboTask.sendWeibo(weiboAcount.id,weiboAcount.password,weiboContent);
			updateSubOutput("发送完毕...");
		}catch(Exception e)
		{
			updateSubOutput("发送微博失败！");
			return false;
		}
		
		return true;
	}
}

class SendMail extends ThenSubTask		//发送邮件类
{
	private Acount sendAcount;
	private String sendContent;
	private String recvAddress;
	
	public SendMail()
	{
		sendAcount = new Acount();
		sendContent = "";
		recvAddress = "";
		super.changeType(1);
	}
	public SendMail(String si,String sp,String sc,String ra)
	{
		sendAcount = new Acount(si,sp);	
		sendContent = sc;
		recvAddress = ra;
		super.changeType(1);
	}
	public SendMail(Acount sa,String sc,String ra)
	{
		sendAcount = new Acount(sa);	
		sendContent = sc;
		recvAddress = ra;
		super.changeType(1);
	}
	public String getSendGmailInfoWithoutRa(Acount sa)
	{
		sa.id = sendAcount.id;
		sa.password = sendAcount.password;
		return sendContent;
	}
	public String getRecvAddress()//String类型无法直接通过recvAdress = ra传递参数。
	{
		return recvAddress;
	}
	public boolean thenDoThat() 		//如果发送成功则返回true,否则返回false
	{
		try
		{
			updateSubOutput("准备开始发送邮件" + sendAcount.id);
			if(GmailTask.sendGmail(sendAcount.id,sendAcount.password,sendContent, recvAddress) == true)
				updateSubOutput("邮件发送成功");
			else
			{
				updateSubOutput("邮件发送失败！");
				return false;
			}
		}catch(AddressException e )
		{}catch(MessagingException a){}
		return true;
	}
}

