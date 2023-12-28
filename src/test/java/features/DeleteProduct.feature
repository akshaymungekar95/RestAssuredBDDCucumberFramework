Feature: Delete products using DELETE api

  Scenario Outline: Validate post api for adding new products
    Given I hit the url of get products api endpoint
    When I pass the url of delete products in the request with <ProductNumber>
    Then I receive the response code as 200
    Examples:
      | ProductNumber |
      | 6 |