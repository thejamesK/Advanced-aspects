package rysowanie;

import java.awt.*;
import javax.swing.*;

public class Rysowanie extends JFrame
{
    public Rysowanie()
    {
        this.setTitle("Drawing");
        this.setBounds(250, 300, 300, 250);
        
        
        
        this.getContentPane().add(drawingPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) 
    {
        
        new Rysowanie().setVisible(true);
        
    }
    
    private DrawingPanel drawingPanel = new DrawingPanel();
    
}

class DrawingPanel extends JPanel
{
    public DrawingPanel()
    {
        
        super();
        
        this.add(new JButton("test"){
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            System.out.println(i++);
        }
        
        });
    }
    
    @Override
    public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.drawString("Title", 0, 40);
            
            g.drawImage(new ImageIcon("PS4.png").getImage(), 40, 80, null);
            
            g.drawRect(40, 40, 120, 80);
            
            g.drawLine(60, 60, 80, 20);
            
            
            Graphics2D g2 = (Graphics2D)g;
            
            System.out.println(i++);
        }
    
    
   public static int i = 0;
}
