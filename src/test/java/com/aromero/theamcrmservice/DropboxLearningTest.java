package com.aromero.theamcrmservice;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.GetTemporaryLinkResult;
import com.dropbox.core.v2.users.FullAccount;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class DropboxLearningTest {
    private final static String FILE_NAME = "spring_logo.png";

    @Value("${app.dropboxToken}")
    private String accessToken;

    private DbxClientV2 client;

    @Before
    public void initializeClient() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("theam-crm-service").withUserLocale("en_US").build();
        client = new DbxClientV2(config, accessToken);
    }

    @Test
    public void checkUserAccount() throws DbxException {
        FullAccount account = client.users().getCurrentAccount();
        Assert.assertEquals("Abraham Romero", account.getName().getDisplayName());
    }

    @Test
    public void getFileLink() throws DbxException, IOException {
        fileUpload();

        GetTemporaryLinkResult getTemporaryLinkResult = client.files().getTemporaryLink("/" + FILE_NAME);
        System.out.println(getTemporaryLinkResult.getLink());
        Assert.assertNotNull(getTemporaryLinkResult.getLink());

        deleteFile();
    }

    private void fileUpload() throws DbxException, IOException {
        client.files().uploadBuilder("/" + FILE_NAME)
                .uploadAndFinish(new ClassPathResource(FILE_NAME).getInputStream());
    }

    private void deleteFile() throws DbxException {
        client.files().deleteV2("/" + FILE_NAME);
    }
}
