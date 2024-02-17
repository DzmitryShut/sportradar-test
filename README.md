# sportradar-test

## Launch

Run tests with `./gradlew test`, requires `java 21`.

## Notes

I decided to write code using classic pattern ~~Controller~~-Service-Repository and designed **Scoreboard** as **Service**. But code can be written in more OOP and domain way by renaming **ScoreboardService** to **Scoreboard** and moving responsibility of handling **Game** data to it.
