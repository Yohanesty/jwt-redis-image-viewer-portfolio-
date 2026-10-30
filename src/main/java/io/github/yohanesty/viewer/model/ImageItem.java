package io.github.yohanesty.viewer.model;

public class ImageItem {
    private final String imageId;
    private final String displayName;

    public ImageItem(String imageId, String displayName) {
        this.imageId = imageId;
        this.displayName = displayName;
    }

    public String getImageId() { return imageId; }
    public String getDisplayName() { return displayName; }
}
