package com.tnd.pw.action.runner.config;

import com.tnd.pw.action.sdk.ActionSdkApi;
import com.tnd.pw.development.dbservice.DBServiceApiClient;
import com.tnd.pw.development.dbservice.DataHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class DevelopmentConfig {
    @Value("${db.url}")
    private String db_url;
    @Value("${action.domain}")
    private String actionDomain;

    @Bean
    public ActionSdkApi actionSdkApi() {
        return new ActionSdkApi(actionDomain);
    }

    @Bean
    public DBServiceApiClient dbServiceApiClient() {
        return new DBServiceApiClient();
    }

    @Bean
    public DataHelper dataHelper(DBServiceApiClient dbServiceApiClient) {
        return new DataHelper(db_url, dbServiceApiClient);
    }

}
