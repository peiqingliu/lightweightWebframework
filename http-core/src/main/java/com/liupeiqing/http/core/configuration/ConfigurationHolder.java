package com.liupeiqing.http.core.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liupeqing
 * @date 2019/2/25 12:20
 */
public class ConfigurationHolder {

    private static Map<String,AbstractHttpConfiguration> config = new HashMap<>(8);

    /**
     *
     * 添加配置 缓存
     * @param key
     * @param configuration
     */
    public static void addConfiguration(String key,AbstractHttpConfiguration configuration){
        config.put(key,configuration);
    }

    /**
     * Get class from cache by class name
     * @param clazz
     * @return
     */
    public static AbstractHttpConfiguration getConfiguration(Class<? extends AbstractHttpConfiguration> clazz){
        return config.get(clazz.getName());
    }
}
