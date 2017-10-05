/**
 *    Created by maxkirchgesner on 4/18/17.
 *
 *    This is a personal project that allows me to call on any stock and
 *    displays information to me in the way I want to see it
 *
 **/

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class GUI {
    private static int tablelength = 5;
    private static int currentrow = 0;
    private static Object rowData[][] = new Object[tablelength][3];

    /**************************
        GUI for Application
     **************************/
    public GUI() {
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
        menu.add(button1);
        menu.add(button2);
        menu.add(button3);
        menu.add(button4);
        menu.add(button5);

        /**********************************************
            Creating the frame for the application
         **********************************************/
        StockList mystocks = new StockList();
        combobox = new JComboBox();
        combobox.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
        combobox.insertItemAt("Click on dropdown to select a stock", 0);
        for (int i = 1; i < 3218; i++){ combobox.insertItemAt(mystocks.getStocks().get(i).getSymbol() + " -- " + mystocks.getStocks().get(i).getName().replace(" - Common Stock", ""), i); }
        combobox.setSelectedIndex(0);
        combobox.setMaximumRowCount(10);
        combobox.addActionListener ((ActionEvent e) -> {
            // parses company ticker symbol
            String company = (String) combobox.getEditor().getItem();
            String temp = company.substring(0,5).replace(" ","").replace("\t","");
            int s = company.lastIndexOf("--");
            String name = company.substring(s).replace("--", "").replace("\t", "");
            // parses the name of the company
            String name2 = name.substring(1);
            Stock temp_stock = loadStock(name2,temp);
            if (currentrow == 4) { currentrow = 0; }
            rowData[currentrow][0] = " "+temp_stock.getName();
            rowData[currentrow][1] = " "+temp_stock.getSymbol();
            rowData[currentrow++][2] = " "+temp_stock.getPrice();
        });
        AutoCompleteDecorator.decorate(combobox);
        frame.setSize(1200,800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(combobox);
        frame.setVisible(true);

        /****************
            Stock Table
         ****************/
        Object columnNames[] = { "Company", "Stock Symbol", "Price"};
        JTable table = new JTable(rowData, columnNames);
        table.setRowHeight(table.getRowHeight()+10);
        table.getTableHeader().setFont(new Font("Lucide Grande", Font.BOLD, 20));
        table.setFont(new Font("Lucida Grande", Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800,600));
        MatteBorder border = new MatteBorder(1, 1, 0, 0, Color.BLACK);
        table.setBorder(border);

        /***********
            PANELS
         ***********/
        JPanel stockPanel = new JPanel();
        stockPanel.setBackground(Color.GREEN);
        stockPanel.setPreferredSize(new Dimension(300, 400));
        JPanel infoPanel = new JPanel();
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
        frame.setJMenuBar(menu);
        frame.getContentPane().add(Box.createRigidArea(new Dimension(750,20)));
        frame.getContentPane().add(scrollPane);
        frame.add(infoPanel, BorderLayout.EAST);
        frame.setVisible(true);
    }

    /****************************************
        Gets stock requested in search bar
     ****************************************/
    public static Stock loadStock(String company_name, String ticker_symbol) {
        // Create a JSON object
        String url = "https://www.quandl.com/api/v3/datasets/WIKI/" + ticker_symbol + ".json?api_key=tDSfEvKgfs6q-4KhB7Nd";
        Stock stock = new Stock(company_name, ticker_symbol);
        JSONArray array;
        //Load data from Stocks API
        try {
            String getJson = org.apache.commons.io.IOUtils.toString(new URL(url), "UTF-8");
            JSONObject json = new JSONObject(getJson);
            JSONObject j2 = json.getJSONObject("dataset");
            array = j2.getJSONArray("data");
            // format is: date, open, high, low, end-of-day
            for (int i = 0; i < 1; i++) {
                JSONArray temparray = array.getJSONArray(i);
                stock.setPrice(temparray.getString(4));
            }
        } catch (JSONException | IOException e) {
            System.out.println("Caught exception: " + e);
        }
        return stock;
    }

    /**********
        Main
     **********/
    public static void main(String[] args) { GUI gui = new GUI(); }
}