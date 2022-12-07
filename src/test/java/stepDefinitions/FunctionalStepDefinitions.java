package stepDefinitions;


import common.ConfigFileReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;

import common.Util;
import helpers.APIRequestHelper;



public class FunctionalStepDefinitions {


    APIRequestHelper apiRequestHelper = new APIRequestHelper();
    Response response;
    String authCredentialJsonFilePath ="src/test/resources/testdata/JsonFiles/AuthDetailsPayload.json";
    String bookingIdSchemaFilePath ="src/test/resources/schemas/bookingIdSchema.json";
    static String pingPath = "ping";
    static String bookingPath = "booking";
    static String authPath = "auth";
    static String bookingIdPath = "booking/{id}";
    static String bookingIdKey =  "bookingid";
    static String tokenKey =   "token";
    static String token_id;
    static int booking_id;



    @Given("the valid base URL")
    public void theValidBaseURL() {
        apiRequestHelper.getBaseURL();
    }

    @When("the request send to the health check end point")
    public void theRequestSendToTheHealthCheckEndPoint() {
        response = apiRequestHelper.getCallResponse(pingPath);
    }

    @Then("validate the response for the status code <{int}> and body {string}")
    public void validateTheResponseForTheStatusCodeAndBody(int status_code, String message) {
        apiRequestHelper.validateStatusCodeAndContentType(status_code,ContentType.TEXT);
        apiRequestHelper.validateTheResponseBodyText(message);
    }

    @Then("validate the response for the status code {string}, content type {string} and body {string}")
    public void validateTheResponseForTheStatusCodeContentTypeAndBody(String status_code, String content_type, String message) {
        apiRequestHelper.validateStatusCodeAndContentType(Integer.parseInt(status_code), ContentType.valueOf(content_type));
        apiRequestHelper.validateTheResponseBodyText(message);
    }



    @Given("Health Check on the environment is successful with the status code {string}, content type {string} and body {string}")
    public void healthCheckOnTheEnvironmentIsSuccessfulWithTheStatusCodeContentTypeAndBody(String status_code, String content_type, String message) {
        apiRequestHelper.getBaseURL();
        response = apiRequestHelper.getCallResponse(pingPath);
        apiRequestHelper.validateStatusCodeAndContentType(Integer.parseInt(status_code), ContentType.valueOf(content_type));
        apiRequestHelper.validateTheResponseBodyText(message);
    }

    @When("the request is send to server")
    public void theRequestIsSendToServer() {
        response = apiRequestHelper.getCallResponse(bookingPath);
    }

    @When("the request is send to server with query parameters {string} , {string} as First name and Last name")
    public void theRequestIsSendToServerWithQueryParametersAsFirstNameAndLastName(String firstname, String lasatname) {
        response = apiRequestHelper.getCallWithTwoQueryParametersResponse(bookingPath,"firstname",firstname,"lastname",lasatname);

    }
    @When("the request is send to server with query parameters {string} as First name")
    public void theRequestIsSendToServerWithQueryParametersAsFirstName(String firstname) {
        response = apiRequestHelper.getCallWithOneQueryParametersResponse(bookingPath,"firstname",firstname);

    }

    @When("the request is send to server with query parameters {string} as Last name")
    public void theRequestIsSendToServerWithQueryParametersAsLastName(String lastname) {
        response = apiRequestHelper.getCallWithOneQueryParametersResponse(bookingPath,"lastname",lastname);
    }

    @When("the request is send to server with query parameters {string} , {string} as checkin and checkout")
    public void theRequestIsSendToServerWithQueryParametersAsCheckinAndCheckout(String checkin, String checkout) {
        response = apiRequestHelper.getCallWithTwoQueryParametersResponse(bookingPath,"checkin",checkin,"checkout",checkout);
    }

    @When("the request is send to server with a booking id")
    public void theRequestIsSendToServerWithABookingId() {
        response = apiRequestHelper.getCallWithPathParametersResponse(bookingIdPath,booking_id);
    }


    @Then("validate the response for the status code {string}, content type {string} and Payload Body with {string} as first name, {string} as last name, {string} as total price, {string} as deposit paid, {string} as check in, {string} as check out and {string} as additional needs")
    public void validateTheResponseForTheStatusCodeContentTypeAndPayloadBody(String status_code, String content_type, String expected_firstname, String expected_lastname,
                                                                             String expected_totalprice, String expected_depositpaid,String expected_checkin, String expected_checkout, String expected_additionalneeds) {
        apiRequestHelper.validateStatusCodeAndContentType(Integer.parseInt(status_code), ContentType.valueOf(content_type));
        apiRequestHelper.logResponseBody();
        apiRequestHelper.validateTheFullResponseBody(expected_firstname,expected_lastname,Integer.parseInt(expected_totalprice),
                Boolean.parseBoolean(expected_depositpaid),expected_checkin,expected_checkout,expected_additionalneeds);
    }





