package otus.hibernate;

import org.hibernate.cfg.Configuration;
import otus.data.AddressDataSet;
import otus.data.PhoneDataSet;
import otus.data.UserDataSet;

import java.io.IOException;
import java.util.Properties;

public class ConfigurationLoader {

    private Configuration configuration = new Configuration();

    public ConfigurationLoader() {
        configuration.configure("hibernate.cfg.xml");
    }
    public Configuration getConfiguration(){
        return configuration;
    }
}
