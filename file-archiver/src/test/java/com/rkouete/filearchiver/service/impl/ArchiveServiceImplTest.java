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
import org.mockito.internal.stubbing.answers.CallsRealMethods;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArchiveServiceImplTest {

    @Mock
    S3ArchiveStrategy s3ArchiveStrategy;

    @Mock
    BlobStorageArchiveStrategy blobStorageArchiveStrategy;

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
        lenient().doAnswer(new CallsRealMethods()).when(s3ArchiveStrategy)
                .archive(any(File.class));
        lenient().doAnswer(new CallsRealMethods()).when(blobStorageArchiveStrategy)
                .archive(any(File.class));
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