import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Created by marcin.majka on 1/3/2017.
 */
public class Test {
    public static void main(String[] args) {

        double maxVelocity = 450;
        double res = maxVelocity+maxVelocity*(-30)/100;
        System.out.println("maxVelocity "+res);

        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        LocalDateTime time = utc.toLocalDateTime();
        time = time.minus(Duration.ofMinutes(5));
        Duration between = Duration.between(time, utc.toLocalDateTime());
        System.out.println("between "+between.getSeconds());


        double maxDistance = 12500;
        maxDistance = maxDistance - maxDistance*0.10;
        double minDistance = maxDistance*0.10;


        System.out.println("maxDistance " +maxDistance+ " "+minDistance);

        double capacity = 204500;
        double average = 12;
        double percentage = 30;
        double cons = average*percentage/100;
        System.out.println("SSS "+cons);

        double flightTimeInHour = 9830d/3600000d;
        System.out.println(flightTimeInHour);

    }
}
