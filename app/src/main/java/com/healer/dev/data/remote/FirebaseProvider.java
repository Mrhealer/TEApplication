package com.healer.dev.data.remote;

public class FirebaseProvider {

    public static FirebaseHandler provide() {
        return new FirebaseHandlerImpl();
    }

}
