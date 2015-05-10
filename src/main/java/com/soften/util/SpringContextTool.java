package com.soften.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by mysgq1 on 15/2/10.
 */
public class SpringContextTool implements ApplicationContextAware {
    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext acx) {
        context = acx;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

}
