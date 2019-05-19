package edu.monash.it.fit3077.akql.model.health;


import edu.monash.it.fit3077.akql.Constant;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.IOException;

/*
This class is responsible for registering and deregistering the current client to the backend server
so it can receive monitor events via sockets. The registration/deregistration uses REST endpoint defined in the backend.
 */
class MeasurementTracker {
    private final String patientId;
    private final String healthMeasurement;

    /**
     * Setup the instance variables needed for registration and deregistration of monitors.
     * @param patientId: the patient id.
     * @param healthMeasurement: the health measurement of interest (eg. Cholesterol).
     */
    MeasurementTracker(String patientId, String healthMeasurement) {
        this.patientId = patientId;
        this.healthMeasurement = healthMeasurement;
    }

    /*
    This class is responsible to make the necessary registration/deregistration requests. We use a separate thread
    so we do not hog the main thread and freeze up the GUI application.
     */
    class RegistrationRunnable implements Runnable {
        private final String url;

        RegistrationRunnable(boolean isRegistering) {
            if (isRegistering) {
                this.url = "http://localhost:8080/MonitorRegister";
            } else {
                this.url = "http://localhost:8080/MonitorDeregister";
            }
        }
        @Override
        public void run() {
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(this.url);

            // Setup body payload.
            JSONObject payload = new JSONObject();
            payload.put("patientId", MeasurementTracker.this.patientId);
            payload.put("measurementType", MeasurementTracker.this.healthMeasurement);
            payload.put("clientId", Constant.clientId);
            httppost.setEntity(new StringEntity(payload.toString(), ContentType.APPLICATION_JSON));

            try {
                httpclient.execute(httppost); // Make request.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Used to tell backend service to register a health measurement monitor for a particular patient so that it will send events
     * about it.
     */
    void register() {
        Thread patientLoaderThread = new Thread(new RegistrationRunnable(true));
        patientLoaderThread.start();
    }

    /**
     * Used to tell backend service to stop sending events about a particular patient for particular health measurement.
     */
    void deregister() {
        Thread patientLoaderThread = new Thread(new RegistrationRunnable(false));
        patientLoaderThread.start();
    }
}
