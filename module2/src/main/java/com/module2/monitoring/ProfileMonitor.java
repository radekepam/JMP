package com.module2.monitoring;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@Profile("!local")
public class ProfileMonitor implements HealthIndicator {

    @Autowired
    private Environment environment;

    @Override
    public Health health() {
        return Health.up()
                .withDetail("Profile", getProfiles())
                .build();
    }

    private String getProfiles() {
        return String.join(", ", this.environment.getActiveProfiles());
    }
}
