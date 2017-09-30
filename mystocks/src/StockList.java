/**
 * Created by maxkirchgesner on 4/18/17.
 */
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class StockList {

    private ArrayList<Stock> stocks;
    private static String nasdaqfile = "NasdaqStocks.txt";

    public StockList(){
        stocks = new ArrayList<Stock>();

        String line;
        try {
            FileReader fileReader = new FileReader(nasdaqfile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                String[] split = line.split("#");
                for (int i = 0; i < split.length; i++){
                    split[i].replace("\\t","");
                }
                Stock temp = new Stock(split[1],split[0]);
                stocks.add(temp);
            }

            bufferedReader.close();
        } catch(FileNotFoundException e) {
            System.out.println("Unable to open file '" + nasdaqfile + "'");
        } catch(IOException e) {
            System.out.println("Error reading file '" + nasdaqfile + "'");
        }
    }

    public ArrayList<Stock> getStocks(){
        return stocks;
    }

    public void addStock(Stock stock){
        stocks.add(stock);
    }

    public void removeStock(Stock stock){
        stocks.remove(stock);
    }

}
