package IFTTT;

public class TaskThread extends Thread{
	private boolean suspend = false;  
	private String control = "";            
	  
    public void setSuspend(boolean suspend) 
    {  
        if (!suspend)
        {  
            synchronized (control) 
            {  
                control.notifyAll();  
            }  
        }  
        this.suspend = suspend;  
    }  
  
    public boolean getSuspend()
    {  
        return this.suspend;  
    }  
  
    public void run() 
    {  
        while (true) 
        {  
            synchronized (control) 
            {  
                if (suspend) 
                {  
                    try 
                    {  
                        control.wait();  
                    }catch(InterruptedException e){e.printStackTrace();}  
                }  
            }  
            this.runTask();  
        }  
    }  
  
    protected void runTask()  
    {
    	
    }
}
