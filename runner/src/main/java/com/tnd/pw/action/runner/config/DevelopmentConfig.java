package com.tnd.pw.action.runner.config;

import com.tnd.pw.action.runner.handler.FeatureHandler;
import com.tnd.pw.action.runner.handler.ReleaseHandler;
import com.tnd.pw.action.runner.service.FeatureHandlerService;
import com.tnd.pw.action.runner.service.ReleaseHandlerService;
import com.tnd.pw.action.runner.service.impl.FeatureHandlerServiceImpl;
import com.tnd.pw.action.runner.service.impl.ReleaseHandlerServiceImpl;
import com.tnd.pw.action.sdk.ActionSdkApi;
import com.tnd.pw.development.dbservice.DBServiceApiClient;
import com.tnd.pw.development.dbservice.DataHelper;
import com.tnd.pw.development.feature.dao.FeatureDao;
import com.tnd.pw.development.feature.dao.RequirementDao;
import com.tnd.pw.development.feature.dao.impl.FeatureDaoImpl;
import com.tnd.pw.development.feature.dao.impl.RequirementDaoImpl;
import com.tnd.pw.development.feature.service.FeatureService;
import com.tnd.pw.development.feature.service.impl.FeatureServiceImpl;
import com.tnd.pw.development.release.dao.EpicDao;
import com.tnd.pw.development.release.dao.ReleaseDao;
import com.tnd.pw.development.release.dao.ReleasePhaseDao;
import com.tnd.pw.development.release.dao.impl.EpicDaoImpl;
import com.tnd.pw.development.release.dao.impl.ReleaseDaoImpl;
import com.tnd.pw.development.release.dao.impl.ReleasePhaseDaoImpl;
import com.tnd.pw.development.release.service.ReleaseService;
import com.tnd.pw.development.release.service.impl.ReleaseServiceImpl;
import com.tnd.pw.strategy.call.api.CallActionApi;
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

    @Bean
    public CallActionApi callActionApi() {
        return new CallActionApi();
    }

    @Bean
    public ReleaseDao releaseDao() {
        return new ReleaseDaoImpl();
    }

    @Bean
    public ReleasePhaseDao releasePhaseDao() {
        return new ReleasePhaseDaoImpl();
    }

    @Bean
    public EpicDao epicDao() {
        return new EpicDaoImpl();
    }

    @Bean
    public FeatureDao featureDao() {
        return new FeatureDaoImpl();
    }

    @Bean
    public RequirementDao requirementDao() {
        return new RequirementDaoImpl();
    }

    @Bean
    public ReleaseService releaseService() {
        return new ReleaseServiceImpl();
    }

    @Bean
    public FeatureService featureService() {
        return new FeatureServiceImpl();
    }

    @Bean
    public FeatureHandlerService featureHandlerService() {
        return new FeatureHandlerServiceImpl();
    }

    @Bean
    public ReleaseHandlerService releaseHandlerService() {
        return new ReleaseHandlerServiceImpl();
    }

    @Bean
    public FeatureHandler featureHandler() {
        return new FeatureHandler();
    }

    @Bean
    public ReleaseHandler releaseHandler() {
        return new ReleaseHandler();
    }
}
