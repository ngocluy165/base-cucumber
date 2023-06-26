Feature: Test API

  Scenario: Test call API

    When I make GET Rest API call to url "https://cat-fact.herokuapp.com/facts/"
    Then The response of previous request has status code is 200
    When I make GET Rest API call to url "https://hungnt.free.beeceptor.com/todo/notfound"
    Then The response of previous request has status code is 404
    When I make POST Rest API call to url "https://hungnt.free.beeceptor.com/todos" with payload
    """
      {
      "todo": "abc"
      }
    """
    Then The response of previous request has status code is 200 and payload is
    """
      {
      "id":6,
      "title":"Complete all the use-cases for faster API design and testing with Beeceptor.",
      "completed":true,
      "details":[{"step":"step 1"},{"step":"step 2"}]
      }
    """

    Then The response of previous request has status code is 200 and payload as below json path table
      | id        | 6                                                                            |
      | title     | Complete all the use-cases for faster API design and testing with Beeceptor. |
      | completed | true                                                                         |
    And The response of previous request has payload as below json path table
      | id      | 6                                                                            |
      | title   | Complete all the use-cases for faster API design and testing with Beeceptor. |
      | details | [{"step":"step 1"},{"step":"step 2"}]                                        |
    And The response of previous request has payload as below json path table
      | title      | Complete all the use-cases for faster API design and testing with Beeceptor. |
      | details[0] | {"step":"step 1"},{"step":"step 2"}                                          |
