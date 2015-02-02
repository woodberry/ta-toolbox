package au.net.woodberry.ta.toolbox.indicators;

import eu.verdelhan.ta4j.Tick;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StubDataTestUtils {
    
    private static final int COLUMN_COUNT = 7;
    private static final int TIMESTAMP = 0;
    private static final int OPEN = 1;
    private static final int HIGH = 2;
    private static final int LOW = 3;
    private static final int CLOSE = 4;
    private static final int VOLUME = 5;

    private StubDataTestUtils() {}

    public static List<Tick> createTickData(String stubData) {
        return createTickData(stubData, null);
    }

    public static List<Tick> createTickData(String stubData, DateTimeFormatter dtf) {
        List<Tick> ticks = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(StubDataTestUtils.class.getResourceAsStream(stubData)));
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                if (data == null || data.length != COLUMN_COUNT) {
                    continue;
                }
                DateTime dt = dtf != null ? DateTime.parse(data[TIMESTAMP], dtf) : DateTime.parse(data[TIMESTAMP]);
                Tick tick = new Tick(dt, Double.parseDouble(data[OPEN]), Double.parseDouble(data[HIGH]), Double.parseDouble(data[LOW]),
                        Double.parseDouble(data[CLOSE]), Double.parseDouble(data[VOLUME]));
                ticks.add(tick);
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ticks;
    }
}
