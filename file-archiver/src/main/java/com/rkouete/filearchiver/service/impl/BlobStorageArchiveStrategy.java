package com.rkouete.filearchiver.service.impl;

import com.rkouete.filearchiver.service.ArchiveStrategy;
import common.EnumProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author rkouete
 */

@Component
@Qualifier("BLOB_STORAGE")
public class BlobStorageArchiveStrategy implements ArchiveStrategy {
    @Override
    public void archive(File file) {
        logger.debug("Archiving {} on Blob storage", file.getName());
        //todo
    }

    @Override
    public EnumProvider provider() {
        return EnumProvider.BLOB_STORAGE;
    }
}
