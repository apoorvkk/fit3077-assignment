# FIT3077 Assignment 1

Apoorv Kansal (27821455) akan57@student.monash.edu <br>
Vaibhav Jaideep vjai0002@student.monash.edu

<img src="Images/application.png" />

## Descripton

This application allows practitioners to monitor cholesterol levels of their patients who they might think are at risk of a heart attack. They can select multiple patients to monitor simultaneously and their patients' cholesterol levels will update periodically on the main dashboard view (per hour). Practitioners can also load more of their patients they see in the sidebar.

Note: this application will eventually support multiple types of monitors for a patient (other than cholesterol). This will happen in assignment 2.

## Architecture

Practitioners will use a frontend GUI application (see image above) to monitor their patients. The GUI application will fetch the practitioner's patients from the FHIR service and connect to a backend service I created to receive updated events about the monitored patients (eg. their cholesterol level). The backend service polls FHIR every hour to get the relevant monitor data and then send back to connected clients (the GUI application).

Having a backend service poll instead all clients minimizes the number of requests made to the FHIR service. For example, if 1 million clients wanted to observe patient A's cholesterol level, instead of making 1 million requests to get the same data per hour, there is backend service that will make just 1 request every hour to FHIR to get the cholesterol level and then send events back to all clients via web sockets. Furthermore, the backend service will only poll for patients that are being monitored on specific measurements (eg. cholesterol). This also minimizes the requests to FHIR.

## UML

Please see the `UML` folder for class diagrams and sequence diagrams.

## Tools and Frameworks

The frontend is written in Java using [JavaFx](https://openjfx.io/).
The backend is written in Java using SpringMVC with SpringBoot.
The websocket connection uses the [STOMP protocol](https://stomp.github.io/) which has been implemented by default in [Spring MVC](https://spring.io/guides/gs/serving-web-content/).

## Setup

The following tools must be installed already:

- git
- Java
- JDK 1.8
- Maven

1. Clone the repository.

```
git clone https://git.infotech.monash.edu/fit3077-s1-2019/VJAK/project.git
```

2. Install the backend dependencies.

```
cd project/BackEnd/server
mvn install
```

3. Compile the backend.

```
mvn compile
```

4. Run the backend server. Note: Please have port 8080 free.

```
mvn spring-boot:run
```

5. Open a new command line and locate the cloned project. Install the frontend dependencies.

```
cd project/FrontEnd/DashboardMonitor/
mvn install
```

6. Compile the frontend.

```
mvn compile
```

7. Run the GUI app.

```
mvn exec:java -Dexec.mainClass=edu.monash.it.fit3077.vjak.DashboardMonitorApplication
```

## Assumptions

- The practitioner is hardcoded. The application does not allow changing practitioners as that was out of scope for the project. You must go into the driver class of the frontend and change the practioner id and then compile and run the program to use a different practitioner.
- A Practioner class is not needed given there is only one practitioner controlling the whole system. If the application eventually supports logging on of different practitioners, it will make sense to introduce the class at that time.
- All measurements (in this case, it is just cholesterol but will eventuall support other measurements) will have the actual number and an associated unit.
- The `Patient` resource allows patients to have multiple names. I have selected the first one to show as the official name.
- When searching for the patient's latest measurement value (eg. cholesterol level), I am searching the `Observation` resource on patient id and the measurement code and then sorted by date in descending order. Based on this search criteria, I simply take the first observation as the latest measurement recorded for that patient. Note that sorting by date uses the `issued` property inside the `Observation` resource.
- The cholesterol property is defined as "Total Cholesterol" and has the measurement code "2093-3".

## Contributions

Apoorv Kansal

- Architected the whole system.
- Designed and developed the frontend GUI application.
- Designed and developed the backend service.
- Documented code.
- Review code and architecture.
- Documented readme file.
- Made project plan.
- Reviewed criteria.
- Defined a list of assumptions.

Vaibhav Jaideep

- Copied and added an example application from the Internet to the git repository which was not needed and removed.

**Note:** <br>
I (Apoorv Kansal) completed the whole project on my own due to the other assignment partner not putting in enough effort and initiative. The whole process was very stresful where I stayed up numerous nights to finish the whole project myself.
