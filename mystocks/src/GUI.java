/**
 * Created by maxkirchgesner on 4/18/17.
 *
 *    This is a personal project that allows me to call on any stock and
 *    displays information to me in the way I want to see it
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import java.awt.event.ActionListener;
import java.awt.event.*;
import org.apache.commons.io.IOUtils;

// For API integration
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


public class GUI {

    private static StockList mystocks;
    private static StockList recentstocks;

    public GUI(StockList sl){

    }

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

        JMenu menu1 = new JMenu("Menu");
        JMenu menu2 = new JMenu("Stock Search");
        JMenu menu3 = new JMenu("News");

        // add each menu item to the menu
        menu.add(menu1);
        menu1.add(menu2);
        menu1.add(menu3);

        /**********************************************
            Creating the frame for the application
         **********************************************/
        mystocks = new StockList();
        // size of nasdaw file is 3218
        String[] nasdaq_stocks = new String[3218];
        int count = 0;
        for (int i = 0; i < 3218; i++){
            nasdaq_stocks[count++] = mystocks.getStocks().get(i).getSymbol() + " -- " + mystocks.getStocks().get(i).getName().replace(" - Common Stock", "");
        }

        Stock current = new Stock();
        combobox = new JComboBox(nasdaq_stocks);
        combobox.addActionListener ((ActionEvent e) -> {
            // parses company ticker symbol
            String company = (String) combobox.getEditor().getItem();
            String temp = company.substring(0,5).replace(" ","").replace("\t","");
            int s = company.lastIndexOf("--");
            String name = company.substring(s).replace("--", "").replace("\t", "");
            // parses the name of the company
            String name2 = name.substring(1);
            Stock temp_stock = loadStock(name2,temp);
            recentstocks.addStock(temp_stock);
            GUI g = new GUI(recentstocks);
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
        // Object rowData[][] = rowBuilder(loadStock(stock))
        Object rowData[][] = { { "Apple Inc.", "AAPL", "EXAMPLE PRICE"} };

        Object columnNames[] = { "Company", "Stock Symbol", "Price"};
        JTable table = new JTable(rowData, columnNames);
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

        JPanel previewPanel = new JPanel();
        previewPanel.setBackground(Color.ORANGE);
        previewPanel.setPreferredSize(new Dimension(200, 100));

        JPanel pointsPanel = new JPanel();
        pointsPanel.setBackground(Color.RED);
        pointsPanel.setPreferredSize(new Dimension(300, 50));

        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setPreferredSize(new Dimension(200, 400));
        infoPanel.add(previewPanel);
        infoPanel.add(pointsPanel);

        // Adding components to frame
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

        // Default stocks
        String aapl = "https://www.quandl.com/api/v3/datasets/WIKI/AAPL.json?api_key=tDSfEvKgfs6q-4KhB7Nd";
        String goog = "https://www.quandl.com/api/v3/datasets/WIKI/GOOGL.json?api_key=tDSfEvKgfs6q-4KhB7Nd";
        String tsla = "https://www.quandl.com/api/v3/datasets/WIKI/TSLA.json?api_key=tDSfEvKgfs6q-4KhB7Nd";
        Stock s1 = new Stock("Apple Inc.", "AAPL");
        Stock s2 = new Stock("Alphabet Inc. Class A", "GOOGL");
        Stock s3 = new Stock("Tesla Inc.", "TSLA");


        // Create a JSON object
        String url = "https://www.quandl.com/api/v3/datasets/WIKI/" + ticker_symbol + ".json?api_key=tDSfEvKgfs6q-4KhB7Nd";
        Stock stock = new Stock(company_name, ticker_symbol);
        JSONArray array = new JSONArray();

        //Load data from Stocks API
        try {
            String getJson = org.apache.commons.io.IOUtils.toString(new URL(url), "UTF-8");
            JSONObject json = new JSONObject(getJson);

            JSONObject j2 = json.getJSONObject("dataset");
            array = j2.getJSONArray("data");

            // format is: date, open, high, low, end-of-day
            // right now it is only getting the most recent day of the specified stock
            for (int i = 0; i < 1; i++) {
                stock.setPrice(array.get(0).toString());
            }
            stock.printStock();
//            recentstocks.addStock(stock);
        } catch (JSONException | IOException e) {
            System.out.println("Caught exception: " + e);
        }

        // KEY: stock symbol
        // VALUE: company name, price, percent change, 52-week high, 52-week low
        return stock;
    }

    /**************************************
       Stocks to add on click in combobox
     **************************************/
    public void addStocks(){
        // when clicked on, get info from click and add to combobox

        // add eventlistener to main function below, then call addStocks()
    }

    /**********
        Main
     **********/
    public static void main(String[] args) {
        GUI gui = new GUI();


    }
}