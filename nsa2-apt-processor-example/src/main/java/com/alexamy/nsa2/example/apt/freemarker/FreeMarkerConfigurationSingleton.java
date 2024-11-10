package com.alexamy.nsa2.example.apt.freemarker;

import freemarker.template.Configuration;

public class FreeMarkerConfigurationSingleton {

    private static Configuration configuration;

    private FreeMarkerConfigurationSingleton() {
    }

    public static Configuration getInstance() {
        if (configuration == null) {
            configuration = new Configuration(Configuration.VERSION_2_3_33);
            configuration.setClassForTemplateLoading(FreeMarkerConfigurationSingleton.class, "/freemarker/templates");
        }
        return configuration;
    }

}
