package com.aromero.theamcrmservice.storage;

import java.io.InputStream;

public interface Storage {
    void deleteFile(String filePath);

    void saveFile(String destinationPath, InputStream in);

    String getTempLink(String filePath);
}
