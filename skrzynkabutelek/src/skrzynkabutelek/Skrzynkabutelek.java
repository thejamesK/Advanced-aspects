package skrzynkabutelek;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Skrzynkabutelek 
{

    public static void main(String[] args) 
    {
        Crate crate = new Crate();
        
        Lock lock = new ReentrantLock();
        Condition waiting = lock.newCondition();
        
        BottleMakingMachine machine1 = new BottleMakingMachine(crate, lock, waiting);
        CrateChangingMachine machine2 = new CrateChangingMachine(crate, lock, waiting);
        
        Thread production = new Thread(machine1, "Producent");
        Thread changer = new Thread(machine2, "Changer");
        
        production.start();
        changer.start();
    }
    
}

class BottleMakingMachine implements Runnable
{
    public BottleMakingMachine(Crate crate, Lock lock, Condition waiting)
    {
        this.crate = crate;
        this.lock = lock;
        this.waiting = waiting;
                
    }
    @Override
    public void run() 
    {
        lock.lock();
        try
        {
            System.out.println(Thread.currentThread().getName()+": I'm starting bottle production!");
            while(true)
            {
                while(crate.isFull())
                {
                    try 
                    {
                        System.out.println(Thread.currentThread().getName()+": We need to change a crate becaouse this one is full! :(");
                        waiting.await();
                        System.out.println(Thread.currentThread().getName()+": I'm back to bottle production! :)");
                    } 
                    catch (InterruptedException ex) 
                    {
                        ex.printStackTrace();
                    }
                }
                
                System.out.println(Thread.currentThread().getName()+": I producted bottle nubmer "+(++i));
                crate.addBottle(new Bottle());
                
                waiting.signalAll();
            }
        }
        finally
        {
            lock.unlock();
        }
        
    }
    private Condition waiting;
    private Lock lock;
    private Crate crate;
    private int i = 0;
}

class CrateChangingMachine implements Runnable
{
    public CrateChangingMachine(Crate crate, Lock lock, Condition waiting)
    {
        this.crate = crate;
        this.lock = lock;
        this.waiting = waiting;
    }
    @Override
    public void run() 
    {
        lock.lock();
        try
        {
            System.out.println(Thread.currentThread().getName()+": I getting ready to change the crate!");
            while(true)
            {
                System.out.println(Thread.currentThread().getName()+": Random information");
                while(!crate.isFull())
                {
                    try 
                    {
                        System.out.println(Thread.currentThread().getName()+": Crate was changed!");
                        waiting.await();
                        System.out.println(Thread.currentThread().getName()+": I'm back to changing!");
                    } 
                    catch (InterruptedException ex) 
                    {
                        ex.printStackTrace();
                    }
                }
                crate.getTheNumberOfBottles();
                crate.change();
                crate.getTheNumberOfBottles();
                
                waiting.signalAll();
                
            } 
        }
        finally
        {
            lock.unlock();
        }
    }
    private Condition waiting;
    private Lock lock;
    private Crate crate;
}


class Crate
{
    public synchronized boolean isFull()
    {
        if(bottleList.size() == CAPACITY)
            return true;
        else
            return false;
        
    }
    
    public synchronized int getTheNumberOfBottles()
    {
        System.out.println(Thread.currentThread().getName()+": Now in create is: "+bottleList.size());
        return this.bottleList.size();
    }
    
    public synchronized void addBottle(Bottle bottle)
    {
        bottleList.add(bottle);
    }
    
    public synchronized void change()
    {
        System.out.println(Thread.currentThread().getName()+": I changing crate!");
        bottleList.clear();
    }
    private final int CAPACITY = 10;
    private ArrayList bottleList = new ArrayList(CAPACITY);
}


class Bottle
{
    
}
