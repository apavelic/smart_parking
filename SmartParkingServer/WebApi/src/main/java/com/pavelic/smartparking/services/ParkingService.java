package main.java.com.pavelic.smartparking.services;
import com.google.gson.Gson;
import main.java.com.pavelic.smartparking.models.Parking;
import main.java.com.pavelic.smartparking.models.State;
import main.java.com.pavelic.smartparking.server.Server;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

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
}
