package fr.gouv.finances.soda.example.testpic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@Profile("devvi")
@PropertySources(@PropertySource("config.devvi-properties"))
public class DevviConfig extends Config {

}
