package com.tnd.pw.development.runner;

import com.tnd.com.ioc.SpringApplicationContext;
import com.tnd.common.api.server.CommonServer;
import com.tnd.pw.development.runner.config.DevelopmentConfig;
import com.tnd.pw.development.runner.handler.DevHandler;
import com.tnd.pw.development.runner.handler.FeatureHandler;
import com.tnd.pw.development.runner.handler.IdeaHandler;
import com.tnd.pw.development.runner.handler.ReleaseHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DevelopmentRunner {
    public static void main(String args[]) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(DevelopmentConfig.class);
        context.refresh();
        SpringApplicationContext.setShareApplicationContext(context);

        CommonServer commonServer = new CommonServer();
        commonServer.register(SpringApplicationContext.getBean(FeatureHandler.class));
        commonServer.register(SpringApplicationContext.getBean(ReleaseHandler.class));
        commonServer.register(SpringApplicationContext.getBean(DevHandler.class));
        commonServer.register(SpringApplicationContext.getBean(IdeaHandler.class));

        commonServer.initServlet(8005);
        commonServer.startServer();
    }
}
