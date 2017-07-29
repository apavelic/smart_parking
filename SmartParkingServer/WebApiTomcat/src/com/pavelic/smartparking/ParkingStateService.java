package com.pavelic.smartparking;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import main.java.com.pavelic.smartparking.models.State;
import main.java.com.pavelic.smartparking.server.Server;

import java.io.IOException;
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
 * Created by Andrej on 28.07.2017..
 */
@Path("/state")
public class ParkingStateService {

    private Server server;
    private Gson gson;

    public ParkingStateService() {
        this.gson = new Gson();
        server = Server.getServer();
    }

    @GET
    @Path("/{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@PathParam("from") String dateFrom, @PathParam("to") String dateTo) {

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date from = null;
        Date to = null;
        try {
            from = df.parse(dateFrom);
            to = df.parse(dateTo);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<State> parking = server.getState(from, to);
        return gson.toJson(parking);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String get() {
        List<State> parking = server.getState(null, null);
        return gson.toJson(parking);
    }
}

