package com.rkouete.filearchiver.service;

import common.EnumProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface ArchiveStrategy {
    Logger logger = LogManager.getLogger();
    void archive(MultipartFile file);
    EnumProvider provider();
}
