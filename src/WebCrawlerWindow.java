import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

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
                StringBuilder result = new StringBuilder();
                String keyword = textField1.getText();
                if (keyword.isEmpty()) {
                    textPane1.setText("No results have been found for the provided keyword.");
                } else {
                    Map<String, Integer> resultMap = WebSearchEngine.rankTopPagesByKeyword(keyword);
                    if (!resultMap.isEmpty()) {
                        Iterator<Map.Entry<String, Integer>> iterator = resultMap.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, Integer> entry = iterator.next();
                            String url = entry.getKey();
                            Integer value = entry.getValue();
                            result.append("URL: ").append(url).append(" | Occurrences: ").append(value).append("\n\r");
                            textPane1.setText(result.toString());
                        }
                    } else {
                        textPane1.setText("No results have been found for the provided keyword.");
                    }
                }
                // textField1.setText("Button Clicked.");
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
                String result = "";
                String url = textField4.getText();
                ArrayList<String> results = WebCrawler.TraversalLinksInsideWebsiteWithoutStore(url);
                for (String s : results)
                {
                    result += s + "\n";
                }
                textField4.setText(url);
                textPane4.setText(result);
                subPanel04.revalidate();
                subPanel04.repaint();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("WebCrawlerWindow");
        frame.setContentPane(new WebCrawlerWindow().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setVisible(true);
    }
}
