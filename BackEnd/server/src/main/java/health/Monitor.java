package health;

public class Monitor {
    enum Measurement {
        CHOLESTEROL("cholesterol");

        String measureType;

        Measurement(String type) {
            measureType = type;
        }
    }

    Measurement measurement;
    String patientId;
}