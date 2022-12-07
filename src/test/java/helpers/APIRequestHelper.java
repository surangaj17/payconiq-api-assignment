package helpers;

import common.ConfigFileReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.Assert;
import common.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pojo.BookingDates;
import pojo.BookingIds;
import pojo.Bookings;
import pojo.PatchBookings;

import static org.hamcrest.MatcherAssert.assertThat;


import javax.swing.text.html.parser.Entity;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.IsIterableContaining.hasItem;
import static org.hamcrest.core.IsIterableContaining.hasItems;

public class APIRequestHelper {

        Response response;
    ConfigFileReader configFileReader= new ConfigFileReader("Config");


    public void getBaseURL(){

        RestAssured.baseURI= configFileReader.getBaseUrl();
    }

    public Response getCallResponse(String path){
        RestAssured.basePath=path;
        response = given().
                when().
                get().
                then().
                extract().response();
        return response;
    }

    public Response getCallWithTwoQueryParametersResponse(String path, String first_param_name, String first_param_value, String second_param_name,String second_param_value){
        RestAssured.basePath=path;
        response = given().
                queryParam(first_param_name,first_param_value).
                queryParam(second_param_name,second_param_value).
                when().
                get().
                then().
                extract().response();
        return response;
    }

    public Response getCallWithOneQueryParametersResponse(String bookingPath, String param_name, String param_value) {
        RestAssured.basePath=bookingPath;
        response = given().
                queryParam(param_name,param_value).
                when().
                get().
                then().
                extract().response();
        return response;
    }

    public Response getCallWithPathParametersResponse(String path, int booking_id){
        RestAssured.basePath=path;
        response = given().
                pathParam("id",booking_id).
                when().
                get().
                then().
                extract().response();
        return response;
    }

    public void validateIntValueForAGivenAttributeOfResponse(int expected_value){
        response.then().body("bookingid",hasItem(expected_value));

    }

    public void validateStatusCodeAndContentType(int status_code, ContentType content_type){
        response
                .then()
                .statusCode(status_code)
                .contentType(content_type);
    }

    public void logResponseBody(){
        response.then().log().all();
    }

    public Response postCallResponse(String path, String jsonBody ){
        RestAssured.basePath=path;
        response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post()
                .then()
                .extract().response();
        return response;
    }

    public Response postCallWithBodyResponse(String path, String firstname, String lastname, int totalprice,
                                             boolean depositpaid, String checkin, String checkout, String additionalneeds ){
        RestAssured.basePath=path;
        BookingDates bookingDates = new BookingDates(checkin,checkout);
        Bookings bookings = new Bookings(firstname,lastname,totalprice,depositpaid,bookingDates,additionalneeds);

        System.out.println();

        response = given()
                .contentType(ContentType.JSON)
                .body(bookings)
                .when()
                .post()
                .then()
                .extract().response();
        return response;
    }


    public Response putCallWithPathParametersResponse(String path,int booking_id,String token_id, String jsonBody ){



        RestAssured.basePath=path;
        response = given()
                .pathParam("id",booking_id)
                .contentType(ContentType.JSON)
                .header("Cookie","token="+token_id)
                .body(jsonBody)
                .when()
                .put()
                .then()
                .extract().response();
        return response;
    }

    public Response putCallWithPathParametersWithValuesResponse(String path,int booking_id,String token_id, String updated_firstname, String updated_lastname,int updated_totalprice,boolean updated_depositpaid, String updated_checkin, String updated_checkout, String updated_additionalneeds){

        BookingDates bookingDates = new BookingDates(updated_checkin,updated_checkout);
        Bookings bookings = new Bookings(updated_firstname,updated_lastname,updated_totalprice,updated_depositpaid,bookingDates,updated_additionalneeds);

        RestAssured.basePath=path;
        response = given()
                .pathParam("id",booking_id)
                .contentType(ContentType.JSON)
                .header("Cookie","token="+token_id)
                .body(bookings)
                .when()
                .put()
                .then()
                .extract().response();
        return response;
    }

    public Response patchCallWithPathParametersResponse(String path,int booking_id,String token_id, String updated_firstname, String updated_lastname ){
        PatchBookings patchBookings = new PatchBookings(updated_firstname,updated_lastname);

        RestAssured.basePath=path;
        response = given()
                .pathParam("id",booking_id)
                .contentType(ContentType.JSON)
                .header("Cookie","token="+token_id)
                .body(patchBookings)
                .when()
                .patch()
                .then()
                .extract().response();
        return response;
    }

    public Response patchCallWithPathParametersResponse(String path,int booking_id,String token_id, String jsonBody ){

        RestAssured.basePath=path;
        response = given()
                .pathParam("id",booking_id)
                .contentType(ContentType.JSON)
                .header("Cookie","token="+token_id)
                .body(jsonBody)
                .when()
                .patch()
                .then()
                .extract().response();
        return response;
    }

    public Response deleteCallWithPathParametersResponse(String path,int booking_id,String token_id){

        RestAssured.basePath=path;
        response = given()
                .pathParam("id",booking_id)
                .contentType(ContentType.JSON)
                .header("Cookie","token="+token_id)
                .when()
                .delete()
                .then()
                .extract().response();
        return response;
    }

    public void validateTheResponseBody(String filepath) {
        JSONParser parser = new JSONParser();
        JSONObject expectedJsonObject,actualJsonObject = null;
        try {
            expectedJsonObject = Util.getJsonObjectFromFile(filepath);
            actualJsonObject = (JSONObject) parser.parse(response.body().asString());
            Assert.assertEquals(expectedJsonObject,actualJsonObject);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void validateTheFullResponseBody(String firstname, String lastname, int totalprice,boolean depositpaid,
                                            String checkin, String checkout, String additionalneeds ) {
        Bookings bookings = response.getBody().as(Bookings.class);
        assertThat(bookings.getFirstname(),equalTo(firstname));
        assertThat(bookings.getLastname(),equalTo(lastname));
        assertThat(bookings.getTotalprice(),equalTo(totalprice));
        assertThat(bookings.isDepositpaid(),equalTo(depositpaid));
        assertThat(bookings.getBookingdates().getCheckin(),equalTo(checkin));
        assertThat(bookings.getBookingdates().getCheckout(),equalTo(checkout));
        assertThat(bookings.getAdditionalneeds(),equalTo(additionalneeds));
    }

    public void validateTheFullResponseBody2(String firstname, String lastname, int totalprice,boolean depositpaid,
                                             String additionalneeds ) {
        Bookings bookings = response.getBody().as(Bookings.class);
        assertThat(bookings.getFirstname(),equalTo(firstname));
        assertThat(bookings.getLastname(),equalTo(lastname));
        assertThat(bookings.getTotalprice(),equalTo(totalprice));
        assertThat(bookings.isDepositpaid(),equalTo(depositpaid));
      //  assertThat(bookings.getBookingdates().getCheckin(),equalTo(checkin));
      //  assertThat(bookings.getBookingdates().getCheckout(),equalTo(checkout));
        assertThat(bookings.getAdditionalneeds(),equalTo(additionalneeds));
    }

    public void validateTheResponseBodyText(String message){
        String actualMessage = response.asString();
        Assert.assertEquals(message,actualMessage );
    }

    public int getIntValueFromTheBody(String key){
        return response.path(key);
    }

    public String getStringValueFromTheBody(String key){
        return response.path(key);
    }

    public void jsonSchemaValidator(String bookingIdSchemaFilePath) {

        response.
                then().
                body(JsonSchemaValidator.matchesJsonSchema(new File(bookingIdSchemaFilePath)));
    }


}
