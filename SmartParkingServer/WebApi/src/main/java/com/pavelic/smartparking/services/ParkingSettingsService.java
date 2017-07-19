package main.java.com.pavelic.smartparking.services;

import com.google.gson.Gson;
import main.java.com.pavelic.smartparking.models.Parking;
import main.java.com.pavelic.smartparking.models.ParkingSettings;
import main.java.com.pavelic.smartparking.server.Server;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by epavean on 19.7.2017..
 */
@Path("/settings")
public class ParkingSettingsService {
    private Server server;
    private Gson gson;

    public ParkingSettingsService() {
        this.gson = new Gson();
        server = Server.getServer();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get() {
        ParkingSettings settings = server.getSettings();
        return gson.toJson(settings);
    }

    @PUT
    @Path("/{model}")
    @Produces(MediaType.APPLICATION_JSON)
    public void put(@PathParam("model") String model) {
        try {
            ParkingSettings settings = new Gson().fromJson(model, ParkingSettings.class);
            server.updateSettings(settings);
        } catch (Exception ex) {

        }
    }
}
