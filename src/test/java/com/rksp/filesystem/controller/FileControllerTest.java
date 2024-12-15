package com.rksp.filesystem.controller;

import com.rksp.filesystem.model.FileEntity;
import com.rksp.filesystem.repository.FileEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileControllerTest {
    @Mock
    private FileEntityRepository fileEntityRepository;

    private FileController fileController;

    @BeforeEach
    void setUp() {
        fileController = new FileController(fileEntityRepository);
    }

    @Test
    void listUploadedFiles() {
        var result = List.of(new FileEntity());
        when(fileEntityRepository.findAll()).thenReturn(result);

        assertEquals(
                fileController.listUploadedFiles(),
                ResponseEntity.ok(result)
        );
    }

    @Test
    void deleteFile() {
        fileController.deleteFile(0L);
        verify(fileEntityRepository).deleteById(0L);
    }
}