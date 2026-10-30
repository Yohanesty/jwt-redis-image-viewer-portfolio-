package io.github.yohanesty.viewer.controller;

import io.github.yohanesty.viewer.model.ImageItem;
import io.github.yohanesty.viewer.model.ViewerAccessContext;
import io.github.yohanesty.viewer.security.ViewerAuthorizationInterceptor;
import io.github.yohanesty.viewer.service.SyntheticImageService;
import io.github.yohanesty.viewer.service.ViewerAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final ViewerAccessService accessService;
    private final SyntheticImageService imageService;

    @Autowired
    public ImageController(ViewerAccessService accessService,
                           SyntheticImageService imageService) {
        this.accessService = accessService;
        this.imageService = imageService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ImageItem> list(HttpServletRequest request) {
        ViewerAccessContext context = context(request);
        List<ImageItem> items = new ArrayList<ImageItem>();
        for (String imageId : context.getSession().getAllowedImageIds()) {
            items.add(new ImageItem(imageId, "Synthetic " + imageId));
        }
        return items;
    }

    @RequestMapping(value = "/{imageId}/stream", method = RequestMethod.GET)
    public ResponseEntity<byte[]> stream(@PathVariable("imageId") String imageId,
                                         HttpServletRequest request) {
        ViewerAccessContext context = context(request);
        accessService.assertImageAllowed(context, imageId);

        byte[] body = imageService.renderSvg(imageId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("image/svg+xml"));
        headers.setCacheControl("no-store");
        return new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);
    }

    private ViewerAccessContext context(HttpServletRequest request) {
        return (ViewerAccessContext) request.getAttribute(
                ViewerAuthorizationInterceptor.ACCESS_CONTEXT);
    }
}
