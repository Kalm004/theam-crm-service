package com.aromero.theamcrmservice.api.customerPhoto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/customers/{id}/photo")
public class CustomerPhotoController {
    private final CustomerPhotoService customerPhotoService;

    @Autowired
    public CustomerPhotoController(CustomerPhotoService customerPhotoService) {
        this.customerPhotoService = customerPhotoService;
    }

    @PostMapping
    public void handleFileUpload(@PathVariable("id") Long customerId, @RequestParam("file") MultipartFile file) throws IOException {
        customerPhotoService.saveCustomerPhoto(customerId, file);
    }

    @GetMapping
    public String getPhotoUrl() {
        return "";
    }
}
