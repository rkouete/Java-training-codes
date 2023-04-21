package com.rkouete.filearchiver.service.impl;

import com.rkouete.filearchiver.service.ArchiveService;
import com.rkouete.filearchiver.service.ArchiveStrategy;
import common.EnumProvider;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toUnmodifiableMap;

/**
 * @author rkouete
 */

@Service
public class ArchiveServiceImpl implements ArchiveService {

    private final Map<EnumProvider, ArchiveStrategy> archivers;

    public ArchiveServiceImpl(List<ArchiveStrategy> archivers) {
        this.archivers = archivers.stream()
                .collect(toUnmodifiableMap(ArchiveStrategy::provider, Function.identity()));
    }

    @Override
    public void archive(File file, List<EnumProvider> providers) {
        providers.forEach(provider -> archivers.get(provider).archive(file));
    }
}
