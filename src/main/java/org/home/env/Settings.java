package org.home.env;

import com.alibaba.fastjson.annotation.JSONField;

public class Settings {
    final private int DefaultServerPort = 19000;
    final private String DefaultServerAddress = "localhost";

    @JSONField(name = "ServerAddress")
    private String serverAddress = DefaultServerAddress;

    @JSONField(name = "ServerPort")
    private int serverPort = DefaultServerPort;

    public String getServerAddress() {
        if ((serverAddress==null) | ((serverAddress!=null) & (serverAddress.isEmpty()))) {
            serverAddress = DefaultServerAddress;
        };
        return serverAddress;
    }

    public int getServerPort() {
        if (serverPort == 0) serverPort = DefaultServerPort;
        return serverPort;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }


}
