import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class StockLoader {

    public StockLoader(){}

    public static Stock loadStock(String company_name, String ticker_symbol)
    {
        // Create a JSON object
        String url = "https://www.quandl.com/api/v3/datasets/WIKI/" + ticker_symbol + ".json?api_key=tDSfEvKgfs6q-4KhB7Nd";
        Stock stock = new Stock(company_name, ticker_symbol);
        JSONArray array;
        //Load data from Stocks API
        try
        {
            String getJson = org.apache.commons.io.IOUtils.toString(new URL(url), "UTF-8");
            JSONObject json = new JSONObject(getJson);
            JSONObject j2 = json.getJSONObject("dataset");
            array = j2.getJSONArray("data");
            // format is: date, open, high, low, end-of-day
            for (int i = 0; i < 1; i++)
            {
                JSONArray temparray = array.getJSONArray(i);
                stock.setPrice(temparray.get(4).toString());
                stock.setDate(temparray.get(0).toString());
            }
        }
        catch (JSONException | IOException e)
        {
            System.out.println("Caught exception: " + e);
        }
        return stock;
    }

    public static ArrayList<Double> loadStock5Day(String company_name, String ticker_symbol)
    {
        // Create a JSON object
        String url = "https://www.quandl.com/api/v3/datasets/WIKI/" + ticker_symbol + ".json?api_key=tDSfEvKgfs6q-4KhB7Nd";
        JSONArray array;
        ArrayList<Double> prices = new ArrayList<>();
        //Load data from Stocks API
        try
        {
            String getJson = org.apache.commons.io.IOUtils.toString(new URL(url), "UTF-8");
            JSONObject json = new JSONObject(getJson);
            JSONObject j2 = json.getJSONObject("dataset");
            array = j2.getJSONArray("data");
            // format is: date, open, high, low, end-of-day
            for (int i = 4; i >= 0; i--)
            {
                JSONArray temparray = array.getJSONArray(i);
                prices.add(Double.parseDouble(temparray.get(4).toString()));
            }
        }
        catch (JSONException | IOException e)
        {
            System.out.println("Caught exception: " + e);
        }
        return prices;
    }

    public static ArrayList<Double> loadStock10Day(String company_name, String ticker_symbol)
    {
        // Create a JSON object
        String url = "https://www.quandl.com/api/v3/datasets/WIKI/" + ticker_symbol + ".json?api_key=tDSfEvKgfs6q-4KhB7Nd";
        JSONArray array;
        ArrayList<Double> prices = new ArrayList<>();
        //Load data from Stocks API
        try
        {
            String getJson = org.apache.commons.io.IOUtils.toString(new URL(url), "UTF-8");
            JSONObject json = new JSONObject(getJson);
            JSONObject j2 = json.getJSONObject("dataset");
            array = j2.getJSONArray("data");
            // format is: date, open, high, low, end-of-day
            for (int i = 9; i >= 0; i--)
            {
                JSONArray temparray = array.getJSONArray(i);
                prices.add(Double.parseDouble(temparray.get(4).toString()));
            }
        }
        catch (JSONException | IOException e)
        {
            System.out.println("Caught exception: " + e);
        }
        return prices;
    }

    public static ArrayList<Double> loadStock1Month(String company_name, String ticker_symbol)
    {
        // Create a JSON object
        String url = "https://www.quandl.com/api/v3/datasets/WIKI/" + ticker_symbol + ".json?api_key=tDSfEvKgfs6q-4KhB7Nd";
        JSONArray array;
        ArrayList<Double> prices = new ArrayList<>();
        //Load data from Stocks API
        try
        {
            String getJson = org.apache.commons.io.IOUtils.toString(new URL(url), "UTF-8");
            JSONObject json = new JSONObject(getJson);
            JSONObject j2 = json.getJSONObject("dataset");
            array = j2.getJSONArray("data");
            // format is: date, open, high, low, end-of-day
            for (int i = 30; i >= 0; i--)
            {
                JSONArray temparray = array.getJSONArray(i);
                prices.add(Double.parseDouble(temparray.get(4).toString()));
            }
        }
        catch (JSONException | IOException e)
        {
            System.out.println("Caught exception: " + e);
        }
        return prices;
    }

    public static ArrayList<Double> loadStock1Year(String company_name, String ticker_symbol)
    {
        // Create a JSON object
        String url = "https://www.quandl.com/api/v3/datasets/WIKI/" + ticker_symbol + ".json?api_key=tDSfEvKgfs6q-4KhB7Nd";
        JSONArray array;
        ArrayList<Double> prices = new ArrayList<>();
        //Load data from Stocks API
        try
        {
            String getJson = org.apache.commons.io.IOUtils.toString(new URL(url), "UTF-8");
            JSONObject json = new JSONObject(getJson);
            JSONObject j2 = json.getJSONObject("dataset");
            array = j2.getJSONArray("data");
            // format is: date, open, high, low, end-of-day
            for (int i = 364; i >= 0; i--)
            {
                JSONArray temparray = array.getJSONArray(i);
                prices.add(Double.parseDouble(temparray.get(4).toString()));
            }
        }
        catch (JSONException | IOException e)
        {
            System.out.println("Caught exception: " + e);
        }
        return prices;
    }

    public static ArrayList<Double> loadStock5Year(String company_name, String ticker_symbol)
    {
        // Create a JSON object
        String url = "https://www.quandl.com/api/v3/datasets/WIKI/" + ticker_symbol + ".json?api_key=tDSfEvKgfs6q-4KhB7Nd";
        JSONArray array;
        ArrayList<Double> prices = new ArrayList<>();
        //Load data from Stocks API
        try
        {
            String getJson = org.apache.commons.io.IOUtils.toString(new URL(url), "UTF-8");
            JSONObject json = new JSONObject(getJson);
            JSONObject j2 = json.getJSONObject("dataset");
            array = j2.getJSONArray("data");
            // format is: date, open, high, low, end-of-day
            for (int i = 1825; i >= 0; i--)
            {
                JSONArray temparray = array.getJSONArray(i);
                prices.add(Double.parseDouble(temparray.get(4).toString()));
            }
        }
        catch (JSONException | IOException e)
        {
            System.out.println("Caught exception: " + e);
        }
        return prices;
    }

}