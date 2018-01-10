package otus.hibernate;

import org.hibernate.cfg.Configuration;
import otus.data.AddressDataSet;
import otus.data.PhoneDataSet;
import otus.data.UserDataSet;

import java.io.IOException;
import java.util.Properties;

public class ConfigurationLoader {

    private PropertiesLoader propertiesLoader;
    private Configuration configuration = new Configuration();

    public ConfigurationLoader( PropertiesLoader propertiesLoader) throws IOException {
        this.propertiesLoader = propertiesLoader;
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(UserDataSet.class);

        Properties properties = propertiesLoader.loadProperties();
        properties.stringPropertyNames()
                .forEach(property -> configuration.setProperty(property, properties.getProperty(property)));
    }
    public Configuration getConfiguration(){
        return configuration;
    }




}
