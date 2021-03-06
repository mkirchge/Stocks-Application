public class Stock {

    private String name;
    private Double change;
    private Double yearlow;
    private Double yearhigh;
    private String symbol;
    private String price;
    private String date;

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

    public String getPrice(){ return price; }

    public double getChange() { return change; }

    public double getYearlow() { return yearlow; }

    public double getYearhigh() { return yearhigh; }

    public String getDate() { return date; }

    public void setPrice(String p) { price = p; }

    public void setDate(String d) { date = d; }

    public void setChange(double c) { change = c; }

    public void setYearlow(double yl) { yearlow = yl; }

    public void setYearhigh(double yh) { yearhigh = yh; }

    public void printStock() {
        System.out.println(" "+name);
        System.out.println(" "+symbol);
        System.out.println(" "+price);
    }

}
