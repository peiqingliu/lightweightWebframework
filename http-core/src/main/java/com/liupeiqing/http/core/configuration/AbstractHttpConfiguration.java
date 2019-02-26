package com.liupeiqing.http.core.configuration;

import java.util.Properties;

/**
 * 抽象配置类
 * @author liupeqing
 * @date 2019/2/25 12:22
 */
public abstract class AbstractHttpConfiguration {

    private String propertiesName;
    private Properties properties;

    public String getPropertiesName() {
        return propertiesName;
    }

    public void setPropertiesName(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String get(String key){
        return properties.get(key) == null ? null : properties.get(key).toString();
    }

    @Override
    public String toString() {
        return "AbstractHttpConfiguration{" +
                "propertiesName='" + propertiesName + '\'' +
                ", properties=" + properties +
                '}';
    }
}
