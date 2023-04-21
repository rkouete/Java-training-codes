package com.rkouete.filearchiver.controller;

import com.rkouete.filearchiver.service.ArchiveService;
import com.rkouete.filearchiver.service.ArchiveStrategy;
import com.rkouete.filearchiver.service.impl.ArchiveServiceImpl;
import com.rkouete.filearchiver.service.impl.S3ArchiveStrategy;
import common.EnumProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.CallsRealMethods;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(FileArchiveController.class)
class FileArchiveControllerTest {

    @Mock
    S3ArchiveStrategy s3ArchiveStrategy;

    @Mock
    @Qualifier("GOOGLE_STORAGE")
    ArchiveStrategy blobStorageArchiveStrategy;

    private MockMvc mockMvc;

    @MockBean
    ArchiveService archiveService;

    @Autowired
    WebApplicationContext wContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(s3ArchiveStrategy.provider()).thenReturn(EnumProvider.S3);
        Mockito.when(blobStorageArchiveStrategy.provider()).thenReturn(EnumProvider.BLOB_STORAGE);

        lenient().doAnswer(new CallsRealMethods()).when(s3ArchiveStrategy)
                .archive(any(File.class));

        archiveService = new ArchiveServiceImpl(Arrays.asList(s3ArchiveStrategy, blobStorageArchiveStrategy));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(wContext)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void archive() throws Exception {

        Resource fileResource = new ClassPathResource(
                "images/attachment.png");

        assertNotNull(fileResource);

        MockMultipartFile file = new MockMultipartFile(
                "file",fileResource.getFilename(),
                MediaType.MULTIPART_FORM_DATA_VALUE,
                fileResource.getInputStream());
        assertNotNull(file);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/archive/add")
                        .file(file)
                        .param("providers", "S3")
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("File is archived successfully"));

    }

}