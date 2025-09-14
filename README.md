# Route Finder

### About
Route Finder is a tool for finding the quickest and cheapest route to a destination.

### Getting Started
To run the application locally in IntelliJ

* Run `mvn clean install` to build the project
* Run `mvn compile exec:java` to start the program

These commands can be run from IntelliJ's Maven window by clicking on 'Execute Maven Goal' and entering the command. Alternatively these commands can be run by adding clicking 'Edit Configurations' in IntelliJ's run options and adding a Maven configuration with the relevant command in the 'Command line' field.

As the project's dependecies are handled by Maven, simply running the main class will result in a build failure unless build/run actions are delegated to maven by your IDE. This can be done in IntelliJ by visiting:

Settings -> Build, Execution, Deployment -> Build Tools -> Maven -> Runner

and checking the box 'Delegate IDE build/run actions to Maven'.

### Testing

The tests for this application can run using the Maven command `mvn test`.

NOTE - The test output may begin with several warnings about restricted or unsafe methods being called. This is an upstream issue caused by Maven using tools and libraries which will have stricter access rules in future Java releases. It is not caused by this project's code.