package com.pavelic.smartparking;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import main.java.com.pavelic.smartparking.models.Parking;
import main.java.com.pavelic.smartparking.server.Server;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by Andrej on 28.07.2017..
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
