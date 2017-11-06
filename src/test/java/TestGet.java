import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.http.Method;
import org.codehaus.jackson.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class TestGet {

    @Test
    public void testGetCityWeatherFrankfurt() throws URISyntaxException {


        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = BaseClass.WEATHER_FORECAST;

        // Get the RequestSpecification of the request that you want to sent
        // to the server. The server is specified by the BaseURI that we have
        // specified in the above step.
        RequestSpecification httpRequest = RestAssured.given();

        // Make a request to the server by specifying the method Type and the method URL.
        // This will return the Response from the server. Store the response in a variable.
        Response response = httpRequest.accept(ContentType.JSON).request(Method.GET, "/Frankfurt");

        // Now let us print the body of the message to see what response
        // we have recieved from the server
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);

        //Check the Response Code
        // If it is 200
        System.out.println("Response Status Code =>  " + response.statusCode());
        Assert.assertEquals(HttpStatus.SC_OK, response.statusCode());


        // Reader header of a give name. In this line we will get
        // Header named Content-Type
        String contentType = response.header("Content-Type");
        System.out.println("Content-Type value: " + contentType);
        Assert.assertEquals(contentType /* actual value */, "application/json" /* expected value */);

        // Reader header of a give name. In this line we will get
        // Header named Server
        String serverType =  response.header("Server");
        System.out.println("Server value: " + serverType);
        Assert.assertEquals(serverType /* actual value */, "nginx/1.12.2" /* expected value */);

        // Reader header of a give name. In this line we will get
        // Header named Content-Encoding
        String acceptLanguage = response.header("Content-Encoding");
        System.out.println("Content-Encoding: " + acceptLanguage);
        Assert.assertEquals(acceptLanguage /* actual value */, "gzip" /* expected value */);

        //Read and check the current values from the request
        // First get the JsonPath object instance from the Response interface
        JsonPath jsonPathEvaluator = response.jsonPath();

        // Then simply query the JsonPath object to get a String value of the node
        // specified by JsonPath: City (Note: You should not put $. in the Java code)
        String city = jsonPathEvaluator.get("City");
        String temperature = jsonPathEvaluator.get("Temperature");
        String humidity = jsonPathEvaluator.get("Humidity");

        // Let us print the city variable to see what we got
        System.out.println("City received from Response " + city);
        System.out.println("Temperature received from Response " + temperature);
        System.out.println("Humidity received from Response " + humidity);

        // Validate the response
        Assert.assertEquals(city, "Frankfurt am Main", "Correct city name received in the Response");
        Assert.assertEquals(temperature, "8.12 Degree celsius", "Correct city name received in the Response");
        Assert.assertEquals(humidity, "93 Percent", "Correct city name received in the Response");

        //Useful links for JSON Path
        // http://toolsqa.com/rest-assured/read-response-body-using-rest-assured/
        //http://toolsqa.com/rest-assured/read-response-body-using-rest-assured/
        //http://www.groupkt.com/post/f2129b88/free-restful-web-services-to-consume-and-test.htm

    }

    @Test
    public void testPut() throws URISyntaxException {

        //Build JSON request object
        ObjectMapper mapper = new ObjectMapper();

        ArrayNode arrayNode = mapper.createArrayNode();

        /**
         * Create three JSON Objects objectNode1, objectNode2, objectNode3
         * Add all these three objects in the array
         */

        ObjectNode objectNode1 = mapper.createObjectNode();
        objectNode1.put("name", "Szabolcs");
        objectNode1.put("job", "leader");

        //ObjectNode objectNode2 = mapper.createObjectNode();
        //objectNode2.put("bookName", "Spring");
        //objectNode2.put("price", "200");

        //ObjectNode objectNode3 = mapper.createObjectNode();
        //objectNode3.put("bookName", "Liferay");
        //objectNode3.put("price", "500");

        /**
         * Array contains JSON Objects
         */
        arrayNode.add(objectNode1);
        //arrayNode.add(objectNode2);
        //arrayNode.add(objectNode3);

        /**
         * We can directly write the JSON in the console.
         * But it wont be pretty JSON String
         */
        System.out.println(arrayNode.toString());



        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = BaseClass.REGISTER_USER;

        // Get the RequestSpecification of the request that you want to sent
        // to the server. The server is specified by the BaseURI that we have
        // specified in the above step.
        RequestSpecification httpRequest = RestAssured.given();

        // Make a request to the server by specifying the method Type and the method URL.
        // This will return the Response from the server. Store the response in a variable.
        Response response = httpRequest.accept(ContentType.JSON).body(arrayNode.toString()).when().request(Method.POST);

        // Now let us print the body of the message to see what response
        // we have recieved from the server
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);

    }
}
