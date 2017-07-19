package main.java.com.pavelic.smartparking.services;
import com.google.gson.Gson;
import main.java.com.pavelic.smartparking.models.Parking;
import main.java.com.pavelic.smartparking.models.ParkingSettings;
import main.java.com.pavelic.smartparking.server.Server;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Andrej on 03.06.2017..
 */
@Path("/parking")
public class ParkingService {

    private Server server;
    private Gson gson;

    public ParkingService() {
        this.gson = new Gson();
        server = Server.getServer();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get() {
        List<Parking> parking = server.getParking();
        return gson.toJson(parking);
    }

    @GET
    @Path("/parkingStatus")
    @Produces(MediaType.APPLICATION_JSON)
    public String isParkingOnline() {
        return Boolean.toString(server.isSmartParkingOnline());
    }
}
