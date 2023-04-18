package com.rkouete.filearchiver.service.impl;

import com.rkouete.filearchiver.service.ArchiveService;
import com.rkouete.filearchiver.service.ArchiveStrategy;
import common.EnumProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.lenient;

import java.io.File;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class ArchiveServiceImplTest {

    @Mock
    ArchiveStrategy s3ArchiveStrategy;

    @Mock
    @Qualifier("GOOGLE_STORAGE")
    ArchiveStrategy blobStorageArchiveStrategy;

//    @Mock
//    ArchiveStrategy googleStorageArchiveStrategy;

    @Mock
    ArchiveService archiveService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(s3ArchiveStrategy.provider()).thenReturn(EnumProvider.S3);
        when(blobStorageArchiveStrategy.provider()).thenReturn(EnumProvider.BLOB_STORAGE);
       // when(googleStorageArchiveStrategy.provider()).thenReturn(EnumProvider.GOOGLE_STORAGE);
        archiveService = new ArchiveServiceImpl(Arrays.asList(s3ArchiveStrategy, blobStorageArchiveStrategy));
    }

    @Test
    void testArchive() {
        File file = Mockito.mock(File.class);
        lenient().when(file.isFile()).thenReturn(Boolean.TRUE);
        lenient().when(file.getName()).thenReturn("my-file.jpg");
        archiveService.archive(file, Arrays.asList(EnumProvider.S3,EnumProvider.BLOB_STORAGE));
    }
}