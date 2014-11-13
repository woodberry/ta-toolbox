package au.net.woodberry.ta.toolbox.indicators.volatility.cbl.longside;

import eu.verdelhan.ta4j.Tick;
import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private Utils() {}

    static List<Tick> createTickData(String stubData) {
        List<Tick> ticks = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(CountBackLineTest.class.getResourceAsStream(stubData)));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                Tick tick = new Tick(DateTime.parse(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]),
                        Double.parseDouble(data[4]), Double.parseDouble(data[5]));
                ticks.add(tick);
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ticks;
    }
}
