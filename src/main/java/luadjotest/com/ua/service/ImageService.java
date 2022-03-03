package luadjotest.com.ua.service;

import luadjotest.com.ua.model.Product;
import luadjotest.com.ua.repository.ImageRepos;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    private final ImageRepos imageRepos;

    public ImageService(ImageRepos imageRepos) {
        this.imageRepos = imageRepos;
    }


}
