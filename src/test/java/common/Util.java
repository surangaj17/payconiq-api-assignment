package common;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Util {

    public static String generateStringFromResource(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static String getValueForAGivenKeyFromJsonFile(String filepath, String key) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(filepath));
        JSONObject jsonObject =  (JSONObject) obj;
        return (String) jsonObject.get(key);

    }

    public static JSONObject getJsonObjectFromFile(String filepath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(filepath));
        return (JSONObject) obj;
    }

    public static HashMap getPayloadValuesFromJsonFile (String filepath)  {

        JSONParser parser = new JSONParser();
        JSONObject JsonObj = null;
        HashMap<String,Object> bookingData = new HashMap<>();
        try {
            JsonObj = Util.getJsonObjectFromFile(filepath);
            JSONObject JsonDatesObj = null;
            JsonDatesObj = (JSONObject) parser.parse(JsonObj.get("bookingdates").toString());

            bookingData.put("firstname",JsonObj.get("firstname").toString());
            bookingData.put("lastname",JsonObj.get("lastname").toString());
            bookingData.put("totalprice",Integer.parseInt(JsonObj.get("totalprice").toString()));
            bookingData.put("depositpaid",Boolean.parseBoolean(JsonObj.get("depositpaid").toString()));
            bookingData.put("checkin",JsonDatesObj.get("checkin").toString());
            bookingData.put("checkout",JsonDatesObj.get("checkout").toString());
            bookingData.put("additionalneeds",JsonObj.get("additionalneeds").toString());

            //String  firstname = JsonObj.get("firstname").toString();
            //String  lastname = JsonObj.get("lastname").toString();
           // int  totalprice = Integer.parseInt(JsonObj.get("totalprice").toString());
           // boolean  depositpaid = Boolean.parseBoolean(JsonObj.get("depositpaid").toString());
           // String  checkin = JsonDatesObj.get("checkin").toString();
           // String  checkout = JsonDatesObj.get("checkout").toString();
           // String  additionalneeds = JsonObj.get("additionalneeds").toString();

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return bookingData;
    }
}
