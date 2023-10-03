package com.live.tv.LiveTv.scheduler.job;

import com.live.tv.LiveTv.service.CourtService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class RecoverPostsJob implements Job {
    @Autowired
    private CourtService courtService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        courtService.recoverPosts();
        log.info("Recover posts successfully started");
    }
}
