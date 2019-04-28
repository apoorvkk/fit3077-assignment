package edu.monash.it.fit3077.vjak.model.health.tracker;


import edu.monash.it.fit3077.vjak.Constant;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

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
                this.url = "https://postb.in/DS7ctmz6";
            } else {
                this.url = "https://postb.in/DS7ctmz6";
            }
        }
        @Override
        public void run() {
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(this.url);

            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            params.add(new BasicNameValuePair("patientId", MeasurementTracker.this.patientId));
            params.add(new BasicNameValuePair("measurementType", MeasurementTracker.this.healthMeasurement));
            params.add(new BasicNameValuePair("clientId", Constant.clientId));
            try {
                httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

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
