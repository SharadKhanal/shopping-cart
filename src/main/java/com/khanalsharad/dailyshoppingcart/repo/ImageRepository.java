package com.khanalsharad.dailyshoppingcart.repo;

import com.khanalsharad.dailyshoppingcart.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
