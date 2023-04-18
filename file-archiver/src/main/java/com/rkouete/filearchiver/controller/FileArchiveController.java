package com.rkouete.filearchiver.controller;

import com.rkouete.filearchiver.service.ArchiveService;
import common.EnumProvider;
import common.RestResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/archive")
public class FileArchiveController {

    Logger logger = LogManager.getLogger(FileArchiveController.class);

    private final ArchiveService archiveService;

    public FileArchiveController(ArchiveService archiveService) {
        this.archiveService = archiveService;
    }

    @GetMapping()
    public RestResponse isAlive() {
        return new RestResponse(200, "archive service is up !!");
    }

    /**
     * add a file to the chosen storage(s)
     *
     * @param multipartFile
     * @param providers the providers chosen (S3,BLOB_STORAGE, GOOGLE_STORAGE, ...)
     * @return @link RestResponse
     */
    @PostMapping("/add")
    public RestResponse archive(@RequestParam("file") MultipartFile multipartFile, @RequestParam("providers") String providers) {

        try {

            List<EnumProvider> enumProviders = Arrays.stream(providers.split(","))
                    .map(String::strip)
                    .map(EnumProvider::valueOf)
                    .toList();
            logger.debug("providers : {}", providers);

            //todo check if file.getOriginalFilename() is not null
            File file = new File(multipartFile.getOriginalFilename());
            FileCopyUtils.copy(multipartFile.getBytes(), file);

            archiveService.archive(file, enumProviders);
            Files.delete(file.toPath());
            return new RestResponse(200, "File is archived successfully");
        } catch (IllegalArgumentException|IOException e) {
            e.printStackTrace();
            return new RestResponse(400, "An error occured while archiving " + multipartFile.getOriginalFilename());
        }
    }

}
