package com.edu.eksamenbackend.controller;

import com.edu.eksamenbackend.dto.AlbumDTO;
import com.edu.eksamenbackend.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    // A. Create a new album
    @PostMapping
    public ResponseEntity<AlbumDTO> createAlbum(@RequestBody AlbumDTO albumDTO) {
        AlbumDTO createdAlbum = albumService.createAlbum(albumDTO);
        return ResponseEntity.ok(createdAlbum);
    }

    // B. Get all albums
    @GetMapping
    public ResponseEntity<List<AlbumDTO>> getAllAlbums() {
        List<AlbumDTO> albums = albumService.getAllAlbums();
        return ResponseEntity.ok(albums);
    }

    // C. Get a single album by ID
    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> getAlbumById(@PathVariable Long id) {
        AlbumDTO album = albumService.getAlbumById(id);
        return ResponseEntity.ok(album);
    }

    // D. Update an existing album
    @PutMapping("/{id}")
    public ResponseEntity<AlbumDTO> updateAlbum(@PathVariable Long id, @RequestBody AlbumDTO albumDTO) {
        AlbumDTO updatedAlbum = albumService.updateAlbum(id, albumDTO);
        return ResponseEntity.ok(updatedAlbum);
    }

    // E. Delete an album
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
}