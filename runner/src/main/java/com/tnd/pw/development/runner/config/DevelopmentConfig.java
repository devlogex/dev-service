package com.tnd.pw.development.runner.config;

import com.tnd.dbservice.sdk.api.DBServiceSdkClient;
import com.tnd.dbservice.sdk.api.impl.DBServiceSdkClientImpl;
import com.tnd.pw.development.idea.dao.IdeaDao;
import com.tnd.pw.development.idea.dao.impl.IdeaDaoImpl;
import com.tnd.pw.development.idea.service.IdeaService;
import com.tnd.pw.development.idea.service.impl.IdeaServiceImpl;
import com.tnd.pw.development.runner.handler.DevHandler;
import com.tnd.pw.development.runner.handler.FeatureHandler;
import com.tnd.pw.development.runner.handler.IdeaHandler;
import com.tnd.pw.development.runner.handler.ReleaseHandler;
import com.tnd.pw.development.runner.service.*;
import com.tnd.pw.development.runner.service.impl.*;
import com.tnd.pw.action.sdk.ActionServiceSdkClient;
import com.tnd.pw.action.sdk.impl.ActionServiceSdkClientImpl;
import com.tnd.pw.development.dbservice.DataHelper;
import com.tnd.pw.development.feature.dao.FeatureDao;
import com.tnd.pw.development.feature.dao.RequirementDao;
import com.tnd.pw.development.feature.dao.impl.FeatureDaoImpl;
import com.tnd.pw.development.feature.dao.impl.RequirementDaoImpl;
import com.tnd.pw.development.feature.service.FeatureService;
import com.tnd.pw.development.feature.service.impl.FeatureServiceImpl;
import com.tnd.pw.development.release.dao.EpicDao;
import com.tnd.pw.development.release.dao.ReleaseDao;
import com.tnd.pw.development.release.dao.ReleaseLayoutDao;
import com.tnd.pw.development.release.dao.ReleasePhaseDao;
import com.tnd.pw.development.release.dao.impl.EpicDaoImpl;
import com.tnd.pw.development.release.dao.impl.ReleaseDaoImpl;
import com.tnd.pw.development.release.dao.impl.ReleaseLayoutDaoImpl;
import com.tnd.pw.development.release.dao.impl.ReleasePhaseDaoImpl;
import com.tnd.pw.development.release.service.ReleaseService;
import com.tnd.pw.development.release.service.impl.ReleaseServiceImpl;
import com.tnd.pw.report.sdk.ReportSdkClient;
import com.tnd.pw.report.sdk.impl.ReportSdkClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@PropertySource("classpath:application.properties")
public class DevelopmentConfig {
    public static ExecutorService executor = Executors.newFixedThreadPool(5);

    @Value("${db.host}")
    private String db_host;
    @Value("${db.port}")
    private String db_port;
    @Value("${action.service.host}")
    private String action_service_host;
    @Value("${action.service.port}")
    private String action_service_port;
    @Value("${report.service.host}")
    private String report_service_host;
    @Value("${report.service.port}")
    private String report_service_port;

    @Bean
    public ActionServiceSdkClient actionServiceSdkClient() {
        return new ActionServiceSdkClientImpl(action_service_host, Integer.parseInt(action_service_port), 2);
    }

    @Bean
    public DBServiceSdkClient dbServiceSdkClient() {
        return new DBServiceSdkClientImpl(db_host,Integer.parseInt(db_port), 1);
    }

    @Bean
    public ReportSdkClient reportSdkClient() {
        return new ReportSdkClientImpl(report_service_host, Integer.parseInt(report_service_port), 1);
    }

    @Bean
    public SdkService sdkService() {
        return new SdkService();
    }

    @Bean
    public DataHelper dataHelper(DBServiceSdkClient dbServiceSdkClient) {
        return new DataHelper(dbServiceSdkClient);
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
    public ReleaseLayoutDao releaseLayoutDao() {
        return new ReleaseLayoutDaoImpl();
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
    public IdeaDao ideaDao() {
        return new IdeaDaoImpl();
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
    public IdeaService ideaService() {
        return new IdeaServiceImpl();
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
    public IdeaHandlerService ideaHandlerService() {
        return new IdeaHandlerServiceImpl();
    }

    @Bean
    public FeatureHandler featureHandler() {
        return new FeatureHandler();
    }

    @Bean
    public ReleaseHandler releaseHandler() {
        return new ReleaseHandler();
    }

    @Bean
    public DevHandler devHandler() {
        return new DevHandler();
    }

    @Bean
    public IdeaHandler ideaHandler() {
        return new IdeaHandler();
    }

    @Bean
    public CalculateService calculateService() {
        return new CalculateService();
    }

    @Bean
    public DevHandlerService devHandlerService() {
        return new DevHandlerServiceImpl();
    }
}
