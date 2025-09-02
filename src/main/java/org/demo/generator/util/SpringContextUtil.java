package org.demo.generator.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/1
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        SpringContextUtil.context = context;
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }
}
