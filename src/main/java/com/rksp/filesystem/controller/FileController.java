package com.rksp.filesystem.controller;

import com.rksp.filesystem.model.FileEntity;
import com.rksp.filesystem.repository.FileEntityRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
@Controller
@RequiredArgsConstructor
public class FileController {
    private final FileEntityRepository fileEntityRepository;

    @GetMapping("/")
    public ResponseEntity<List<FileEntity>> listUploadedFiles(){
        return ResponseEntity.ok(fileEntityRepository.findAll());
    }

    @PostMapping("/upload")
    public ResponseEntity<FileEntity> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        var fileEntity = FileEntity.builder()
                .fileName(file.getName())
                .fileData(file.getBytes())
                .fileSize(file.getSize())
                .build();
        return ResponseEntity.ok(fileEntityRepository.save(fileEntity));
    }

    @GetMapping(value = "/download/{id}")
    public void serveFile(@PathVariable Long id, HttpServletResponse response) throws IOException {
        var fileEntity = fileEntityRepository.findById(id).orElseThrow();
        var inputStream = new ByteArrayInputStream(fileEntity.getFileData());
        IOUtils.copy(inputStream, response.getOutputStream());
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        fileEntityRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
