# Messenger.Server

##Команды для докера:
Сборка docker image
```
docker build -t messenger-server:1.0 .
```
Запуск docker image
```
docker run --rm --name server --publish 19000:19000 --env TZ=Asia/Novosibirsk messenger-server:1.0
```

##Oracle JDBC drivers
1. Visit Oracle database website and download it. https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html
2. Upload or install the downloaded ojdbc.jar into the Maven local repository.
```
mvn install:install-file -Dfile=c:/Projects/Messenger.Java/Messenger.Server/lib/ojdbc11.jar -DgroupId=com.oracle.database.jdbc -DartifactId=ojdbc11 -Dversion=21.1.0.0 -Dpackaging=jar
```
3. Now, we can define the Oracle JDBC driver dependency like this pom.xml:
```
    <dependency>
        <groupId>com.oracle.database.jdbc</groupId>
        <artifactId>ojdbc11</artifactId>
        <version>21.1.0.0</version>
        <!-- <scope>system</scope>  -->
        <!-- <systemPath>${project.basedir}/lib/ojdbc11.jar</systemPath>  -->
    </dependency>
```
4. install PostgreSQL JDBC Driver
```
mvn install -DgroupId=org.postgresql -DartifactId=postgresql -Dversion=42.2.18.jre7
```
```
    <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
      <version>42.2.18.jre7</version>
    </dependency>
```

##PostgreSQL with Docker

https://www.youtube.com/watch?v=A8dErdDMqb0


1. Create a Postgres docker container
```
    docker run --name db.messenger.server -d --rm -p 5432:5432 -v c:/Projects/Messenger.Java/Messenger.Server/db/data:/var/lib/postgresql/data -e TZ=Asia/Novosibirsk -e POSTGRES_PASSWORD=password postgres
    docker ps -a
    docker stop db.messenger.server
```
2. Connect and run some queries
```
    docker exec -it db.messenger.server psql -U postgres
    CREATE DATABASE messenger;
    \c messenger;
    CREATE TABLE users (
    Id SERIAL PRIMARY KEY,
    Name CHARACTER VARYING(30) UNIQUE NOT NULL);
    INSERT INTO users (Name) VALUES ('One');
    \dt;
    select * from users;
    \q
```
