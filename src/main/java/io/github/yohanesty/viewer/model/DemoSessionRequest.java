package io.github.yohanesty.viewer.model;

import java.util.List;

public class DemoSessionRequest {
    private String userId;
    private List<String> imageIds;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public List<String> getImageIds() { return imageIds; }
    public void setImageIds(List<String> imageIds) { this.imageIds = imageIds; }
}
