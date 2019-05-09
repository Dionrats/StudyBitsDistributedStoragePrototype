package nl.dionrats.studybitsprototype.controller;


import nl.dionrats.studybitsprototype.entity.Document;
import lombok.extern.slf4j.Slf4j;
import nl.dionrats.studybitsprototype.entity.DocumentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import nl.dionrats.studybitsprototype.service.FileService;

import java.io.*;


@Slf4j
@RestController
@RequestMapping(value = "/prototype")
public class PrototypeController {

    private final FileService fileService;

    @Autowired
    public PrototypeController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> upload(@ModelAttribute DocumentDTO document) throws IOException {
        if (document == null) {
            return new ResponseEntity<>("please select a file!", HttpStatus.OK);
        }
        String key = fileService.processFile(document);

        return new ResponseEntity<>(new DocumentDTO(document.getName(), document.getType(), key), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/download/{id}", produces = "application/octet-stream")
    public ResponseEntity<byte[]> download(@PathVariable("id") String id) {
        Document document = fileService.retrieveFile(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/octet-stream"));
        String filename = document.getName() + "." + document.getType();
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(document.getData(), headers, HttpStatus.OK);

    }
}
