import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class GraphsPage extends JComponent {

    private static Object favTable[][] = new Object[10][2];
    private JFrame frame;
    public static XYDataset ds;
    private XYSeriesCollection dataset = new XYSeriesCollection();
    private XYSeries series1 = new XYSeries("Stock");

    private String currentCompany;
    private String currentStock;
    private String tempStr;
    private String currentName;

    public GraphsPage(StockList sl)
    {
        JComboBox combobox;
        AutoCompleteDecorator decorator = new AutoCompleteDecorator();

        /***************************************
         Frame that everything goes into
         ***************************************/
        frame = new JFrame("MyStocks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);
        frame.setSize(1000,1000);

        /*******************************
         Menu bar for stock search
         *******************************/
        JMenuBar menu = new JMenuBar();
        menu.setBackground(new Color(59,89,152));
        menu.setOpaque(true);
        JButton button1 = new JButton("Stock Search");
        button1.setBackground(new Color(59,89,152));
        button1.setForeground(Color.BLACK);
        button1.setFont(new Font("Arial", Font.BOLD, 14));
        button1.setOpaque(true);
        JButton button3 = new JButton("Graphs");
        button3.setBackground(new Color(59,89,152));
        button3.setForeground(Color.BLACK);
        button3.setFont(new Font("Arial", Font.BOLD, 14));
        button3.setOpaque(true);

        button1.addActionListener((ActionEvent e) ->
        {
            frame.setContentPane(new GUI(sl));
            destroyFrame();
        });
        button3.addActionListener((ActionEvent e) -> frame.setContentPane(this));

        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonColor = new Color(59,89,152);

        JButton fiveDayButton = new JButton("5 Day Graph");
        fiveDayButton.setBackground(buttonColor);
        fiveDayButton.setOpaque(true);
        fiveDayButton.setForeground(Color.BLACK);
        fiveDayButton.setFont(buttonFont);
        JButton tenDayButton = new JButton("10 Day Graph");
        tenDayButton.setBackground(buttonColor);
        tenDayButton.setOpaque(true);
        tenDayButton.setForeground(Color.BLACK);
        tenDayButton.setFont(buttonFont);
        JButton oneMonthButton = new JButton("30 Day Graph");
        oneMonthButton.setBackground(buttonColor);
        oneMonthButton.setOpaque(true);
        oneMonthButton.setForeground(Color.BLACK);
        oneMonthButton.setFont(buttonFont);
        JButton oneYearButton = new JButton("1 Year Graph");
        oneYearButton.setBackground(buttonColor);
        oneYearButton.setOpaque(true);
        oneYearButton.setForeground(Color.BLACK);
        oneYearButton.setFont(buttonFont);
        JButton fiveYearButton = new JButton("5 Year Graph");
        fiveYearButton.setBackground(buttonColor);
        fiveYearButton.setOpaque(true);
        fiveYearButton.setForeground(Color.BLACK);
        fiveYearButton.setFont(buttonFont);

        fiveDayButton.addActionListener( (ActionEvent e) ->
        {
            series1.clear();
            ArrayList<Double> yearPrices = StockLoader.loadStock5Day(currentStock,tempStr);
            for (int i = 0; i < yearPrices.size(); i++)
            {
                series1.add(i+1, yearPrices.get(i));
            }
        });
        tenDayButton.addActionListener( (ActionEvent e) ->
        {
            series1.clear();
            ArrayList<Double> yearPrices = StockLoader.loadStock10Day(currentStock,tempStr);
            for (int i = 0; i < yearPrices.size(); i++)
            {
                series1.add(i+1, yearPrices.get(i));
            }
        });
        oneMonthButton.addActionListener( (ActionEvent e) ->
        {
            series1.clear();
            ArrayList<Double> yearPrices = StockLoader.loadStock1Month(currentStock,tempStr);
            for (int i = 0; i < yearPrices.size(); i++)
            {
                series1.add(i+1, yearPrices.get(i));
            }
        });
        oneYearButton.addActionListener( (ActionEvent e) ->
        {
            series1.clear();
            ArrayList<Double> yearPrices = StockLoader.loadStock1Year(currentStock,tempStr);
            for (int i = 0; i < yearPrices.size(); i++)
            {
                series1.add(i+1, yearPrices.get(i));
            }
        });
        fiveYearButton.addActionListener( (ActionEvent e) ->
        {
            series1.clear();
            ArrayList<Double> yearPrices = StockLoader.loadStock5Year(currentStock,tempStr);
            for (int i = 0; i < yearPrices.size(); i++)
            {
                series1.add(i+1, yearPrices.get(i));
            }
        });

        menu.add(button1);
        menu.add(button3);
        menu.add(fiveDayButton);
        menu.add(tenDayButton);
        menu.add(oneMonthButton);
        menu.add(oneYearButton);
        menu.add(fiveYearButton);

        /**********************************************
                 Creating the combobox for stocks
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
            series1.clear();
            // parses company ticker symbol
            currentCompany = (String) combobox.getEditor().getItem();
            tempStr = currentCompany.substring(0,5).replace(" ","").replace("\t","");
            int s = currentCompany.lastIndexOf("--");
            currentName = currentCompany.substring(s).replace("--", "").replace("\t", "");
            // parses the name of the company
            currentStock = currentName.substring(1);
            Stock temp = StockLoader.loadStock(currentStock,tempStr);
            ArrayList<Double> yearPrices = StockLoader.loadStock1Year(currentStock,tempStr);
            for (int i = 0; i < yearPrices.size(); i++)
            {
                series1.add(i+1, yearPrices.get(i));
            }
            dataset.addSeries(series1);
        });

        decorator.decorate(combobox);
        frame.setSize(1200,800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(combobox);
        frame.setVisible(true);

        /***********
         GRAPH
         ***********/
        JFreeChart lineChart = ChartFactory.createXYLineChart("Stock Graph","Day","Price",dataset,PlotOrientation.VERTICAL,false,true,false);
        lineChart.setBackgroundPaint(Color.white);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        JPanel jp1 = new JPanel();
        jp1.add(chartPanel);

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
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setPreferredSize(new Dimension(200, 400));
        TitledBorder fav_panel_title = new TitledBorder("FAVORITES");
        fav_panel_title.setTitleJustification(TitledBorder.CENTER);
        fav_panel_title.setTitlePosition(TitledBorder.TOP);
        fav_panel_title.setTitleFont(new Font("Arial", Font.ITALIC, 14));
        infoPanel.setBorder(fav_panel_title);

        frame.setJMenuBar(menu);
        frame.getContentPane().add(Box.createRigidArea(new Dimension(750,20)));
        frame.add(chartPanel);
        frame.add(infoPanel, BorderLayout.EAST);
        frame.setVisible(true);

    }
    public void destroyFrame(){ frame.dispose();}

}