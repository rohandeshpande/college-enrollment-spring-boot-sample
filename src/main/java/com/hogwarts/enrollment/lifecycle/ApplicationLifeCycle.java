package com.hogwarts.enrollment.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationLifeCycle implements Lifecycle {
    @Autowired
    DataCleanupComponent dataCleanupComponent;

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        log.info("Start purging data from all tables before application shutdown");
        dataCleanupComponent.purgeAllData();
        log.info("Completed purging data from all tables");
    }

    @Override
    public boolean isRunning() {
        return true;
    }
}
