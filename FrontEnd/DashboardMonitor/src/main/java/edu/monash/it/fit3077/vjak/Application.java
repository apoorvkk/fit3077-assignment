package edu.monash.it.fit3077.vjak;

import edu.monash.it.fit3077.vjak.api.hapi.HapiPatientLoader;
import edu.monash.it.fit3077.vjak.model.Patient;

import java.util.ArrayList;

public class Application {

    public static void main(String[] args) {
        HapiPatientLoader h = new HapiPatientLoader("1");
        ArrayList<Patient> ps = h.loadPatients();
    }
}