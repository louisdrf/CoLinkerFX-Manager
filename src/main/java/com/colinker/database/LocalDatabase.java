package com.colinker.database;

import org.h2.tools.Server;

import java.sql.SQLException;

public class LocalDatabase {

    private final String serverPort;
    private Server tcpServer;

    public LocalDatabase(String serverPort) {
        this.serverPort = serverPort;
    }

    public void start() throws SQLException {
        this.tcpServer = Server.createTcpServer("-tcpPort", this.serverPort, "-tcpAllowOthers");
        this.tcpServer.start();
        System.out.println("Local database running : http://localhost:" + this.serverPort + "/h2-console");
    }

    public void stop() {
        if (this.tcpServer != null) {
            this.tcpServer.stop();
        }
    }
}
