import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI extends JComponent {

    private static Integer tablelength = 5;
    private static Integer tablecols = 3;
    private static Integer currentrow = 0;
    private static Integer fav_row = 0;
    private static Object rowData[][] = new Object[tablelength][tablecols];
    private ArrayList<Stock> favorites = new ArrayList<>();
    private static Object favTable[][] = new Object[10][2];
    JFrame frame;

    /**************************
        GUI for Application
     **************************/
    public GUI(StockList sl)
    {
        JComboBox combobox;
        AutoCompleteDecorator decorator = new AutoCompleteDecorator();

        /***************************************
            Frame that everything goes into
         ***************************************/
        frame = new JFrame("MyStocks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);
//        frame.getContentPane().setBackground(Color.WHITE);
        frame.setSize(1000,1000);

        /*******************************
            Menu bar for stock search
         *******************************/
        JMenuBar menu = new JMenuBar();
        JButton mainPage = new JButton("Stock Search");
        mainPage.setBackground(new Color(59,89,152));
        mainPage.setForeground(Color.BLACK);
        mainPage.setFont(new Font("Arial", Font.BOLD, 14));
        mainPage.setOpaque(true);
        JButton graphPage = new JButton("Graphs");
        graphPage.setBackground(new Color(59,89,152));
        graphPage.setForeground(Color.BLACK);
        graphPage.setFont(new Font("Arial", Font.BOLD, 14));
        graphPage.setOpaque(true);

        mainPage.addActionListener((ActionEvent e) -> frame.setContentPane(this));
        graphPage.addActionListener((ActionEvent e) ->
        {
            frame.setContentPane(new GraphsPage(sl));
            destroyFrame();
        });

        menu.add(mainPage);
        menu.add(graphPage);

        /**********************************************
            Creating the frame for the application
         **********************************************/
        StockList mystocks = new StockList();
        combobox = new JComboBox();
        combobox.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        combobox.insertItemAt("Click on dropdown to select a stock", 0);
        for (int i = 1; i < 3218; i++)
        {
            combobox.insertItemAt(mystocks.getStocks().get(i).getSymbol() + " -- " + mystocks.getStocks().get(i).getName().replace(" - Common Stock", ""), i);
        }
        combobox.setSelectedIndex(0);
        combobox.setMaximumRowCount(10);
        combobox.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        combobox.addActionListener ((ActionEvent e) ->
        {
            // parses company ticker symbol
            String company = (String) combobox.getEditor().getItem();
            String temp = company.substring(0,5).replace(" ","").replace("\t","");
            int s = company.lastIndexOf("--");
            String name = company.substring(s).replace("--", "").replace("\t", "");
            // parses the name of the company
            String name2 = name.substring(1);
            Stock temp_stock = StockLoader.loadStock(name2,temp);
            //favorites.add(temp_stock);
            if (currentrow == 4) { currentrow = 0; }
            if (fav_row == 10) { fav_row = 0; }
            rowData[currentrow][0] = " "+temp_stock.getName();
            rowData[currentrow][1] = " "+temp_stock.getSymbol();
            rowData[currentrow++][2] = " "+temp_stock.getPrice();
            //favTable[fav_row][0] = temp_stock.getSymbol();
            //favTable[fav_row][1] = temp_stock.getPrice();
        });


        decorator.decorate(combobox);
        frame.setSize(1200,1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(combobox);
        frame.setVisible(true);

        /****************
            Stock Table
         ****************/
        JLabel label = new JLabel("Recent Stocks");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        Object columnNames[] = { "Company", "Stock Symbol", "Price"};
        JTable table = new JTable(rowData, columnNames);
        table.setEnabled(false);
        table.setRowHeight(table.getRowHeight()+10);
        table.getTableHeader().setFont(new Font("Lucide Grande", Font.BOLD, 20));
        table.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        table.setGridColor(Color.black);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800,600));
        MatteBorder border = new MatteBorder(1, 1, 0, 0, Color.BLACK);
        table.setBorder(border);
        JPanel mainPanel = new JPanel();
        TitledBorder stocksTableBorder = new TitledBorder("Recently Viewed Stocks");
        stocksTableBorder.setTitleJustification(TitledBorder.CENTER);
        stocksTableBorder.setTitlePosition(TitledBorder.TOP);
        stocksTableBorder.setTitleFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(scrollPane);
        mainPanel.setBorder(stocksTableBorder);

        /***********
            PANELS
         ***********/
        Object fav_columnNames[] = { "Ticker", "Price"};
        JTable fav_table = new JTable(favTable, fav_columnNames);
        fav_table.setGridColor(Color.black);
        JScrollPane fav_bar = new JScrollPane(fav_table);
        JPanel stockPanel = new JPanel();
        stockPanel.setBackground(Color.GREEN);
        stockPanel.setPreferredSize(new Dimension(300, 400));
        JPanel infoPanel = new JPanel();
        infoPanel.add(fav_bar, BorderLayout.CENTER);
        infoPanel.setBackground(Color.WHITE);
        JPanel previewPanel = new JPanel();
        previewPanel.setBackground(Color.ORANGE);
        previewPanel.setPreferredSize(new Dimension(100, 300));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setPreferredSize(new Dimension(200, 600));
        infoPanel.add(previewPanel);

        /***********************************************************
            Add favorites StockList to infopanel (in smaller table)
            This should appear on every window (page)
         ***********************************************************/
        TitledBorder fav_panel_title = new TitledBorder("FAVORITES");
        fav_panel_title.setTitleJustification(TitledBorder.CENTER);
        fav_panel_title.setTitlePosition(TitledBorder.TOP);
        fav_panel_title.setTitleFont(new Font("Arial", Font.ITALIC, 14));
        infoPanel.setBorder(fav_panel_title);

        frame.setJMenuBar(menu);
        frame.getContentPane().add(Box.createRigidArea(new Dimension(750,20)));
        frame.getContentPane().add(mainPanel);
        frame.add(infoPanel, BorderLayout.EAST);
        frame.setVisible(true);
    }
    public void destroyFrame(){ frame.dispose();}

    /**********
        Main
     **********/
    public static void main(String[] args)
    {
        StockList emptylist = new StockList();
        GUI gui = new GUI(emptylist);
    }
}