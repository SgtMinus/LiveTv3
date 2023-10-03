package com.live.tv.LiveTv.scheduler.factory;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.stereotype.Component;

@Component
public class JobFactory {

    public JobDetail createJob(Class job, String name,
                               String group, String description) {
        return JobBuilder.newJob().ofType(job)
                .storeDurably(false)
                .withIdentity(name, group)
                .withDescription(description)
                .build();
    }

}
