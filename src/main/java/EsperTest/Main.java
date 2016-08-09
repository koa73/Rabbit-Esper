package EsperTest;

import com.espertech.esper.client.*;

import java.util.Random;

class Main {

    private static final Random generator = new Random();

    private static void GenerateRandomTick(EPRuntime cepRT) {
        double price = (double) generator.nextInt(10);
        long timeStamp = System.currentTimeMillis();
        String symbol = "AAPL";
        Tick tick = new Tick(symbol, price, timeStamp);
        System.out.println("Sending tick:" + tick);
        cepRT.sendEvent(tick);
    }

    public static void main(String[] args) {

        //The Configuration is meant only as an initialization-time object.
        Configuration epConfig = new Configuration();

        // We register Ticks as objects the engine will have to handle
        epConfig.addEventType("StockTick",Tick.class.getName());

        // We setup the engine
        EPServiceProvider epService = EPServiceProviderManager.getProvider("myCEPEngine",epConfig);
        EPRuntime epRT = epService.getEPRuntime();

        //final String expression = "select avg(price) from StockTick(symbol='AAPL').win:time(30 sec)";
        final String expression = "select avg(price) from StockTick(symbol='AAPL').win:length(2)";
        //final String expression = "select * from StockTick(symbol='AAPL').win:length(2) having avg(price) > 6.0";

        EPStatement statement = epService.getEPAdministrator().createEPL(expression);

        statement.addListener(new MyListener());

        for(int i = 0; i< 15; i++)
            GenerateRandomTick(epRT);
    }

    private static class MyListener implements UpdateListener {
        public void update(EventBean[] newEvents, EventBean[] oldEvents) {
            EventBean event = newEvents[0];
            System.out.println("avg=" + event.get("avg(price)"));
        }
    }

    private static class CEPListener implements UpdateListener {
        public void update(EventBean[] newData, EventBean[] oldData) {
            System.out.println("Event received: "
                    + newData[0].getUnderlying());
        }
    }
}


