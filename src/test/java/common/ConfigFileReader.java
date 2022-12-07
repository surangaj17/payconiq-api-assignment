package common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

    private Properties properties;
    private final String configPropertyFilePath = "src/test/resources/configs/Configuration.properties";

    String   propertyFilePath ;

    public ConfigFileReader(String propertyFileType) {
        BufferedReader reader;

        if (propertyFileType == "Config") {
            propertyFilePath = configPropertyFilePath;
        }

        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }


    public String getBaseUrl() {
        String url = properties.getProperty("url");
        if (url != null) return url;
        else throw new RuntimeException("url not specified in the Configuration.properties file.");
    }

    public String getTestDataValues(String key) {
        String value = properties.getProperty(key);
        if (value != null) return value;
        else throw new RuntimeException("Data specified in the TestData.properties file.");
    }

    public int getIntTestDataValues(String key) {
        int value = Integer.parseInt(properties.getProperty(key));
        return value;

    }

    public boolean getBooleanTestDataValues(String key) {
        boolean value = Boolean.parseBoolean(properties.getProperty(key));
        return value;

    }

}