    @Then("validate the Get request of the created booking with the status code {string}, content type {string} and Payload Body with {string} as first name, {string} as last name, {string} as total price, {string} as deposit paid, {string} as check in, {string} as check out and {string} as additional needs")
    public void validateTheGetRequestOfTheCreatedBookingWithTheStatusCodeContentTypeAndPayloadBody(String status_code, String content_type, String expected_firstname, String expected_lastname,
                                                                                                   String expected_totalprice, String expected_depositpaid,String expected_checkin, String expected_checkout, String expected_additionalneeds) {
        apiRequestHelper.getCallWithPathParametersResponse(bookingIdPath,booking_id);
        apiRequestHelper.validateStatusCodeAndContentType(Integer.parseInt(status_code), ContentType.valueOf(content_type));
        apiRequestHelper.logResponseBody();
        apiRequestHelper.validateTheFullResponseBody(expected_firstname,expected_lastname, Integer.parseInt(expected_totalprice),
                Boolean.parseBoolean(expected_depositpaid),expected_checkin,expected_checkout,expected_additionalneeds);
    }


    @Then("validate the responses of all booking ids with the status code {string} and content type {string}")
    public void validateTheResponsesOfAllBookingIdsWithTheStatusCodeAndContentType(String status_code, String content_type) {
        apiRequestHelper
                .validateStatusCodeAndContentType(Integer.parseInt(status_code), ContentType.valueOf(content_type));
    }



    @Then("validate the created booking id returned for the given first name and last name")
    public void validateTheCreatedBookingIdReturnedForTheGivenFirstNameAndLastName() {
        apiRequestHelper.logResponseBody();
        apiRequestHelper.validateIntValueForAGivenAttributeOfResponse(booking_id);

    }

    @Then("validate the created booking id returned for the given first name")
    public void validateTheCreatedBookingIdReturnedForTheGivenFirstName() {
        apiRequestHelper.logResponseBody();
        apiRequestHelper.validateIntValueForAGivenAttributeOfResponse(booking_id);
    }

    @And("validate the schema for all booking ids returned payload")
    public void validateTheAllBookingIdsAreNumbers() {
        apiRequestHelper.jsonSchemaValidator(bookingIdSchemaFilePath);
    }



    @When("the request send to the server to create a new booking with {string} as first name, {string} as last name, {string} as total price, {string} as deposit paid, {string} as check in, {string} as check out and {string} as additional needs")
    public void theRequestSendToTheServerToCreateANewBookingDetails(String firstname, String lastname, String totalprice,
                                                                    String depositpaid, String checkin, String checkout, String additionalneeds) {
        response = apiRequestHelper.postCallWithBodyResponse(bookingPath,firstname,lastname,
                Integer.parseInt(totalprice), Boolean.parseBoolean(depositpaid),checkin,checkout,additionalneeds);
    }


    @Then("validate the new booking must be created with the status code {string} and content type {string}")
    public void validateTheNewBookingMustBeCreatedWithTheStatusCodeAndContentType(String status_code, String content_type) {
        apiRequestHelper.validateStatusCodeAndContentType(Integer.parseInt(status_code), ContentType.valueOf(content_type));
        apiRequestHelper.logResponseBody();
        booking_id = apiRequestHelper.getIntValueFromTheBody(bookingIdKey);
        System.out.println("Booking Id : " + booking_id);
    }


    @When("the request is send to server to update a booking with {string} as first name, {string} as last name, {string} as total price, {string} as deposit paid, {string} as check in, {string} as check out and {string} as additional needs")
    public void theRequestIsSendToServerToUpdateABookingWithValues(String updated_firstnme, String updated_lastname,
                     String updated_totalprice,String updated_depositpaid,String updated_checkin, String updated_checkout, String updated_additionalneeds) {

        response = apiRequestHelper.putCallWithPathParametersWithValuesResponse(bookingIdPath,booking_id,token_id,updated_firstnme,
                updated_lastname,Integer.parseInt(updated_totalprice), Boolean.parseBoolean(updated_depositpaid),updated_checkin,updated_checkout,updated_additionalneeds);
    }


