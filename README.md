# TranslateGame
A translate game
have fun and enjoy:)

## Preparation

* `ln -s . src`
* prepare `material/output.mp3`

## Build

```
javac -cp "lib/*" serialize/*.java translategame/*.java single/*.java Server/*.java
```

## Run

Server

```
java -cp "lib/*:." Server.Server
```

Client

```
java -cp "lib/*:." translategame.TranslateGame
```
