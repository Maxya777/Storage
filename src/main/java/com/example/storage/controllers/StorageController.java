package com.example.storage.controllers;

import com.example.storage.AuthConstants;
import com.example.storage.dto.FileResponse;
import com.example.storage.entities.File;
import com.example.storage.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class StorageController {
    private final StorageService storageService;

    @GetMapping("/list")
    public ResponseEntity<List<FileResponse>> getAllFiles(@RequestHeader(AuthConstants.HEADER_STRING) String authToken,
                                                          @RequestParam("limit") int limit) {
        return ResponseEntity.ok(storageService.getFiles(authToken, limit));
    }

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestHeader(AuthConstants.HEADER_STRING) String authToken,
                                        @RequestParam("filename") String filename,
                                        @RequestBody MultipartFile file) throws IOException {
        storageService.uploadFile(authToken, filename, file);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/file")
    public ResponseEntity<?> renameFile(@RequestHeader(AuthConstants.HEADER_STRING) String authToken,
                                        @RequestParam("filename") String filename,
                                        @RequestBody Map<String, String> fileNameRequest) {
        storageService.renameFile(authToken, filename, fileNameRequest.get("filename"));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestHeader(AuthConstants.HEADER_STRING) String authToken,
                                        @RequestParam("filename") String filename) {
        storageService.deleteFile(authToken, filename);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> downloadFile(@RequestHeader(AuthConstants.HEADER_STRING) String authToken,
                                               @RequestParam("filename") String filename) {
        File file = storageService.downloadFile(authToken, filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file.getContent());
    }
}