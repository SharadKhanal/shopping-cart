package com.khanalsharad.dailyshoppingcart.controller;

import com.khanalsharad.dailyshoppingcart.dto.ImageDto;
import com.khanalsharad.dailyshoppingcart.model.Image;
import com.khanalsharad.dailyshoppingcart.response.ApiResponse;
import com.khanalsharad.dailyshoppingcart.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadImage(@RequestParam("file") List<MultipartFile> file, @RequestParam Long productId) {
        try {
            List<ImageDto> imageDtos = imageService.save(file, productId);
            return ResponseEntity.ok(new ApiResponse("Upload Success", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload Failed!", e.getMessage()));
        }
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("imageId") Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType())).header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + image.getFileName() + "\"").body(resource);
    }

    @PutMapping("/update/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable("imageId") Long imageId, @RequestBody MultipartFile file) {

        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.updateImageById(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Update Success", image));
            }
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update Failed", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable("imageId") Long imageId) {

        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete Success", image));
            }
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update Failed", INTERNAL_SERVER_ERROR));
    }
}
