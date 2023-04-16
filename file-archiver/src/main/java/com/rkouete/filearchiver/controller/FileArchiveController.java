package com.rkouete.filearchiver.controller;

import com.rkouete.filearchiver.service.ArchiveService;
import common.EnumProvider;
import common.RestResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/archive")
public class FileArchiveController {

    private final ArchiveService archiveService;
    Logger logger = LogManager.getLogger(FileArchiveController.class);

    public FileArchiveController(ArchiveService archiveService) {
        this.archiveService = archiveService;
    }

    @GetMapping()
    public RestResponse isAlive() {
        return new RestResponse(200, "archive service is alive");
    }

    /**
     * add a file to the chosen storage(s)
     *
     * @param file      the
     * @param providers the providers chosen (S3,BLOB_STORAGE, GOOGLE_STORAGE, ...)
     * @return @link RestResponse
     */
    @PostMapping("/add")
    public RestResponse archive(@RequestParam("file") MultipartFile file, @RequestParam("providers") String providers) {
        try {
            List<EnumProvider> enumProviders = Arrays.stream(providers.split(","))
                    .map(String::strip)
                    .map(EnumProvider::valueOf)
                    .toList();
            logger.debug("providers : {}", providers);
            archiveService.archive(file, enumProviders);
            return new RestResponse(200, "File is archived successfully");
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return new RestResponse(400, "An error occured while archiving " + file.getOriginalFilename());
        }


    }
}
