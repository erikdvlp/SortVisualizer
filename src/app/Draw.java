package app;

import javax.swing.*;
import java.awt.*;

public class Draw extends JPanel
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
                initWindow();
            }
        });
    }

    public static void initWindow()
    {
        Draw mainPanel = new Draw();
        JFrame frame = new JFrame("Sorting visualizer");
        frame.getContentPane().add(mainPanel);
        
        mainPanel.setBackground(Color.black);
        //frame.setLayout(new FlowLayout());
        frame.getContentPane().setBackground(Color.black);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawBars(g);
    }

    public void drawBars(Graphics g)
    {
        g.setColor(Color.white);
        g.fillRect(10, 0, 10, 450);
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(800, 500);
    }
}