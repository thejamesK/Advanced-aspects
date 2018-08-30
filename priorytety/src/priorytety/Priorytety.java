package priorytety;

public class Priorytety 
{

    public static void main(String[] args) 
    {
        System.out.println(Thread.currentThread().getPriority());
        
        Thread thread1 = new Thread(new Thread1(), "Small");
        Thread thread2 = new Thread(new Thread2(), "Big");
        
        
        thread1.setPriority(Thread.MIN_PRIORITY);
        thread2.setPriority(Thread.MAX_PRIORITY);
        thread1.start();
        thread2.start();
    }
    
}


class Thread1 implements Runnable
{

    @Override
    public void run() 
    {
        while(true)
        System.out.println(Thread.currentThread().getName());
    }
    
}

class Thread2 implements Runnable
{

    @Override
    public void run() 
    {
        while(true)
        System.out.println(Thread.currentThread().getName());
    }
    
}
