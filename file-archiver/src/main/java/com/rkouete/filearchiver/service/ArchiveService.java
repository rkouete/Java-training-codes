package com.rkouete.filearchiver.service;

import common.EnumProvider;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author rkouete
 */
public interface ArchiveService {
    void archive(File file, List<EnumProvider> providers);
}
