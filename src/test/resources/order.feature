Feature: Generate order
  Scenario: Generating and processing an order
    Given an order is published
    When it's processed
    Then the order listener consumer the message

