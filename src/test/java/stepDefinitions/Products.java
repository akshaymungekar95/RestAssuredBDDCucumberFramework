package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Products {

    public int responseCode;
    public RequestSpecification httpRequest;
    public Response response;
    public ResponseBody body;

    public JSONObject requestParams;

    public JsonPath jsnpath;

    String str;


    @Given("I hit the url of get products api endpoint")
    public void i_hit_the_url_of_get_products_api_endpoint() {
        RestAssured.baseURI = "https://fakestoreapi.com/";
        httpRequest = RestAssured.given();
    }

    @When("I pass the url of products in the request")
    public void i_pass_the_url_of_products_in_the_request() {
        response = httpRequest.get("products");
    }

    @Then("I receive the response code as {int}")
    public void i_receive_the_response_code_as(Integer statusCode) {
        responseCode = response.getStatusCode();
        assertEquals(responseCode, statusCode);
    }

    @Then("I verify that the rate of the first product is {string}")
    public void i_verify_that_the_rate_of_the_first_product_is(String rate) {
        body = response.getBody();

        String responseBody = body.asString();

        JsonPath jsonpath = response.jsonPath();

        String s = jsonpath.getJsonObject("rating[0].rate").toString();

        assertEquals(rate, s);
    }


    @And("I pass the request body of product title {string}")
    public void i_pass_the_request_body_of_the_product_title(String title) {
        requestParams = new JSONObject();

        requestParams.put("title", "shoes");
        requestParams.put("price", 13.5);
        requestParams.put("description", "Lorem ipsum test");
        requestParams.put("image", "https://i.test.cc");
        requestParams.put("category", "electronic");

        httpRequest.body(requestParams.toJSONString());
        response = httpRequest.post("products");
        body = response.getBody();

        jsnpath = response.jsonPath();

        str = jsnpath.getJsonObject("id").toString();

        System.out.println("******************"+body.asString()+"************"+str);

    }

    @Then("I receive the response body with id as {int}")
    public void i_receive_the_response_body_with_id_as(Integer id) {
        assertEquals(id, Integer.parseInt(str));
    }

    @When("I pass the request body of put api")
    public void iPassTheRequestBodyOfPutApi() {
        requestParams = new JSONObject();

        requestParams.put("title", "test product");
        requestParams.put("price", 14.5);
        requestParams.put("description", "Lorem ipsum test");
        requestParams.put("image", "https://i.test.cc");
        requestParams.put("category", "electronic");

        httpRequest.body(requestParams.toJSONString());
        response = httpRequest.put("products/"+6);
    }

    @And("I pass the url of products in the request with {int}")
    public void iPassTheUrlOfProductsInTheRequestWithProductnumber(int productNumber) {

        body = response.getBody();
        jsnpath = response.jsonPath();
        str = jsnpath.getJsonObject("id").toString();

        System.out.println("******************"+response.statusLine());
        System.out.println("******************"+body.asString()+"************"+str);
    }

    @When("I pass the url of delete products in the request with {int}")
    public void iPassTheUrlOfDeleteProductsInTheRequestWithProductNumber(int productNumber) {
        //httpRequest.body(requestParams.toJSONString());
        response = httpRequest.delete("products/"+6);
    }
}
