package app;

import javax.swing.*;
import java.awt.*;

public class Draw extends JPanel
{
    public static JFrame frame;
    public static int[] bars;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                initWindow();
            }
        });
    }

    public static void initWindow()
    {
        Draw mainPanel = new Draw();
        frame = new JFrame("Sorting visualizer");
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
        if (bars != null)
        {
            g.setColor(Color.white);
            for (int i = 0; i < bars.length; i++)
                g.fillRect(i*10+5, 500-bars[i], 5, bars[i]);
        }
    }

    public static void updateBars(int[] b)
    {
        bars = b;
        frame.getContentPane().repaint();
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(1005, 500);
    }
}