package com.khanalsharad.dailyshoppingcart.dto;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
@Getter
@Setter
public class ImageDto {

    private Long imageId;

    private String imageName;

    private String downloadUrl;
}
