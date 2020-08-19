package fr.gouv.finances.soda.example.testpic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Profile("piccloudsoda")
@PropertySources(@PropertySource("config.piccloudsoda-properties"))
public class PicCloudSodaConfig extends Config {

}
