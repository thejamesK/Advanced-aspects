package animacjaobrazka;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class AnimacjaObrazka extends JFrame
{
    public AnimacjaObrazka()
    {
        this.setTitle("Animation");
        this.setBounds(250, 300, 300, 250);
        animationPanel.setBackground(Color.GRAY);
        
        JButton startButton = (JButton)buttonPanel.add(new JButton("Start"));
        
        startButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent ae) 
            {
                startAnimation();
            }
        });
        
        JButton stopButton = (JButton)buttonPanel.add(new JButton("Stop"));
        
        stopButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent ae) 
            {
                stopAnimation();
            }
        });
        
        JButton addButton = (JButton)buttonPanel.add(new JButton("Add"));
        
        addButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent ae) 
            {
                addAnimation();
            }
        });
        
        
        this.getContentPane().add(animationPanel);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void startAnimation()
    {
        animationPanel.startAnimation();
    }
    
    public void stopAnimation()
    {
        animationPanel.stop();
    }
    
    public void addAnimation()
    {
        animationPanel.addOwl();
    }
    
    private JPanel buttonPanel = new JPanel();
    private AnimationPanel animationPanel = new AnimationPanel();
    public static void main(String[] args) 
    {
        new AnimacjaObrazka().setVisible(true);               
    }
    
    class AnimationPanel extends JPanel
    {
        private volatile boolean stopped = false; //delikatna zmienna 
        private Object lock = new Object();
        public void addOwl()
        {
            owlList.add(new Owl());
            thread = new Thread(threadGroup, new OwlRunnable((Owl)owlList.get(owlList.size() - 1)));
            thread.start();  
            
            threadGroup.list();
        }
        
        public void stop() 
        {
            stopped = true;
        }
        
        public void startAnimation() 
        {
            if(stopped)
            {
                stopped = false;
                synchronized(lock)
                {
                    lock.notifyAll();
                }
            }
        }
        
        
        
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            
            for(int i = 0; i < owlList.size(); i++)
            {
                g.drawImage(Owl.getImg(), ((Owl)(owlList.get(i))).x, ((Owl)(owlList.get(i))).y, null);
            }
        }
        ArrayList owlList = new ArrayList();
        JPanel tmpAnimationPanel = this;
        Thread thread;
        ThreadGroup threadGroup = new ThreadGroup("Owl Group");


        public class OwlRunnable implements Runnable
        {
            public OwlRunnable(Owl owl)
            {
                this.owl = owl;
            }
            @Override
            public void run() 
            {
             
                while (true)
                {
                    synchronized(lock)
                    {
                        while(stopped)
                        {
                            try 
                            {
                                lock.wait();
                            } catch (InterruptedException ex) 
                            {
                                ex.printStackTrace();
                            }
                        }
                    }
                    this.owl.moveOwl(tmpAnimationPanel);
                    repaint();

                    try 
                    {
                        Thread.sleep(1);
                    } 
                    catch (InterruptedException ex) 
                    {
                        ex.printStackTrace();
                    }
                }
                    
            
                
            }
            
            Owl owl;
            
        }
    }
    
}

class Owl
{
    public static Image getImg()
    {
        return Owl.owl;
    }
    public void moveOwl(JPanel cointainer)
    {
        
        Rectangle panelBorders = cointainer.getBounds();
        x += dx;
        y += dy;
        
        
        if(y + yOwl >= panelBorders.getMaxY())
        {
            y = (int)(panelBorders.getMaxY() - yOwl);
            dy = -dy;
        }
        
        if(x + xOwl >= panelBorders.getMaxX())
        {
            x = (int)(panelBorders.getMaxX() - xOwl);
            dx = -dx;
        }
        
        if(y < panelBorders.getMinY())
        {
            y = (int)(panelBorders.getMinY());
            dy = -dy;
        }
        
        if(x < panelBorders.getMinX())
        {
            x = (int)(panelBorders.getMinX());
            dx = -dx;
        }
        
       
    }
    public static Image owl = new ImageIcon("Owl.png").getImage();
    
    int x = 0;
    int y = 0;
    int dx = 1;
    int dy = 1;
    int xOwl = owl.getWidth(null);
    int yOwl = owl.getHeight(null);
    
}
