package com.khanalsharad.dailyshoppingcart.service.image;

import com.khanalsharad.dailyshoppingcart.dto.ImageDto;
import com.khanalsharad.dailyshoppingcart.exception.ImageNotFoundException;
import com.khanalsharad.dailyshoppingcart.model.Image;
import com.khanalsharad.dailyshoppingcart.model.Product;
import com.khanalsharad.dailyshoppingcart.repo.ImageRepository;
import com.khanalsharad.dailyshoppingcart.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@Slf4j
public class imageServiceImpl implements ImageService {
     private static final Logger logger = Logger.getLogger(imageServiceImpl.class.getName());
    private final ImageRepository imageRepository;
    private final ProductService productService;

    public imageServiceImpl(ImageRepository imageRepository, ProductService productService) {
        this.imageRepository = imageRepository;
        this.productService = productService;
    }

    @Override
    public List<ImageDto> save(List<MultipartFile> files, Long productId) {
        logger.info("Saving {} images for product {}"+ files.size()+ "productId: {}" + productId);
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "api/v1/images/doownload/";
                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();

                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());

                savedImageDto.add(imageDto);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }


    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException("Image not found with id {{}}:" + id));
    }

    @Override
    public Image getImageByUrl(String url) {
        return imageRepository.findByDownloadUrl(url);
    }

    @Override
    public Image updateImageById(MultipartFile file, Long imageId) {
        logger.info("Updating image for {} with id {}"+ file.getOriginalFilename());
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return image;
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ImageNotFoundException("Image not found with id {{}}:" + id);
        });
    }

    @Override
    public List<Image> getAllImagesByProductId(Long productId) {
        return imageRepository.findByProductId(productId);
    }
}
