@RegressionTest
Feature: Regression verification of the URL - https://restful-booker.herokuapp.com for HTTP methods GET, POST, PUT, PATCH and DELETE for booking end point

  Background : Health Check on the environment
    Given Health Check on the environment is successful with the status code "201", content type "TEXT" and body "Created"

  Scenario Outline: TC-0009 : Get All Booking ID validate the return ID
    Given the valid base URL
    When the request is send to server
    Then validate the responses of all booking ids with the status code "<status_code>" and content type "<content_type>"
    And validate the schema for all booking ids returned payload
    Examples:
      | status_code | content_type |
      | 200         | JSON         |

  Scenario Outline: TC-0010 : Get All Booking IDs filter by First Name only
    Given the created booking available with "<firstnme>" as first name, "<lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    Given the valid base URL
    When the request is send to server with query parameters "<firstnme>" as First name
    Then validate the responses of all booking ids with the status code "<statuscode>" and content type "<contenttype>"
    Then validate the created booking id returned for the given first name
    And delete the created booking
    Examples:
      | firstnme | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds | statuscode | contenttype |
      | Charith  | Asalanka | 250        | true        | 2022-04-01 | 2022-05-01 | Lunch           | 200        | JSON        |

  Scenario Outline: TC-0011 : Get All Booking IDs filter by Last Name only
    Given the created booking available with "<firstnme>" as first name, "<lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    Given the valid base URL
    When the request is send to server with query parameters "<lastname>" as Last name
    Then validate the responses of all booking ids with the status code "<statuscode>" and content type "<contenttype>"
    Then validate the created booking id returned for the given first name
    And delete the created booking
    Examples:
      | firstnme | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds | statuscode | contenttype |
      | Charith  | Asalanka | 250        | true        | 2022-04-01 | 2022-05-01 | Lunch           | 200        | JSON        |


  Scenario Outline: TC-0012 : Get Booking by ID for a non exists ID
    Given the valid base URL
    When the request is send to server with a booking id
    When the request is send to server with a non exists booking id "<bookingid>"
    Then validate the response for the status code "<statuscode>", content type "<contenttype>" and body "<message>"
    And delete the created booking
    Examples:
      | bookingid | statuscode | contenttype | message   |
      | 254850000 | 404        | TEXT        | Not Found |

  Scenario Outline: TC-0013 : Crerate Booking for different positive data combibnations
    Given the valid base URL
    When the request send to the server to create a new booking with "<firstnme>" as first name, "<lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    Then validate the new booking must be created with the status code "<statuscode>" and content type "<contenttype>"
    Then validate the Get request of the created booking with the status code "<statuscode>", content type "<contenttype>" and Payload Body with "<firstnme>" as first name, "<lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    And delete the created booking
    Examples:
      | firstnme                   | lastname                     | totalprice | depositpaid | checkin    | checkout   | additionalneeds | statuscode | contenttype |
      | Ab                         | De                           | 2500000    | true        | 2021-04-01 | 2022-05-01 | No Meal         | 200        | JSON        |
      | Abersasffeeasssaddaasasasa | Dedadasdasdasdsadasdasdsaaas | 25         | false       | 2021-04-01 | 2022-05-01 | No Meal         | 200        | JSON        |

  Scenario Outline: TC-0014 : Create Booking for different negative data combibnations
    Given the valid base URL
    When the request send to the server to create a new booking with "<firstnme>" as first name, "<lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    Then validate the new booking must be created with the status code "<statuscode>" and content type "<contenttype>"
    Then validate the Get request of the created booking with the status code "<statuscode>", content type "<contenttype>" and Payload Body with "<firstnme>" as first name, "<lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    And delete the created booking
    Examples:
      | firstnme | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds | statuscode | contenttype |
      | 213123   | 123213   | 2500000    | true        | 2021-04-01 | 2022-05-01 | No Meal         | 400        | JSON        |
      | !@#!@#   | #!@#!!   | 25         | false       | 2021-04-01 | 2022-05-01 | Meal            | 400        | JSON        |
      | Diloand  | Keessaa  | 25         | 0           | 2021-04-01 | 2022-05-01 | Dinner          | 400        | JSON        |
      | Diloand  | Keessaa  | -25        | 1           | 2021-04-01 | 2022-05-01 | Lunch           | 400        | JSON        |

