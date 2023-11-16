package com.drone.droneapi.cron;

import com.drone.droneapi.error.LocalBadRequestException;
import com.drone.droneapi.error.LocalNotFoundException;
import com.drone.droneapi.services.IPeriodicTaskLogService;
import com.drone.droneapi.error.ApiResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class MyTaskScheduler {
    private final Logger logger;
    private final IPeriodicTaskLogService periodicTaskLogService;
    @Autowired
    public MyTaskScheduler(Logger logger, IPeriodicTaskLogService periodicTaskLogService)
    {
        this.logger = logger;
        this.periodicTaskLogService = periodicTaskLogService;
    }

    @Scheduled(fixedDelay = 10000)
    public void logDroneBattery()  {

        try{
            ApiResponse logsApiResponse = periodicTaskLogService.periodicTaskLogs();
            if (logsApiResponse.isValid())
            {
                logger.info((String)logsApiResponse.getResult());
            }
            else
            {
                logger.error(String.join(" && ", logsApiResponse.getErrors()));
            }
        }
        catch (Exception ex){
            logger.error(ex.getMessage());
        }
    }
}