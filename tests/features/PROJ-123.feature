Feature: Implement user authentication feature
  As a user
  I want to be able to log in and log out of the application securely
  So that my data is protected

  Background:
    Given the application is running

  Scenario: User can register with email and password
    Given the registration form is displayed
    When the user submits a valid email and password
    Then a new account is created
    And the user is notified of successful registration

  Scenario: User can log in with valid credentials
    Given the user has a registered account
    When the user submits valid login credentials
    Then the user is authenticated and granted access

  Scenario: User can log out successfully
    Given the user is logged in
    When the user initiates logout
    Then the user is logged out and redirected to the public page

  Scenario: Password reset functionality is available
    Given the user has forgotten their password
    When the user requests a password reset and follows the reset flow
    Then the user receives a reset link and can set a new password
