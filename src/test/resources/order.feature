Feature: Generate order
  Scenario: Generating and processing an order with all fields valid
    Given an order is published
    Then the order listener consumer the message

#  Scenario: Generating and processing an order with id as null
#    Given another order with an id as null is published
#    Then the order listener consumer and throw exception

