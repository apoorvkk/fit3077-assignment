Result: 100% for both stages

# FIT3077 Assignment 2 (Both Stages Included)

Note: **Assignment 2 Stage 1** was completed by Apoorv Kansal alone due to a previous partner not contributing at all. As a result, Quang Ly joined the team and replaced the old partner for **Assignment 2 Stage 2**. This work extends on Apoorv Kansal's **Assignment 2 Stage 1**.

Apoorv Kansal (27821455) akan57@student.monash.edu <br>
Quang Ly (28688856) qlyy0001@student.monash.edu

## Stage 1 Application

![Application](img/application.png)

## Stage 2 Application

![Application](img/application_stage_2.gif)

## Descripton

This application allows practitioners to monitor various health measurements (cholesterol, tobacco use, systolic blood pressure, diastolic blood pressure and **oral temperature**) of their patients who they might think are at risk of a heart attack. They can select multiple patients to monitor simultaneously and their patients' health statuses will update periodically on the main dashboard view. All health measurements update per hour except for systolic blood pressure and diastolic blood pressure which update every 10 seconds. We force update every 10 seconds for blood pressure for demonstration purposes that the graph does update (otherwise, we would have to wait at least 1 hour for the graph to update with a new data point and this is not feasible for the interview). Practitioners can also load more of their patients they see in the sidebar.

### Bonus

As an extension for stage 2, we not only created additional monitors for blood pressure (systolic and diastolic) and tobacco use, we also added oral temperature as another monitor. This bonus monitor is very similar to cholesterol and tobacco use monitors. Oral temperatures update every hour.

## Architecture

Practitioners will use a frontend GUI application (see image above) to monitor their patients. The GUI application will fetch the practitioner's patients from the FHIR service and connect to a backend service we created to receive updated events about the monitored patients (eg. cholesterol level, tobacco use etc.). The backend service polls FHIR every hour to get the relevant monitor data and then send the data back to connected clients (the GUI application). As mentioned above, the backend service polls FHIR every 10 seconds for the blood presure health measurements.

## Design Rationale

Please see the `DesignRationale.pdf` in the root folder.

## UML

Please see the `UML` folder for class diagrams and sequence diagrams.

## Tools and Frameworks

The frontend uses Java and [JavaFx](https://openjfx.io/).
The backend uses Java and SpringMVC with SpringBoot.
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
mvn exec:java -Dexec.mainClass=edu.monash.it.fit3077.akql.DashboardMonitorApplication
```

## Assumptions

- The practitioner is hardcoded. The application does not allow changing practitioners as that was out of scope for the project. You must go into the driver class of the frontend and change the practioner id and then compile and run the program to use a different practitioner.
- A Practioner class is not needed given there is only one practitioner controlling the whole system. If the application eventually supports logging on of different practitioners, it will make sense to introduce the class at that time.
- All measurements (in this case, it is just cholesterol but will eventuall support other measurements) will have the actual number and an associated unit.
- The `Patient` resource allows patients to have multiple names. I have selected the first one to show as the official name.
- When searching for the patient's latest measurement value (eg. cholesterol level), I am searching the `Observation` resource on patient id and the measurement code and then sorted by date in descending order. Based on this search criteria, I simply take the first observation as the latest measurement recorded for that patient. Note that sorting by date uses the `issued` property inside the `Observation` resource.
- The cholesterol property is defined as "Total Cholesterol" and has the measurement code "2093-3".
- Error handling (especially fail gracefully steps) is out of scope for this project. The majority of cases will simply log the error and shut the application down. However, the design can easily cater for error handling in future.

- To manage blood pressure data, the application will simply fetch the latest observation value for a particular blood pressure type every 10 seconds. The GUI app will receive these values every 10 seconds and add a new data point to the relevant graph. If the practitioner decides to stop monitoring this health measurement for a particular patient, we discard the history of blood pressure values shown on the graph. The next time the practioner decides to monitor the same measurement for the same patient, the GUI app will show a new graph and not restore the previously discarded blood pressure values. It will show blood pressure values polled from the time the practioner starts monitoring again.
- To avoid cluttered points on a graph over time, we show only the 10 last polled events for the blood pressure graphs. This can be adjusted in the future or even removed.
- We are polling every 10 seconds for blood pressure just for interview purposes.
- We do not support changing timeframes in the application as it is out of scope. The system can be extended to support this feature in future.
- The unit will always be the same for either blood pressure type.
- In the frontend, we kept a separate interface for blood pressure that holds a list of values even though we don’t access the full list. We only retrieve the latest value but in future, we might want to access the whole list so we kept it for the duration of the model's life (i.e until practitioner turns off the monitor).
- Alerts will show if systolic > 180 OR diastolic > 120. It just needs to be one of them. We show the alert message below each graph.
- Due to complexity of the class diagrams, we have decided to simplify it for readability purposes. For instance, we have omitted external packages from the diagram but have references in the application code.
- "-" means private or protected in our class diagram whereas "+" means public.
- We have omitted majority of classes from external packages (eg. JavaFX, SpringMVC and apache http) from the class diagrams for readability purposes.
