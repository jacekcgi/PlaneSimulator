import com.gl.planesAndAirfileds.simulator.domain.Plane;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by marcin.majka on 1/3/2017.
 */
public class Test {
    public static void main(String[] args) {
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
