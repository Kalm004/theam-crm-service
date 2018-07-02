package com.aromero.theamcrmservice.integration;

import com.aromero.theamcrmservice.storage.Storage;
import com.aromero.theamcrmservice.storage.StorageException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest extends BaseIntegrationTest{
    private static final int NUMBER_OF_CUSTOMERS = 2;

    @MockBean
    private Storage storage;

    @Test
    public void getAllCustomers200AndGetListOfCustomers() {
        getWithTokenAndExpectedStatusCode(
                noAdminLoginResponse.getToken(),
                "/customers",
                200
        ).body("size()", equalTo(NUMBER_OF_CUSTOMERS));
    }

    @Test
    public void getCustomerById200AndCustomerWithExistingCustomer() {
        String photoTempUrl = "tempUrl";
        when(storage.getTempLink("/customers/1/customer1.jpg")).thenReturn(photoTempUrl);

        getWithTokenAndExpectedStatusCode(
                noAdminLoginResponse.getToken(),
                "/customers/1",
                200
        ).body("id", equalTo(1))
        .body("photoTempUrl", equalTo(photoTempUrl));
    }

    @Test
    public void getCustomerById404IfCustomerDoesntExist() {
        getWithTokenAndExpectedStatusCode(
                noAdminLoginResponse.getToken(),
                "/customers/100",
                404
        );
    }

    @Test
    public void getCustomerById500IfStorageException() {
        doThrow(new StorageException()).when(storage).getTempLink("/customers/1/customer1.jpg");
        getWithTokenAndExpectedStatusCode(
                noAdminLoginResponse.getToken(),
                "/customers/1",
                500
        );
    }

    @Test
    @DirtiesContext
    public void deleteCustomer200WithExistingCustomer() {
        deleteWithTokenAndExpectedStatusCode(
                noAdminLoginResponse.getToken(),
                "/customers/1",
                200
        );
    }

    @Test
    @DirtiesContext
    public void deleteCustomer404IfCustomerDoesntExist() {
        deleteWithTokenAndExpectedStatusCode(
                noAdminLoginResponse.getToken(),
                "/customers/100",
                404
        );
    }

    @Test
    @DirtiesContext
    public void deleteCustomer500IfStorageException() {
        doThrow(new StorageException()).when(storage).deleteFile("/customers/1/customer1.jpg");
        deleteWithTokenAndExpectedStatusCode(
                noAdminLoginResponse.getToken(),
                "/customers/1",
                500
        );
    }

    @Test
    public void error401WhenNotAuthenticatedForAllEndpoints() {
        getWithTokenAndExpectedStatusCode("", "/customers", 401);

        getWithTokenAndExpectedStatusCode("", "/customers/1", 401);

        deleteWithTokenAndExpectedStatusCode("", "/customers/1", 401);
    }
}
