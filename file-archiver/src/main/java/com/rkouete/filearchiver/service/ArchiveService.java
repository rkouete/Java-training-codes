package com.rkouete.filearchiver.service;

import common.EnumProvider;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArchiveService {
    void archive(MultipartFile file, List<EnumProvider> providers);
}
