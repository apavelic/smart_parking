package main.java.com.pavelic.smartparking;

import main.java.com.pavelic.smartparking.services.ParkingService;
import main.java.com.pavelic.smartparking.services.ParkingStateService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andrej on 03.06.2017..
 */

@ApplicationPath("/")

public class AppConfig extends Application {


    @Override
    public Set<Class<?>> getClasses() {
        HashSet h = new HashSet<Class<?>>();
        h.add(ParkingService.class);
        h.add(ParkingStateService.class);
        return h;
    }
}
