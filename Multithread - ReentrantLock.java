package multithread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Multithread 
{

    public static void main(String[] args) 
    {
        //Runnable wypisanie = new WypisanieRunnable();
        
        Lock lock = new ReentrantLock();
        
        
        Thread watek = new Thread(new WypisanieRunnable(lock), "watek 1");
        Thread watek2 = new Thread(new WypisanieRunnable(lock), "watek 2");
        
        watek.start();
        watek2.start();
        
        /*Counter licznik = new Counter();
                
        Thread watek3 = new Thread(new CounterRunnable(licznik, false), "Maleje");
        Thread watek4 = new Thread(new CounterRunnable(licznik, true), "Rosnie");
        
        watek3.start();*/
        
        /*try 
        {
            watek.join();
        } 
        catch (InterruptedException ex) 
        {
            System.out.println(ex.getMessage());
        }*/
        System.out.println(Thread.currentThread().getName());
        System.out.println("cos tu sie dzieje OD RAZU po skonczonym watku watek");
        
        //watek4.start();
    }
    
}

class WypisanieRunnable implements Runnable
{
    public WypisanieRunnable(Lock lock)
    {
        this.lock = lock;
    }
    static String msg[] = {"To", "jest", "synchronicznie", "wypisana", "wiadomosc"};
    @Override
    public void run() 
    {
        display(Thread.currentThread().getName());
    }
    
    public void display(String threadName)
    {
        lock.lock();
        try
        {
            for(int i = 0; i < msg.length; i++)
            {
                System.out.println(threadName+": "+msg[i]);
            
                try
                {
                    Thread.sleep(100);
                }
                catch(InterruptedException ex)
                {   
                    System.out.println(ex.getMessage());
                }
            }
            
        }
        finally
        {
            lock.unlock();
        }
        
    }
    
    
    private Lock lock;
    
}

/*class Counter
{
    public void increaseCounter()
    {
        counter++;
        System.out.println(Thread.currentThread().getName()+": "+counter);
    }
    
    public void decraseCounter()
    {
        counter--;
        System.out.println(Thread.currentThread().getName()+": "+counter);
    }
    
    private int counter = 50;
}

class CounterRunnable implements Runnable
{
    public CounterRunnable(Counter licznik, boolean increase)
    {
        this.licznik = licznik;
        this.increase = increase;
    }
    
    @Override
    public void run() 
    {
        synchronized(licznik)
        {
            for(int i = 0; i < 50; i++)
            {
                if(increase)
                    licznik.increaseCounter();
                else
                    licznik.decraseCounter();
                try
                {
                    Thread.sleep(10);
                }
                catch(InterruptedException ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    
    boolean increase;
    Counter licznik;
    
}*/