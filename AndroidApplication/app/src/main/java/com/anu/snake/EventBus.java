package com.anu.snake;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
    private volatile static EventBus instance;
    private Map<Class<?>, List<Subscription>> map;

    private EventBus() {
        map = new HashMap<>();
    }
    public static EventBus getDefault() {
        if (instance == null) {
            synchronized (EventBus.class) {
                if (instance == null) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Subscribe {}

    public void register(Object subscriber) {
        Class<?> clazz = subscriber.getClass();
        //这里其实可能有NoClassDefFoundError，原版在捕获块里用的是getMethods()
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(Subscribe.class)) {
                Subscribe s = m.getAnnotation(Subscribe.class);
                //原版在这区分了不同参数列表的情况
                Class<?> c = m.getParameterTypes()[0];
                List<Subscription> list = map.get(c);
                if (list == null) {
                    list = new ArrayList<>();
                    map.put(c, list);
                }
                list.add(new Subscription(m, subscriber));
            }
        }
    }
    public void post(Object event) {
        Class<?> clazz = event.getClass();
        List<Subscription> list = map.get(clazz);
        if (list == null) {
            return;
        }
        for (Subscription s : list) {
            try {
                s.method.invoke(s.subscriber, event);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public class Subscription {
        public Method method;
        public Object subscriber;

        public Subscription(Method method, Object subscriber) {
            this.method = method;
            this.subscriber = subscriber;
        }
    }
}
