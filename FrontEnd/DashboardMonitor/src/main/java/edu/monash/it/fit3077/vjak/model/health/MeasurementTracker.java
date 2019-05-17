package edu.monash.it.fit3077.vjak.model.health;


import edu.monash.it.fit3077.vjak.Constant;
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
public class MeasurementTracker {
    private final String patientId;
    private final String healthMeasurement;

    public MeasurementTracker(String patientId, String healthMeasurement) {
        this.patientId = patientId;
        this.healthMeasurement = healthMeasurement;
    }

    /*
    This class is responsbile to make the necessary registration/deregistration requests. We use a separate thread
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

            JSONObject payload = new JSONObject();
            payload.put("patientId", MeasurementTracker.this.patientId);
            payload.put("measurementType", MeasurementTracker.this.healthMeasurement);
            payload.put("clientId", Constant.clientId);
            httppost.setEntity(new StringEntity(payload.toString(), ContentType.APPLICATION_JSON));

            try {
                httpclient.execute(httppost);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void register() {
        Thread patientLoaderThread = new Thread(new RegistrationRunnable(true));
        patientLoaderThread.start();
    }

    public void deregister() {
        Thread patientLoaderThread = new Thread(new RegistrationRunnable(false));
        patientLoaderThread.start();
    }
}
