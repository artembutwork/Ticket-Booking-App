# Ticket Booking App

## application.properties
To set up connection with Microsoft SQL Server database you have to enter an url to data source, username and password in the appropriate places

```
...
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
...
```

##  Build and run
To build and run the application - use script `run.bat`

##  Main business scenario
To run whole use case calling respective endpoints and see requests and responses in action - use script `main.bat`

##  Errors handling
To see how an application handles client requests with invalid data - use script `errors.bat`

##  Additional assumptions:
- Seats given in point 4. of the scenario should be sorted by row number and seat number