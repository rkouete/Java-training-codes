package com.rkouete.filearchiver.service.impl;

import com.rkouete.filearchiver.service.ArchiveStrategy;
import common.EnumProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Qualifier("S3")
public class S3ArchiveStrategy implements ArchiveStrategy {
    @Override
    public void archive(MultipartFile file) {
        logger.debug("Archiving {} on S3", file.getOriginalFilename());
    }

    @Override
    public EnumProvider provider() {
        return EnumProvider.S3;
    }
}
