package app;

import service.EventService;
import service.OrderService;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;

import javax.swing.*;
import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    public static final String DB_CONNECTION = "jdbc:mysql://localgroupon.cbvpzcsrae7t.eu-central-1.rds.amazonaws.com:3316/GroupOn?autoReconnect=true&useSSL=false";
    public static final String DB_USERNAME = "dth";
    public static final String DB_PASSWORD = "dth920312";

    public static void main(String[] args) throws IOException {
        // Create configuration object for webserver instance
        ResourceConfig config = new ResourceConfig();
        // Register REST-resources (i.e. service classes) with the webserver
        config.register(ServerExceptionMapper.class);
        config.register(EventService.class);
        config.register(OrderService.class);
        // add further REST-resources like this:  config.register(Xyz.class);

        // Create webserver instance and start it
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8083), 0);
        HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(config, HttpHandler.class);
        // Context is part of the URI directly after  http://domain.tld:port/
        server.createContext("/restapi", handler);
        server.start();

        // Show dialogue in order to prevent premature ending of server(s)
//        JOptionPane.showMessageDialog(null, "Stop server...");
//        server.stop(0);
        System.out.println("Server started");
    }
}
