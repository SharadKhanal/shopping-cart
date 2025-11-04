package com.khanalsharad.dailyshoppingcart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageDto {

    private Long imageId;

    private String imageName;

    private String downloadUrl;

    public Long getImageId() {
        return imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
