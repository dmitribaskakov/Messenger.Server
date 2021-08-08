package org.home.env;

import com.alibaba.fastjson.annotation.JSONField;

public class Settings {

    final private String DefaultServerAddress = "localhost";
    @JSONField(name = "ServerAddress")
    private String serverAddress = DefaultServerAddress;
    public String getServerAddress() {
        if ((serverAddress==null) | ((serverAddress!=null) & (serverAddress.isEmpty()))) {
            serverAddress = DefaultServerAddress;
        };
        return serverAddress;
    }
    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    final private int DefaultServerPort = 19000;
    @JSONField(name = "ServerPort")
    private int serverPort = DefaultServerPort;
    public int getServerPort() {
        if (serverPort == 0) serverPort = DefaultServerPort;
        return serverPort;
    }
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    @JSONField(name = "DatabaseURI")
    final private String DefaultDatabaseURI = "jdbc:postgresql://0.0.0.0:5432/messenger";
    private String databaseURI = DefaultDatabaseURI;
    public String getDatabaseURI() {
        if ((databaseURI==null) | ((databaseURI!=null) & (databaseURI.isEmpty()))) {
            databaseURI = DefaultDatabaseURI;
        };
        return databaseURI;
    }
    public void setDatabaseURI(String databaseURI) {
        this.databaseURI = databaseURI;
    }

    @JSONField(name = "DatabaseUser")
    final private String DefaultDatabaseUser = "postgres";
    private String databaseUser = DefaultDatabaseUser;
    public String getDatabaseUser() {
        if ((databaseUser==null) | ((databaseUser!=null) & (databaseUser.isEmpty()))) {
            databaseUser = DefaultDatabaseUser;
        };
        return databaseUser;
    }
    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    @JSONField(name = "DatabasePassword")
    final private String DefaultDatabasePassword = "password";
    private String databasePassword = DefaultDatabasePassword;
    public String getDatabasePassword() {
        if ((databasePassword==null) | ((databasePassword!=null) & (databasePassword.isEmpty()))) {
            databasePassword = DefaultDatabasePassword;
        };
        return databasePassword;
    }
    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

//    Файлы конфигурации https://metanit.com/java/database/2.2.php
//    Мы можем определить все данные для подключения непосредственно в программе. Однако что если какие-то данные были изменены? В этом случае потребуется перекомпиляция приложения.
//    Иногда это не всегда удобно, например, отсутствует досуп к исходникам, или перекомпиляция займет довольно продолжительное время. В этом случае мы можем хранить настройки в файле.
//    Так, создадим в папке программы новый текстовый файл database.properties, в котором определим настройки подключения:
//    url = jdbc:mysql://localhost/store?serverTimezone=Europe/Moscow&useSSL=false
//    username = root
//    password = password
//    Загрузим эти настройки в программе:
//
//    Properties props = new Properties();
//		try(InputStream in = Files.newInputStream(Paths.get("database.properties"))){
//        props.load(in);
//    }
//    String url = props.getProperty("url");
//    String username = props.getProperty("username");
//    String password = props.getProperty("password");
}
