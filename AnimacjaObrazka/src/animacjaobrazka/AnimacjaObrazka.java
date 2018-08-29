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
        
        JButton deleteButton = (JButton)buttonPanel.add(new JButton("Delete"));
        
        deleteButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent ae) 
            {
                stopAnimation();
            }
        });
        
        
        this.getContentPane().add(animationPanel);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void startAnimation()
    {
        animationPanel.addOwl();
    }
    
    public void stopAnimation()
    {
        animationPanel.stop();
    }
    
    private JPanel buttonPanel = new JPanel();
    private AnimationPanel animationPanel = new AnimationPanel();
    public static void main(String[] args) 
    {
        new AnimacjaObrazka().setVisible(true);               
    }
    
    class AnimationPanel extends JPanel
    {
        public void addOwl()
        {
            owlList.add(new Owl());
            thread = new Thread(threadGroup, new OwlRunnable((Owl)owlList.get(owlList.size() - 1)));
            thread.start();  
            
            threadGroup.list();
        }
        
        public void stop() 
        {
            threadGroup.interrupt();
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
                try
                {   
                    while (!Thread.currentThread().isInterrupted())
                    {
                        this.owl.moveOwl(tmpAnimationPanel);
                        repaint();
                                               
                        Thread.sleep(1);
                    }
                }    
                catch (InterruptedException ex) 
                {
                    System.out.println(ex.getMessage());
                    owlList.clear();
                    repaint();
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
