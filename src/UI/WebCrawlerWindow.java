package UI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Maxi Agrippa
 */
public class WebCrawlerWindow
{
    private JTabbedPane tabbedPane1;
    private JPanel subPanel01;
    private JPanel subPanel02;
    private JTextField textField1;
    private JButton searchButton;
    private JTextPane textPane1;
    public JPanel rootPanel;
    private JTextField textField2;
    private JButton startButton;
    private JTextPane textPane2;
    private JPanel subPanel03;
    private JPanel subPanel04;
    private JTextField textField3;
    private JButton startButton1;
    private JTextPane textPane3;
    private JTextField textField4;
    private JButton startButton2;
    private JTextPane textPane4;

    public WebCrawlerWindow ()
    {
        searchButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked (MouseEvent e)
            {
                super.mouseClicked(e);
                textField1.setText("Button Clicked.");
                textPane1.setText("Button Clicked pane");
                subPanel01.revalidate();
                subPanel01.repaint();
            }
        });
        startButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked (MouseEvent e)
            {
                super.mouseClicked(e);
                textField2.setText("Button Clicked.");
                textPane2.setText("Button Clicked pane");
                subPanel02.revalidate();
                subPanel02.repaint();
            }
        });
        startButton1.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked (MouseEvent e)
            {
                super.mouseClicked(e);
                textField3.setText("Button Clicked.");
                textPane3.setText("Button Clicked pane");
                subPanel03.revalidate();
                subPanel03.repaint();
            }
        });
        startButton2.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked (MouseEvent e)
            {
                super.mouseClicked(e);
                textField4.setText("Button Clicked.");
                textPane4.setText("Button Clicked pane");
                subPanel04.revalidate();
                subPanel04.repaint();
            }
        });
    }
}
