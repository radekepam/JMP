package com.module2.monitoring;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!local")
public class DatabaseHealth implements HealthIndicator {

    @Override
    public Health health() {
        if(isDatabaseHealthy()) {
            return Health.up()
                    .withDetail("Database", "Database is running")
                    .build();
        }
        return Health.down()
                .withDetail("Database", "Database is not available now")
                .build();
    }

    private boolean isDatabaseHealthy() {
        return true;
    }
}
