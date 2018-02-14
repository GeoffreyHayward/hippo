Feature: As a site user I need to be able to find and display legacy publications

    Scenario: Find (legacy) publication
        Given I navigate to the "home" page
        When I search for "legacy"
        And I click on link "Legacy Publications List"
        Then I should see page titled "Legacy Publications List"
        And I click on link "Legacy Publication"
        Then I should see page titled "Legacy Publication"
        And I should see:
            | Publication Geographic Coverage   | England and Scotland  |
            | Publication Date                  | 14 Feb 2018           |
