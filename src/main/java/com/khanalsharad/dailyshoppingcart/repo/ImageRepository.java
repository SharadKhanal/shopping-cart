package com.khanalsharad.dailyshoppingcart.repo;

import com.khanalsharad.dailyshoppingcart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByDownloadUrl(String downloadUrl);

    List<Image> findByProductId(Long productId);

    Image findByFileName(String fileName);

}
