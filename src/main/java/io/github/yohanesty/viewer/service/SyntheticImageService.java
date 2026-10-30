package io.github.yohanesty.viewer.service;

import java.nio.charset.StandardCharsets;

public class SyntheticImageService {

    public byte[] renderSvg(String imageId) {
        String safeId = imageId == null ? "UNKNOWN" : imageId.replaceAll("[^A-Za-z0-9_-]", "_");
        String svg = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"640\" height=\"360\">"
                + "<rect width=\"100%\" height=\"100%\" fill=\"#f3f4f6\"/>"
                + "<text x=\"40\" y=\"170\" font-family=\"sans-serif\" font-size=\"28\" fill=\"#111827\">"
                + "Synthetic Portfolio Image"
                + "</text>"
                + "<text x=\"40\" y=\"215\" font-family=\"monospace\" font-size=\"20\" fill=\"#374151\">"
                + safeId
                + "</text>"
                + "</svg>";
        return svg.getBytes(StandardCharsets.UTF_8);
    }
}
