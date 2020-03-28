import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
        // below tow lines fix first time click no responding bug.
        rootPanel.revalidate();
        rootPanel.repaint();
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
                String url = textField2.getText();
                HtmlToText htmlToText = HtmlToText.getInstance();
                String result = htmlToText.HtmlToText(url);
                textField2.setText(url);
                textPane2.setText(result);
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
                String url = textField3.getText();
                FindingEmailAddressInAPage findingEmailAddressInAPage = FindingEmailAddressInAPage.getInstance();
                ArrayList<String> results = findingEmailAddressInAPage.FindingEmailAddressInAPage(url);
                textField3.setText(url);
                String result = "";
                for (String s : results)
                {
                    result += s + "\n";
                }
                textPane3.setText(result);
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
