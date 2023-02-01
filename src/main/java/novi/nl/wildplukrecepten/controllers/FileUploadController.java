package novi.nl.wildplukrecepten.controllers;

import novi.nl.wildplukrecepten.models.FileUpload;
import novi.nl.wildplukrecepten.services.FileUploadService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@RestController
public class FileUploadController {
    private final FileUploadService service;

    public FileUploadController(FileUploadService service) {
        this.service = service;
    }

    // post for single upload
    @PostMapping("/upload")
    FileUpload singleFileUpload(@RequestParam("file") MultipartFile file) {
        // next line makes url. example "http://localhost:8080/download/naam.jpg"
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();
        String contentType = file.getContentType();
        String fileName = service.storeFile(file, url);
        return new FileUpload(fileName, contentType, url);
    }

    // get for single download
    @GetMapping("/download/{fileName}")
    ResponseEntity<Resource> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = service.downLoadFile(fileName);
        // this mediaType decides witch type you accept if you only accept 1 type
        // MediaType contentType = MediaType.IMAGE_JPEG;
        // this is going to accept multiple types
        String mimeType;

        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        // for download attachment use next line
        // return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + resource.getFilename()).body(resource);
        // for showing image in browser
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

}
