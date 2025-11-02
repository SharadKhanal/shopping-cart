package com.khanalsharad.dailyshoppingcart.service.image;

import com.khanalsharad.dailyshoppingcart.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    Image save(MultipartFile file, Long productId);

    Image getImageById(Long id);

    Image getImageByUrl(String url);

    Image updateImageById( MultipartFile file, Long imageId);

    void deleteImageById(Long id);
}
