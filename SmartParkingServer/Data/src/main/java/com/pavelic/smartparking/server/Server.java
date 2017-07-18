package main.java.com.pavelic.smartparking.server;

        import com.mongodb.BasicDBObject;
        import com.mongodb.MongoClient;
        import com.mongodb.client.MongoCollection;
        import com.mongodb.client.MongoCursor;
        import com.mongodb.client.MongoDatabase;
        import main.java.com.pavelic.smartparking.models.*;
        import main.java.com.pavelic.smartparking.threads.ClientThread;
        import org.bson.Document;

        import java.io.IOException;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.text.ParseException;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;
        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;
        import java.util.logging.Level;
        import java.util.logging.Logger;


/**
 * Created by Andrej on 27.2.2017..
 */
public class Server {

    private static final String DATABASE = "SmartParking";
    private static final String HOST = "127.0.0.1";
    private static Server server;

    public static final int PORT = 1234;

    private MongoDatabase db;
    private MongoClient mongo;

    public Server() {
        try {
            connectToDatabase();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static Server getServer() {
        if (server == null) {
            server = new Server();
        }
        return server;
    }

    public static void start() {
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

        try(ServerSocket socket = new ServerSocket(PORT)) {

            System.out.println("SERVER STARTED ON PORT: " + PORT);
            socket.accept();
            System.out.println("Smart parking is ONLINE");

            while(true) {
                try {
                    Socket client = socket.accept();
                    ExecutorService es = Executors.newFixedThreadPool(4);
                    es.execute(new ClientThread(client, server));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void connectToDatabase() throws ParseException {
        mongo = new MongoClient(HOST);
        MongoCursor<String> dbsCursor = mongo.listDatabaseNames().iterator();

        boolean databaseExists = databaseExists(dbsCursor);
        dbsCursor.close();

        db = mongo.getDatabase(DATABASE);

        if (databaseExists == false) {
            seed();
        }
    }

    private void seed() {
        MongoCollection<Document> parking = db.getCollection("parking");
        parking.insertMany(getInitialParking());
    }

    private List<Document> getInitialParking() {
        List<Document> seedParking = new ArrayList<Document>();

        seedParking.add(new Document("id", 1)
                .append("location", "Ilica 242")
                .append("state", "FREE"));

        seedParking.add(new Document("id", 2)
                .append("location", "Ilica 242")
                .append("state", "FREE"));

        seedParking.add(new Document("id", 3)
                .append("location", "Ilica 242")
                .append("state", "FREE"));

        seedParking.add(new Document("id", 4)
                .append("location", "Ilica 242")
                .append("state", "FREE"));

        return seedParking;
    }

    private boolean databaseExists(MongoCursor<String> cursor) {
        while (cursor.hasNext()) {
            String db = cursor.next();
            if (db.equals(DATABASE)) {
                return true;
            }
        }
        return false;
    }

    public void update(int parkingId, String state) {
        MongoCollection<Document> parking = db.getCollection("parking");
        Document updateQuery = new Document("id", parkingId);
        parking.updateOne(updateQuery, new Document("$set", new Document("state", state)));
    }

    public void insertState(int parkingId, String state) throws ParseException {
        MongoCollection<Document> parkingState = db.getCollection("state");

        Document doc = new Document("parkingId", parkingId);
        doc.append("state", state);
        doc.append("date", new Date());

        parkingState.insertOne(doc);
    }

    public List<Parking> getParking() {
        MongoCollection<Document>  model = db.getCollection("parking");

        List<Parking> parking = new ArrayList<Parking>();

        MongoCursor<Document> cursor = model.find().iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            int id = doc.getInteger("id");
            String state =doc.getString("state");
            String location = doc.getString("location");

            parking.add(new Parking(id, state, location));
        }

        return parking;
    }

    public List<State> getState(Date from, Date to) {
        MongoCollection<Document>  model = db.getCollection("state");

        List<State> state = new ArrayList<>();

        BasicDBObject query;
        MongoCursor<Document> cursor;

        if(from != null || to != null) {
            query = new BasicDBObject("date", new BasicDBObject("$gt", from).append("$lt", to));
            cursor = model.find(query).iterator();
        } else {
            cursor = model.find().iterator();
        }


        while (cursor.hasNext()) {
            Document doc = cursor.next();
            int id = doc.getInteger("parkingId");
            String parkingState = doc.getString("state");
            Date date = doc.getDate("date");

            state.add(new State(id, parkingState, date));
        }

        return state;
    }
}
