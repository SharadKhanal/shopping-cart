package com.khanalsharad.dailyshoppingcart.service.image;

import com.khanalsharad.dailyshoppingcart.dto.ImageDto;
import com.khanalsharad.dailyshoppingcart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<ImageDto> save(List<MultipartFile> file, Long productId);

    Image getImageById(Long id);

    Image getImageByUrl(String url);

    Image updateImageById(MultipartFile file, Long imageId);

    void deleteImageById(Long id);

    List<ImageDto> getAllImagesByProductId(Long productId);

    List<ImageDto> getAllImages();
}
