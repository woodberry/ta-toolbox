package au.net.woodberry.ta.toolbox.indicators;

import eu.verdelhan.ta4j.Tick;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestDataUtils {

    private TestDataUtils() {}

    public static List<Tick> createTickData(String stubData) {
        return createTickData(stubData, null);
    }

    public static List<Tick> createTickData(String stubData, DateTimeFormatter dtf) {
        List<Tick> ticks = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(TestDataUtils.class.getResourceAsStream(stubData)));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                DateTime dt = dtf != null ? DateTime.parse(data[0], dtf) : DateTime.parse(data[0]);
                Tick tick = new Tick(dt, Double.parseDouble(data[1]), Double.parseDouble(data[2]), Double.parseDouble(data[3]),
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
