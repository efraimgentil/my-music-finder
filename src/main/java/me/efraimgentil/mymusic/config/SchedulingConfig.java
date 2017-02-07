package me.efraimgentil.mymusic.config;

import me.efraimgentil.mymusic.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Properties;

/**
 * Created by efraimgentil on 01/02/17.
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {

    @Autowired
    TaskScheduler scheduler;

    @Autowired @Qualifier(value = "config")
    Properties appProperties;

    @Autowired
    ScanService scanService;

    @Bean
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    public String teste(){
        scheduler.scheduleWithFixedDelay( () -> scanService.scan() , getScanInterval() );
        return "BAH GAMBIS ? ";
    }

    private Long getScanInterval(){
        return Long.valueOf( appProperties.getProperty("scanInterval") ) * 1000 * 60;
    }
}
