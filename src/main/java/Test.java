import com.gl.planesAndAirfileds.simulator.util.PlaneDataUtil;

/**
 * Created by marcin.majka on 1/3/2017.
 */
public class Test {
    public static void main(String[] args) {

        double latitude = 52.47196705965058;
        double longitude = 20.67182205165279;

        double latitude2 =  51.7219;
        double longitude2 = 19.3980;

        double latitudeR =  Math.toRadians(latitude);
        double longitudeR = Math.toRadians(longitude);

        double latitude2R =  Math.toRadians(latitude2);
        double longitude2R =  Math.toRadians(longitude2);



        System.out.println(PlaneDataUtil.calculateFlightDistanceBetweenPointsDegree(latitude,longitude,latitude2,longitude2));
        System.out.println(PlaneDataUtil.calculateFlightDistanceBetweenPointsRadians(latitudeR,longitudeR,latitude2R,longitude2R));

        System.out.println("Course "+PlaneDataUtil.calculateNewCourseRadians(latitudeR,longitudeR,latitude2R,longitude2R));
        System.out.println("Course "+PlaneDataUtil.calculateFinalCourseRadians(latitude2R,longitude2R,latitudeR,longitudeR));





    }
}
