Feature: Account login

  Scenario Outline: Login successfully
    Given User register new account
      | username | <username> |
      | fullname | <fullname> |
      | password | <password> |
      | address  | <address>  |
      | birthday | <birthday> |

    When User want to login with username "<username>" and password "<password>"
    Then User logins successfully
    Examples:
      | fullname                             | username                                   | password                           | address        | birthday   |
      | User ${random_length_8_alphabetic_1} | ${random_length_12_alphabetic_2}@email.com | ${random_length_12_alphanumeric_3} | 123 Le Duc Tho | 03/03/1988 |


