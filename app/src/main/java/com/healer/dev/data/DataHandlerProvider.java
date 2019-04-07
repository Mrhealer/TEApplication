package com.healer.dev.data;

public class DataHandlerProvider {

    public static DataHandler provide() {
        return AppDataHandler.getInstance();
    }

}
