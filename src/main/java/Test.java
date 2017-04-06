import com.gl.planesAndAirfileds.simulator.util.PlaneDataUtil;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created by marcin.majka on 1/3/2017.
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(LocalDateTime.now(Clock.systemUTC()));
        long now = LocalDateTime.now(Clock.systemUTC()).toEpochSecond(ZoneOffset.UTC);
        System.out.println(now);
        now = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(2));
        System.out.println(now);

    }
}
