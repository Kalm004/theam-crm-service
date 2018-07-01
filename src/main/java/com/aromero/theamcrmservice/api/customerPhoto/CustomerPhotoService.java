package com.aromero.theamcrmservice.api.customerPhoto;

import com.aromero.theamcrmservice.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CustomerPhotoService {
    private final Storage storage;

    @Autowired
    public CustomerPhotoService(Storage storage) {
        this.storage = storage;
    }

    public void saveCustomerPhoto(Long customerId, MultipartFile file) throws IOException {
        storage.saveFile("/customers/" + customerId + "/" + file.getOriginalFilename(), file.getInputStream());
    }
}