    @Then("validate the response for the status code {string}, content type {string} and updated values with {string} as first name, {string} as last name, {string} as total price, {string} as deposit paid, {string} as check in, {string} as check out and {string} as additional needs")
    public void validateTheResponseForTheStatusCodeContentTypeAndUpdatedValues(String status_code, String content_type, String updated_firstnme, String updated_lastname,
                                                                               String updated_totalprice, String updated_depositpaid, String updated_checkin, String updated_checkout, String updated_additionalneeds) {
        apiRequestHelper.validateStatusCodeAndContentType(Integer.parseInt(status_code), ContentType.valueOf(content_type));
        apiRequestHelper.logResponseBody();
        apiRequestHelper.validateTheFullResponseBody(updated_firstnme,updated_lastname,Integer.parseInt(updated_totalprice), Boolean.parseBoolean(updated_depositpaid),
                updated_checkin,updated_checkout,updated_additionalneeds);
    }

    @Then("validate the Get request of the updated booking with the status code {string}, content type {string} and Payload Body with {string} as first name, {string} as last name, {string} as total price, {string} as deposit paid, {string} as check in, {string} as check out and {string} as additional needs")
    public void validateTheGetRequestOfTheUpdatedBookingWithValues(String status_code, String content_type, String updated_firstname, String updated_lastname,
                                                                   String updated_totalprice, String updated_depositpaid, String updated_checkin, String updated_checkout, String updated_additionalneeds) {
        apiRequestHelper.validateStatusCodeAndContentType(Integer.parseInt(status_code), ContentType.valueOf(content_type));
        apiRequestHelper.logResponseBody();
        apiRequestHelper.validateTheFullResponseBody(updated_firstname,updated_lastname,Integer.parseInt(updated_totalprice),
                Boolean.parseBoolean(updated_depositpaid),updated_checkin,updated_checkout,updated_additionalneeds);

    }

    @Given("access token is generated")
    public void accessTokenIsGenerated() throws IOException {
        String authjsonBody = Util.generateStringFromResource(authCredentialJsonFilePath);
        response = apiRequestHelper.postCallResponse(authPath,authjsonBody);
        token_id = apiRequestHelper.getStringValueFromTheBody(tokenKey);
    }


    @When("the request is send to server to partially update a booking with {string} as first name and {string} as last name")
    public void theRequestIsSendToServerToPartiallyUpdateABookingWithValues(String updated_firstname, String updated_lastname) {
        response = apiRequestHelper.patchCallWithPathParametersResponse(bookingIdPath,booking_id,token_id,updated_firstname,updated_lastname);
    }


    @When("the request is send to server to delete a booking")
    public void theRequestIsSendToServerToDeleteABooking() {
        response = apiRequestHelper.deleteCallWithPathParametersResponse(bookingIdPath,booking_id,token_id);
    }


    @Then("validate the Get request of the deleted booking not found with the status code {string}, content type {string} and body {string}")
    public void validateTheGetRequestOfTheDeletedBooking(String status_code, String content_type, String message) {
        response = apiRequestHelper.getCallWithPathParametersResponse(bookingIdPath,booking_id);
        apiRequestHelper.validateStatusCodeAndContentType(Integer.parseInt(status_code), ContentType.valueOf(content_type));
        apiRequestHelper.validateTheResponseBodyText(message);
    }


    @Given("the created booking available with {string} as first name, {string} as last name, {string} as total price, {string} as deposit paid, {string} as check in, {string} as check out and {string} as additional needs")
    public void theCreatedBookingAvailable(String firstname, String lastname, String totalprice,
                                           String depositpaid, String checkin, String checkout, String additionalneeds) {
        response = apiRequestHelper.postCallWithBodyResponse(bookingPath,firstname,lastname,
                Integer.parseInt(totalprice), Boolean.parseBoolean(depositpaid),checkin,checkout,additionalneeds);
        booking_id = apiRequestHelper.getIntValueFromTheBody(bookingIdKey);
        System.out.println("Booking Id : " + booking_id);

    }

    @And("delete the created booking")
    public void deleteTheCreatedBooking() {
        String authjsonBody = null;
        try {
            authjsonBody = Util.generateStringFromResource(authCredentialJsonFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        response = apiRequestHelper.postCallResponse(authPath,authjsonBody);
        token_id = apiRequestHelper.getStringValueFromTheBody(tokenKey);

        apiRequestHelper.getBaseURL();
        response = apiRequestHelper.deleteCallWithPathParametersResponse(bookingIdPath,booking_id,token_id);

    }


    @When("the request is send to server with a non exists booking id {string}")
    public void theRequestIsSendToServerWithANonExistsBookingId(String booking_id) {
        response = apiRequestHelper.getCallWithPathParametersResponse(bookingIdPath, Integer.parseInt(booking_id));
    }



}
