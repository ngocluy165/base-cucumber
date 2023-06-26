Feature: Account login

  Scenario Outline: Login successfully
    Given User register new account
      | username | <username> |
      | fullname | <fullname> |
      | password | <password> |
      | address  | <address>  |
      | birthday | <birthday> |

    Given I have a cache with a variable "user_id_1" and String value "${responses[-1].id}"

    When User want to login with username "<username>" and password "<password>"
    Then User logins successfully


    When As a login user, I want to create a new Shop with payload in below table
      | name    | Shop ${random_length_8_alphabetic_4}                                                   |
      | address | Address ${random_length_8_alphabetic_5} Ward, ${random_length_8_alphabetic_6} District |
    Then The shop was created successfully
    Then I have a cache with a variable "shop_id_1" and String value "${responses[-1].id}"

    When As a login user, I want to create a new Shop with payload in below table
      | name    | Shop 2 |
      | address | Addr 2 |
    Then The shop was created successfully
    Then I have a cache with a variable "shop_id_2" and String value "${responses[-1].id}"


    When As a login user, I fetch the shop with id "${variable.shop_id_1}"
    Then The shop has data as below table
      | id   | ${variable.shop_id_1}                |
      | name | Shop ${random_length_8_alphabetic_4} |

    When As a login user, I fetch the shop with id "${variable.shop_id_2}"
    Then The shop has data as below table
      | id      | ${variable.shop_id_2} |
      | name    | Shop 2                |
      | address | Addr 2                |

    When As a login user, I want to delete the Shop with id "${variable.shop_id_1}"
    Then The response of previous request has status code is 200

    Examples:
      | fullname                             | username                                   | password                           | address        | birthday   |
      | User ${random_length_8_alphabetic_1} | ${random_length_12_alphabetic_2}@email.com | ${random_length_12_alphanumeric_3} | 123 Le Duc Tho | 03/03/1988 |


