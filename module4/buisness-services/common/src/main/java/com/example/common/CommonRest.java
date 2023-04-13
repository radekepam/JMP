package com.example.common;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RefreshScope
public class CommonRest {

    @Value("${db.username:not found!}")
    private String propertyOneWithValue;

    private DynamicStringProperty dynamicUsername = DynamicPropertyFactory.getInstance()
            .getStringProperty("db.username", "no username");

    private DynamicStringProperty appName = DynamicPropertyFactory.getInstance()
            .getStringProperty("spring.application.name", "no name");

    @GetMapping("")
    public String hello() {
        DynamicStringProperty dbUsername = DynamicPropertyFactory.getInstance().getStringProperty("db.username", "no username");

        return "Hello from " + appName.get() + " db property: " + propertyOneWithValue + "  xx  " + dynamicUsername.getName() + ": " + dynamicUsername.get();
    }
}
