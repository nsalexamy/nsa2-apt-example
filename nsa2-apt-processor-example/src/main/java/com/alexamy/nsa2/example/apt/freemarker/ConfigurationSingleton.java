package com.alexamy.nsa2.example.apt.freemarker;

import freemarker.template.Configuration;

public class ConfigurationSingleton {

//    private static ConfigurationSingleton instance;

    private static Configuration configuration;

    private ConfigurationSingleton() {
    }

    public static Configuration getInstance() {
        if (configuration == null) {
            configuration = new Configuration(Configuration.VERSION_2_3_33);
            configuration.setClassForTemplateLoading(ConfigurationSingleton.class, "/freemarker/templates");
        }
        return configuration;
    }

}
