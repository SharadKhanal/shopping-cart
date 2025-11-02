package com.khanalsharad.dailyshoppingcart.service.image;

import com.khanalsharad.dailyshoppingcart.exception.ImageNotFoundException;
import com.khanalsharad.dailyshoppingcart.model.Image;
import com.khanalsharad.dailyshoppingcart.repo.ImageRepository;
import com.khanalsharad.dailyshoppingcart.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class imageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductService productService;
    @Override
    public Image save(MultipartFile file, Long productId) {
        return null;
    }

    private Image createImage(MultipartFile file, Long productId) {
         return null;
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException("Image not found with id {{}}:" + id));
    }

    @Override
    public Image getImageByUrl(String url) {
        return null;
    }

    @Override
    public Image updateImageById(MultipartFile file, Long imageId) {
        return null;
    }

    private Image updateExistingImage(Image image, Long imageId) {

        return null;
    }

    @Override
    public void deleteImageById(Long id) {
     imageRepository.findById(id).ifPresentOrElse( imageRepository::delete, () -> {
         throw new ImageNotFoundException("Image not found with id {{}}:" + id);
     });
    }
}
