package com.tnd.pw.action.runner;

import com.tnd.com.ioc.SpringApplicationContext;
import com.tnd.common.api.server.CommonServer;
import com.tnd.pw.action.runner.config.DevelopmentConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DevelopmentRunner {
    public static void main(String args[]) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DevelopmentConfig.class);
        context.refresh();
        SpringApplicationContext.setShareApplicationContext(context);

        CommonServer commonServer = new CommonServer();

        String port = System.getenv("PORT");
        if (port == null) {
            commonServer.initServlet(8005);
        } else {
            commonServer.initServlet(Integer.parseInt(port));
        }
        commonServer.startServer();
    }
}
