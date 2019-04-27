Cross-Ride is a ride-sharing application developed by a startup company. 
Cross-Ride allows its users to register as drivers and/or riders. Registered drivers and riders advertise their usual travel schedule on the application. At the end of shared ride driver sends a request to the server with driver id, rider id, the start time of shared ride, the end time of shared ride and the distance covered in kilometers.
    

- Cross-Ride should only accept the data from registered drivers and riders only. 
- The driver is not allowed to add a ride with end time less than or equal to start time.
- The driver can enter shared rides with overlapping entries. Like 1 shared ride from 2018-08-24 09:00 to 2018-08-24 10:00 and another ride with start time 2018-08-24 09:30 to 2018-08-24 10:10.
    
    
Prerequisites:
    Any IDE
    GIT
    Java 8
    MySQL 5.6+


Development Environment:
  MySQL:
    Cross-Ride applications require MySQL database to store its data. Make sure to update application.properties with spring.datasource.URL(change hostname only), spring.datasource.username, and  spring.datasource.password. You are free to choose MySQL service in a cloud or installed on the local machine or MySQL container.
    
    The Cross-Ride application uses liquibase for Database changes. In case you need to update the Database, please create a new changeset file in resources/db.changelog folder and include the newly created file in db.changelog-master.xml
    For more details on liquibase follow https://www.liquibase.org/documentation/changeset.html 

  Cross-Ride Application:
    To start the application run CrossRideApplication.java main method from your IDE.
