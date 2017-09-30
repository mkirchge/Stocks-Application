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

        combobox = new JComboBox(nasdaq_stocks);
        combobox.addActionListener ((ActionEvent e) -> {
            String company = (String) combobox.getEditor().getItem();
            String temp = company.substring(0,5).replace(" ","").replace("\t","");
            System.out.println(temp);
            System.out.println(company);
            loadStock(temp);
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
        Object rowData[][] = { { "aa", "aa", "aa", "aa", "aa", "aa" }, { "bb", "bb", "bb", "bb", "bb", "bb" } };
        Object columnNames[] = { "Company", "Stock Symbol", "Price", "Percent Change (+/-)", "52-Week High", "52-Week Low" };
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
    public static Stock loadStock(String ticker_symbol) {

        // Create a JSON object
        String url = "https://www.quandl.com/api/v3/datasets/WIKI/" + ticker_symbol + ".json?api_key=tDSfEvKgfs6q-4KhB7Nd";
        ArrayList<String> list = new ArrayList<String>();
        Stock stock = new Stock();
        JSONArray array = new JSONArray();

        //Load data from Stocks API
        try {
            String getJson = org.apache.commons.io.IOUtils.toString(new URL(url), "UTF-8");
            JSONObject json = new JSONObject(getJson);

            JSONObject j2 = json.getJSONObject("dataset");
            array = j2.getJSONArray("data");

            for (int i = 0; i < 5; i++) {
                System.out.println(array.get(i));
            }
        } catch (JSONException | IOException e) {
            System.out.println("Caught exception: " + e);
        }

        // KEY: stock symbol
        // VALUE: company name, price, percent change, 52-week high, 52-week low
        return stock;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) { sb.append((char) cp); }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally { is.close(); }
    }

    /*********************************************************
        Builds a row for the stock that goes into the table
     *********************************************************/
    public String[][] rowBuilder(HashMap<String,ArrayList<String>> stock) {
        String[][] row = new String[1][5];
        // public Stock(String n, String s, int p, float c, float yl, float yh)
        //Stock temp = new Stock(name, symbol, price, change, yearlow, yearhigh);
        return row;
    }

    /*************************************************************
        These are the stocks that will be displayed in the table
     *************************************************************/
    public StockList baseStocks() {
        // DowJones, NASDAQ, NYSE, etc...
        // Apple, Google, Amazon, Tesla, GE, Chevron,
        StockList default_list = new StockList();
        return default_list;
    }

    /**********
        Main
     **********/
    public static void main(String[] args) {
        GUI gui = new GUI();
    }
}