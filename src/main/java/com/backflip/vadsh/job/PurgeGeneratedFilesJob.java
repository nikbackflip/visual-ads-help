package com.backflip.vadsh.job;

import com.backflip.vadsh.service.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PurgeGeneratedFilesJob {

    private final FileStorage storage;

    @Scheduled(cron = "0 0 0 * * *", zone = "GMT")
    public void purgeFiles() {
        try {
            storage.clearStorage();
        } catch (Exception ex) {

        }
    }

}
