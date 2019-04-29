package edu.monash.it.fit3077.vjak.model.health.tracker;


import edu.monash.it.fit3077.vjak.Constant;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MeasurementTracker {
    private String patientId;
    private String healthMeasurement;

    public MeasurementTracker(String patientId, String healthMeasurement) {
        this.patientId = patientId;
        this.healthMeasurement = healthMeasurement;
    }

    public class RegistrationRunnable implements Runnable {
        private String url;

        public RegistrationRunnable(boolean isRegistering) {
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
