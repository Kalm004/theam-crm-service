#CRM Service
This project has been developed using Spring Boot. It provides a CRUD for Customers and Users.

##Getting started
This project uses Gradle as dependency manager.

The following environment variables are required:
* DROPBOX_TOKEN: a dropbox application token to be able to upload files to dropbox 

##Architecture
The architecture is made of the following layers:
* Controller
* Service
* Repository

The repository layer works directly with database entities. The service read / write database entities from the repository but the input and output is done using the structure required by the controller (request and response objects).
The controller will expose the endpoints and contact with the service.

##Error handling
For the error handling I have taken advantage of Spring Boot features and I have declared a global exception handling.

##Input validation
For input validation JSR-303 annotations are being used.

##Endpoints
This API provides endpoints for:
* Login
* CRUD for Customers
* CRUD for Users

##Security
Customers and Users endpoints are secured and a JWT needs to be provided.
This JWT will be returned by the login endpoint.

##Heroku
The application has been deployed to Heroku. No additional or special configuration was needed for it.

Heroku server: https://serene-crag-84514.herokuapp.com

##Swagger
This project has been configured to use Swagger for documentation purpose only.

You can check the output of Swagger-UI here: https://serene-crag-84514.herokuapp.com/swagger-ui.html#/

##Dropbox
The current version of the API has been implemented to use Dropbox to store the photos of the Customers.

If at some point we don't want to keep using Dropbox, it is enough to create a new implementation of the Storage interface.