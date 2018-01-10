package otus.hibernate;

import com.google.common.io.Resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class PropertiesLoader {

    private static final String CONFIG_PROPERTIES = "config.properties";

    public Properties loadProperties() throws IOException {
        URL resource = Resources.getResource(CONFIG_PROPERTIES);

        FileInputStream fileInputStream = new FileInputStream(resource.getPath());
        Properties properties = new Properties();
        properties.load(fileInputStream);

        return properties;
    }

}
