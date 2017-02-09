package me.efraimgentil.mymusic.config;

import me.efraimgentil.mymusic.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Properties;

/**
 * Created by efraimgentil on 01/02/17.
 */
@Configuration
@EnableJpaRepositories( basePackages ="me.efraimgentil.mymusic.repository" )
@EntityScan(basePackages =  "me.efraimgentil.mymusic.model")
public class JpaConfig {

    @Autowired @Qualifier(value = "config")
    Properties appProperties;



}
