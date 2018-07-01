package com.aromero.theamcrmservice.storage;

import java.io.InputStream;

public interface Storage {
    void saveFile(String destinationPath, InputStream in);
}
