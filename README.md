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
