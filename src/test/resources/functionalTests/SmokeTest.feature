@SmokeTest
Feature: Smoke verification of the URL - https://restful-booker.herokuapp.com for HTTP methods GET, POST, PUT, PATCH and DELETE for booking end point

  Background : Health Check on the environment
    Given Health Check on the environment is successful with the status code "201", content type "TEXT" and body "Created"

  Scenario Outline: TC-0001 : Get All Booking IDs
    Given the valid base URL
    When the request is send to server
    Then validate the responses of all booking ids with the status code "<statuscode>" and content type "<contenttype>"
    Examples:
      | statuscode | contenttype |
      | 200        | JSON        |

  Scenario Outline: TC-0002 : Get All Booking IDs filter by First Name/Last Name
    Given the created booking available with "<firstnme>" as first name, "<lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    Given the valid base URL
    When the request is send to server with query parameters "<firstnme>" , "<lastname>" as First name and Last name
    Then validate the responses of all booking ids with the status code "<statuscode>" and content type "<contenttype>"
    Then validate the created booking id returned for the given first name and last name
    And delete the created booking
    Examples:
      | firstnme | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds | statuscode | contenttype |
      | Charith  | Asalanka | 250        | true        | 2022-04-01 | 2022-05-01 | Lunch           | 200        | JSON        |

  Scenario Outline: TC-0003 : Get All Booking IDs filter by Checkin/Checkout dates
    Given the created booking available with "<firstnme>" as first name, "<lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    Given the valid base URL
    When the request is send to server with query parameters "<checkin>" , "<checkout>" as checkin and checkout
    Then validate the responses of all booking ids with the status code "<statuscode>" and content type "<contenttype>"
    Then validate the created booking id returned for the given first name and last name
    And delete the created booking
    Examples:
      | firstnme | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds | statuscode | contenttype |
      | Charith  | Asalanka | 250        | true        | 2022-04-01 | 2022-05-01 | Lunch           | 200        | JSON        |

  Scenario Outline: TC-0004 : Create a Booking
    Given the valid base URL
    When the request send to the server to create a new booking with "<firstnme>" as first name, "<lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    Then validate the new booking must be created with the status code "<statuscode>" and content type "<contenttype>"
    Then validate the Get request of the created booking with the status code "<statuscode>", content type "<contenttype>" and Payload Body with "<firstnme>" as first name, "<lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    And delete the created booking
    Examples:
      | firstnme | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds | statuscode | contenttype |
      | Charith  | Asalanka | 250        | true        | 2022-04-01 | 2022-05-01 | Lunch           | 200        | JSON        |

  Scenario Outline: TC-0005 : Get Booking by ID
    Given the created booking available with "<firstnme>" as first name, "<lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    Given the valid base URL
    When the request is send to server with a booking id
    Then validate the response for the status code "<statuscode>", content type "<contenttype>" and Payload Body with "<firstnme>" as first name, "<lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    And delete the created booking
    Examples:
      | firstnme | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds | statuscode | contenttype |
      | Charith  | Asalanka | 250        | true        | 2022-04-01 | 2022-05-01 | Lunch           | 200        | JSON        |

  Scenario Outline: TC-0006 : Update Booking by ID with valid Cookie token in the header
    Given access token is generated
    Given the created booking available with "<firstnme>" as first name, "<lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    Given the valid base URL
    When the request is send to server to update a booking with "<updated_firstnme>" as first name, "<updated_lastname>" as last name, "<updated_totalprice>" as total price, "<updated_depositpaid>" as deposit paid, "<updated_checkin>" as check in, "<updated_checkout>" as check out and "<updated_additionalneeds>" as additional needs
    Then validate the response for the status code "<statuscode>", content type "<contenttype>" and updated values with "<updated_firstnme>" as first name, "<updated_lastname>" as last name, "<updated_totalprice>" as total price, "<updated_depositpaid>" as deposit paid, "<updated_checkin>" as check in, "<updated_checkout>" as check out and "<updated_additionalneeds>" as additional needs
    Then validate the Get request of the updated booking with the status code "<statuscode>", content type "<contenttype>" and Payload Body with "<updated_firstnme>" as first name, "<updated_lastname>" as last name, "<updated_totalprice>" as total price, "<updated_depositpaid>" as deposit paid, "<updated_checkin>" as check in, "<updated_checkout>" as check out and "<updated_additionalneeds>" as additional needs
    And delete the created booking
    Examples:
      | firstnme | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds | updated_firstnme | updated_lastname | updated_totalprice | updated_depositpaid | updated_checkin | updated_checkout | updated_additionalneeds | statuscode | contenttype |
      | Charith  | Asalanka | 250        | true        | 2022-04-01 | 2022-05-01 | Lunch           | Pathum           | Nissanka         | 450                | false               | 2022-03-15      | 2022-04-22       | Lunch                   | 200        | JSON        |

  Scenario Outline: TC-0007 : Partially Update Booking by ID with valid Cookie token in the header
    Given access token is generated
    Given the created booking available with "<firstnme>" as first name, "<lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    Given the valid base URL
    When the request is send to server to partially update a booking with "<updated_firstnme>" as first name and "<updated_lastname>" as last name
    Then validate the response for the status code "<statuscode>", content type "<contenttype>" and updated values with "<updated_firstnme>" as first name, "<updated_lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    Then validate the Get request of the updated booking with the status code "<statuscode>", content type "<contenttype>" and Payload Body with "<updated_firstnme>" as first name, "<updated_lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    And delete the created booking
    Examples:
      | firstnme | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds | updated_firstnme | updated_lastname | statuscode | contenttype |
      | Charith  | Asalanka | 250        | true        | 2022-04-01 | 2022-05-01 | Lunch           | Kusal            | Nissanka         | 200        | JSON        |

  Scenario Outline: TC-0008 : Delete Booking by ID with valid Cookie token in the header
    Given access token is generated
    Given the created booking available with "<firstnme>" as first name, "<lastname>" as last name, "<totalprice>" as total price, "<depositpaid>" as deposit paid, "<checkin>" as check in, "<checkout>" as check out and "<additionalneeds>" as additional needs
    Given the valid base URL
    When the request is send to server to delete a booking
    Then validate the response for the status code "<delete_statuscode>", content type "<delete_contenttype>" and body "<message>"
    Then validate the Get request of the deleted booking not found with the status code "<get_statuscode>", content type "<get_contenttype>" and body "<get_message>"
    Examples:
      | firstnme | lastname | totalprice | depositpaid | checkin    | checkout   | additionalneeds | delete_statuscode | delete_contenttype | message | get_statuscode | get_contenttype | get_message |
      | Charith  | Asalanka | 250        | true        | 2022-04-01 | 2022-05-01 | Lunch           | 201               | TEXT               | Created | 404            | TEXT            | Not Found   |
