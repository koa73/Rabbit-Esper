package EsperTest; /**
 * Created by OAKutsenko on 09.08.2016.
 */

import java.util.Date;

public class Tick {

    final String symbol;
    final Double price;
    final Date timeStamp;

    public Tick(String s, double p, long t) {
        symbol = s;
        price = p;
        timeStamp = new Date(t);
    }
    public double getPrice() {return price;}
    public String getSymbol() {return symbol;}
    public Date getTimeStamp() {return timeStamp;}

    @Override
    public String toString() {
        return "Price: " + price.toString() + " time: " + timeStamp.toString();
    }
}
