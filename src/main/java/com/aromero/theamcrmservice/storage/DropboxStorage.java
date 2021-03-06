package com.aromero.theamcrmservice.storage;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.GetTemporaryLinkResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class DropboxStorage implements Storage {
    @Value("${app.dropboxToken}")
    private String accessToken;

    @Override
    public void deleteFile(String filePath) {
        try {
            getClient().files().deleteV2(filePath);
        } catch (Exception e) {
            throw new StorageException();
        }
    }

    @Override
    public void saveFile(String destinationPath, InputStream in) {
        try {
            getClient().files().uploadBuilder(destinationPath).uploadAndFinish(in);
        } catch (Exception e) {
            throw new StorageException();
        }
    }

    @Override
    public String getTempLink(String filePath) {
        try {
            GetTemporaryLinkResult getTemporaryLinkResult = getClient().files().getTemporaryLink(filePath);
            return getTemporaryLinkResult.getLink();
        } catch (DbxException e) {
            throw new StorageException();
        }
    }

    private DbxClientV2 getClient() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("theam-crm-service").withUserLocale("en_US").build();
        return new DbxClientV2(config, accessToken);
    }
}
