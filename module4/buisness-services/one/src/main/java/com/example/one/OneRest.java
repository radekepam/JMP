package com.example.one;

import com.netflix.servo.DefaultMonitorRegistry;
import com.netflix.servo.monitor.BasicTimer;
import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.monitor.Stopwatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.concurrent.TimeUnit.SECONDS;

@RestController
@RequestMapping("/api")
public class OneRest {

    @GetMapping("")
    public String hello() throws InterruptedException {
        BasicTimer timer = new BasicTimer(MonitorConfig.builder("test").build(), SECONDS);
        Stopwatch stopwatch = timer.start();

        SECONDS.sleep(1);
        timer.record(2, SECONDS);
        stopwatch.stop();
        DefaultMonitorRegistry.getInstance().register(timer);
        return "Hello from one";
    }
}
