# Journey Planner

## About
Journey Planner is a tool for finding the quickest and cheapest route to a destination.

## Requirements

- Java Development Kit (JDK) 17
- Maven 3.6 or higher (for building and running the project)
- IntelliJ IDEA or another compatible IDE (optional, for development)

Ensure your `JAVA_HOME` environment variable points to JDK 17.

## Getting Started
To run the application locally in IntelliJ

* Run `mvn clean install` to build the project
* Run `mvn compile exec:java` to start the program

These commands can be run from IntelliJ's Maven window by clicking on 'Execute Maven Goal' and entering the command. Alternatively these commands can be run by adding clicking 'Edit Configurations' in IntelliJ's run options and adding a Maven configuration with the relevant command in the 'Command line' field.

As the project's dependencies are handled by Maven, simply running the main class will result in a build failure unless build/run actions are delegated to Maven by your IDE. This can be done in IntelliJ via:

Settings -> Build, Execution, Deployment -> Build Tools -> Maven -> Runner

and checking the box 'Delegate IDE build/run actions to Maven'.

## Testing

### Running Tests With IntelliJ

The tests for this application can be found in `src/test/java`. To run the whole suite of tests for the application right click on the `src/test/java` directory in IntelliJ's Project window and select `Run 'Tests in 'java''`.

Each Test class and method can be run individually by clicking IntelliJ's run button next to the class or method in the test file.

### Running Tests With Maven

The tests for this application can be run using the Maven command `mvn test`.

### Sample Data Proof

Included in the test suite is the file SampleDataProof, which simply outputs to the terminal the results of processing the sample data as per the requirements. 