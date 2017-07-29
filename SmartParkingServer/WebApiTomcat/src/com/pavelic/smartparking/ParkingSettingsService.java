package com.pavelic.smartparking;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import main.java.com.pavelic.smartparking.models.ParkingSettings;
import main.java.com.pavelic.smartparking.server.Server;

import java.io.IOException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Andrej on 28.07.2017..
 */
// The Java class will be hosted at the URI path "/helloworld"
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