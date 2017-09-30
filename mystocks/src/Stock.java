/**
 * Created by maxkirchgesner on 4/18/17.
 */

public class Stock {

    private String name;
    private float change;
    private float yearlow;
    private float yearhigh;
    private String symbol;
    private int price;

    public Stock(){
        name = "";
        symbol = "";
    }

    public Stock(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName(){
        return name;
    }

    public String getSymbol(){
        return symbol;
    }

    public int getPrice(){ return price; }

    public float getChange() { return change; }

    public float getYearlow() { return yearlow; }

    public float getYearhigh() { return yearhigh; }

    public void setPrice(int p) { price = p; }

    public void setChange(float c) { change = c; }

    public void setYearlow(float yl) { yearlow = yl; }

    public void setYearhigh(float yh) { yearhigh = yh; }

}
