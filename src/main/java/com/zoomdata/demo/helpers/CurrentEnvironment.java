package com.zoomdata.demo.helpers;

import static com.zoomdata.demo.helpers.EnvironmentPropertiesHandler.DEFAULT_USER_PASSWORD;
import static com.zoomdata.demo.helpers.EnvironmentPropertiesHandler.getInstance;

public class CurrentEnvironment {

    public static final String ENVIRONMENT = System.getProperty("testEnv") == null ? "default" : System.getProperty("testEnv");
    public static final String SERVER = getInstance().getEnvProperty(EnvironmentPropertiesHandler.SERVER);
    public static final String BASE_URL = getInstance().getProperty(SERVER + "." + EnvironmentPropertiesHandler.BASE_URL);
    public static final String DEFAULT_PASSWORD = getInstance().getProperty(SERVER + "." + DEFAULT_USER_PASSWORD);


    static {
        if (!SERVER.equalsIgnoreCase("qa") && !SERVER.equalsIgnoreCase("dev")) {
            throw new IllegalArgumentException("Please check Server property "
                    + ENVIRONMENT + "." + SERVER + ", as it should be dev or qa");
        }

    }


}
