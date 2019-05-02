package edu.monash.it.fit3077.vjak;

import java.util.UUID;

/*
A simple constants class. Given the low number of constants, we only needed one class. However, it might be useful
to add more classes as the number of constants increase and potentially group into a separate package.
 */
public class Constant {
    public static final String clientId = UUID.randomUUID().toString() + System.currentTimeMillis(); // Represents the frontend id and the server will use this as identification.
    public static final Double guiWindowHeight = 400d;
    public static final Double guiWindowWidth = 800d;
}
