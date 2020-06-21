package com.backflip.vadsh.job;

import com.backflip.vadsh.service.FileStorage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class PurgeGeneratedFilesJobTest {

    @Autowired
    public PurgeGeneratedFilesJob job;

    @MockBean
    public FileStorage mockStorage;

    @Test
    public void jobClearsStorage() {
        //given

        //when
        job.purgeFiles();

        //then
        verify(mockStorage, times(1)).clearStorage();
    }


}
