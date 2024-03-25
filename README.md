# LandParcel

The LandParcel should be imported into an IDE which has java version 21 installed.
The package can be run by executing the command "mvn exec:java".
This will automatically launch an Apache Tomcat server which will host the application on http://localhost:8080.
Once run an in-memory H2 database will be created accessible at http://localhost:8080/h2-console/test.do the credentials required can be found and changed in the pom.xml file.
The SQL script located at src/main/resources/data.sql will be run automatically that will create a table to store the land parcels and populate it with two dummy entries.
API calls can be send to the send using POSTMAN, the documentation located at target\site\apidocs\landclan\com\landparcel\controller\LandParcelController.html can be opened in a brower and will provide details on what in required by each individual API endpoint.
All unit tests can be run by using the command "mvn test", results can be seen via the terminal or in target\surefire-reports.

The index for the documentation can be found in /target/site/apidocs/index.html
