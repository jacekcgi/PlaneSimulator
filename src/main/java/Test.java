import com.gl.planesAndAirfileds.simulator.domain.Plane;

import java.time.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

        Consumer<Plane> greeter = (p) -> System.out.println("Hello, " + p.getId());
        greeter.accept(new Plane());

        Plane p1 = new Plane();
        p1.setId(1l);
        Plane p2 = new Plane();
        p2.setId(2l);
        Plane p3 = new Plane();
        p3.setId(3l);

        Plane[] list = {p1,p2,p3};

        List<Long> names = Arrays.stream(list)
                .map(Plane::getId)
                .collect(Collectors.toList());
        System.out.println(names);
    }
}
