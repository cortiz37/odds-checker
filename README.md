## odds-checker

Command line application that will display real time odds for who will win a specific sport match

### requirements

- JDK: Java 8 (min)

### build

- unix: `./gradlew build`
- windows: `gradlew.bat build`

### run

Running the application, traditional way:

- `java -jar build/libs/odds-checker.jar 1007760948`

Running the application, SpringBoot (bootRun) gradle task:

- unix: `./gradlew bootRun --args=1007760948`
- windows: `gradlew.bat bootRun --args=1007760948`

Output example:
```
Event: Coventry City - Peterborough United
[2021-09-24 14:36:28] | 1:     1.76 | X:     3.00 | 2:     6.10 |
```

### config

All configuration properties are in `application.properties` file

Example (to change the date format):
- `java -jar build/libs/odds-checker.jar 1007760948 --data.date.format=yyyy`

Output example:
```
Event: Coventry City - Peterborough United
[2021] | 1:     1.76 | X:     3.00 | 2:     6.10 |
[2021] | 1:     1.74 | X:     3.05 | 2:     6.25 |
```