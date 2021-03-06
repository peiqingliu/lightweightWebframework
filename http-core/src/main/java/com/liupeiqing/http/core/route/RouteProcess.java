package com.liupeiqing.http.core.route;

import com.liupeiqing.http.core.bean.CicadaBeanManager;
import com.liupeiqing.http.core.context.HttpContext;
import com.liupeiqing.http.core.enums.StatusEnum;
import com.liupeiqing.http.core.exception.HttpException;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author liupeqing
 * @date 2019/2/26 10:00
 */
public final class RouteProcess {

    private volatile static RouteProcess routeProcess;

    private final CicadaBeanManager cicadaBeanManager = CicadaBeanManager.getInstance();

    public static RouteProcess getInstance() {
        if (routeProcess == null) {
            synchronized (RouteProcess.class) {
                if (routeProcess == null) {
                    routeProcess = new RouteProcess();
                }
            }
        }
        return routeProcess;
    }

    /**
     * method invoke方法。使用反射 执行 该实例上的方法，第一个参数 是实例对象，第二个参数为 实例对象调用的该方法所需要的参数
     *
     * @param method
     * @param queryStringDecoder
     * @throws Exception
     */
    public void invoke(Method method,QueryStringDecoder queryStringDecoder) throws Exception {
        if (null == method){
            return;
        }
        Object[] object = parseRouteParameter(method, queryStringDecoder);
        Object bean = cicadaBeanManager.getBean(method.getDeclaringClass().getName());
        if (object == null){
            method.invoke(bean) ;
        }else {
            method.invoke(bean, object);
        }
    }

    private Object[] parseRouteParameter(Method method, QueryStringDecoder queryStringDecoder) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        Class<?>[] parameterTypes = method.getParameterTypes();

        if (parameterTypes.length == 0){
            return null;
        }

        if (parameterTypes.length > 2){
            throw new HttpException(StatusEnum.ILLEGAL_PARAMETER);
        }

        Object[] instances = new Object[parameterTypes.length];

        for (int i = 0; i< instances.length; i++){
            if (parameterTypes[i] == HttpContext.class){
                instances[i] = HttpContext.getContext();
            }else {
                Class<?> parameterType = parameterTypes[i];
                Object instance = parameterType.newInstance();
                Map<String,List<String>> parameters = queryStringDecoder.parameters();
                for (Map.Entry<String,List<String>> param : parameters.entrySet()){
                    Field field = parameterType.getDeclaredField(param.getKey());
                    field.setAccessible(true);
                    field.set(instance,parseFieldValue(field, param.getValue().get(0)));
                }
                instances[i] = instance ;
            }
        }

        return instances;
    }

    private Object parseFieldValue(Field field, String value){
        if (null == value){
            return null;
        }
        Class<?> type = field.getType();
        if ("".equals(value)){
            boolean base = type.equals(int.class) || type.equals(double.class) ||
                    type.equals(short.class) || type.equals(long.class) ||
                    type.equals(byte.class) || type.equals(float.class);
            if (base){
                return 0;
            }
        }
        if (type.equals(int.class) || type.equals(Integer.class)) {
            return Integer.parseInt(value);
        } else if (type.equals(String.class)) {
            return value;
        } else if (type.equals(Double.class) || type.equals(double.class)) {
            return Double.parseDouble(value);
        } else if (type.equals(Float.class) || type.equals(float.class)) {
            return Float.parseFloat(value);
        } else if (type.equals(Long.class) || type.equals(long.class)) {
            return Long.parseLong(value);
        } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (type.equals(Short.class) || type.equals(short.class)) {
            return Short.parseShort(value);
        } else if (type.equals(Byte.class) || type.equals(byte.class)) {
            return Byte.parseByte(value);
        } else if (type.equals(BigDecimal.class)) {
            return new BigDecimal(value);
        }
        return null;
    }
}
