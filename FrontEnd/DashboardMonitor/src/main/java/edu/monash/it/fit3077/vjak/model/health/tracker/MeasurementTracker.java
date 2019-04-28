package edu.monash.it.fit3077.vjak.model.health.tracker;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class MeasurementTracker {
    private String patientId;
    private String healthMeasurement;
    private String registrationId;

    public MeasurementTracker(String patientId, String healthMeasurement) {
        this.patientId = patientId;
        this.healthMeasurement = healthMeasurement;
    }

    public void register() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MeasurementRegistrationModel> request = new HttpEntity<>(new MeasurementRegistrationModel(this.patientId, this.healthMeasurement));
        ResponseEntity<MeasurementRegistrationModel> response = restTemplate
                .exchange("http://localhost:8080/Monitor", HttpMethod.POST, request, MeasurementRegistrationModel.class);

        MeasurementRegistrationModel mr = response.getBody();

        this.registrationId = mr.getRegistrationId();
    }

    public void deregister() {
        final String url = "http://localhost:8080/Monitor/{id}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", this.registrationId);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete (url,  params);
    }
}
