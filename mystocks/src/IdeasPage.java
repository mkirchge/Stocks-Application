import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class IdeasPage {

    private static Object favTable[][] = new Object[10][2];


    public IdeasPage(StockList sl){

        JComboBox combobox;
        AutoCompleteDecorator decorator;
        JFrame frame;

        /***************************************
         Frame that everything goes into
         ***************************************/
        frame = new JFrame("MyStocks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.GRAY);
        frame.setSize(1000,1000);

        /*******************************
         Menu bar for stock search
         *******************************/
        JMenuBar menu = new JMenuBar();
        JButton button1 = new JButton("Stock Search");
        button1.setBackground(new Color(211,211,211));
        button1.setForeground(Color.BLACK);
        button1.setFont(new Font("Arial", Font.BOLD, 14));
        JButton button2 = new JButton("News");
        button2.setBackground(new Color(211,211,211));
        button2.setForeground(Color.BLACK);
        button2.setFont(new Font("Arial", Font.BOLD, 14));
        JButton button3 = new JButton("Graphs");
        button3.setBackground(new Color(211,211,211));
        button3.setForeground(Color.BLACK);
        button3.setFont(new Font("Arial", Font.BOLD, 14));
        JButton button4 = new JButton("Ideas");
        button4.setBackground(new Color(211,211,211));
        button4.setForeground(Color.BLACK);
        button4.setFont(new Font("Arial", Font.BOLD, 14));
        JButton button5 = new JButton("Help");
        button5.setBackground(new Color(211,211,211));
        button5.setForeground(Color.BLACK);
        button5.setFont(new Font("Arial", Font.BOLD, 14));
        button1.addActionListener( (ActionEvent e) -> { new GUI(sl); });
        button2.addActionListener( (ActionEvent e) -> { new NewsPage(sl); });
        button3.addActionListener( (ActionEvent e) -> { new GraphsPage(sl); });
        button4.addActionListener( (ActionEvent e) -> { new IdeasPage(sl); });
        button5.addActionListener( (ActionEvent e) -> { new HelpPage(sl); });
        menu.add(button1);
        menu.add(button2);
        menu.add(button3);
        menu.add(button4);
        menu.add(button5);


        /***********
         PANELS
         ***********/
        Object fav_columnNames[] = { "Ticker", "Price"};
        JTable fav_table = new JTable(favTable, fav_columnNames);
        JScrollPane fav_bar = new JScrollPane(fav_table);
        JPanel stockPanel = new JPanel();
        stockPanel.setBackground(Color.GREEN);
        stockPanel.setPreferredSize(new Dimension(300, 400));
        JPanel infoPanel = new JPanel();
        infoPanel.add(fav_bar, BorderLayout.CENTER);
        infoPanel.setBackground(Color.WHITE);
//        JPanel previewPanel = new JPanel();
//        previewPanel.setBackground(Color.ORANGE);
//        previewPanel.setPreferredSize(new Dimension(100, 300));
//        JPanel pointsPanel = new JPanel();
//        pointsPanel.setBackground(Color.RED);
//        pointsPanel.setPreferredSize(new Dimension(300, 100));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setPreferredSize(new Dimension(200, 400));
//        infoPanel.add(previewPanel);
//        infoPanel.add(pointsPanel);
        TitledBorder fav_panel_title = new TitledBorder("FAVORITES");
        fav_panel_title.setTitleJustification(TitledBorder.CENTER);
        fav_panel_title.setTitlePosition(TitledBorder.TOP);
        fav_panel_title.setTitleFont(new Font("Arial", Font.ITALIC, 14));
        infoPanel.setBorder(fav_panel_title);



        frame.setJMenuBar(menu);
        frame.getContentPane().add(Box.createRigidArea(new Dimension(750,20)));
        frame.add(infoPanel, BorderLayout.EAST);
        frame.setVisible(true);

    }

}
