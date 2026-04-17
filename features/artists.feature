Feature: Artists (Artists, albums and songs management)

DESCRIPTION 

Allows artists and admins to register, edit and remove artist accounts. Allows artists and admins to register, edit and remove albums and songs. Every album must have at least one song. Removing an artist or album also removes all dependent content.

SCENARIOS

Scenario: successful album registration with songs
  Given I am logged in as artist "Vivaldi"
  And I am at the "Publish Album" page
  When I fill in "Name" with "Four Seasons"
  And I fill in "Genre" with "Baroque"
  And I fill in "Release Date" with "1725-01-01"
  And I add a song with name "Winter", genre "Baroque" and file "winter.mp3"
  And I click "Publish"
  Then I can see the message "Album published successfully"
  And the album "Four Seasons" is indexed on the platform

Scenario: unsuccessful album registration without songs
  Given I am logged in as artist "Vivaldi"
  And I am at the "Publish Album" page
  When I fill in "Name" with "Four Seasons"
  And I fill in "Genre" with "Baroque"
  And I fill in "Release Date" with "1725-01-01"
  And I click "Publish"
  Then I can see the error message "An album must have at least one song"
  And the album "Four Seasons" is not indexed on the platform

Scenario: unsuccessful album edit with empty name
  Given I am logged in as artist "Vivaldi"
  And there is an album "Four Seasons" published by "Vivaldi"
  And I am at the "Edit Album" page for "Four Seasons"
  When I clear the field "Name"
  And I click "Save"
  Then I can see the error message "Album name cannot be empty"
  And the album remains listed as "Four Seasons"

Scenario: unsuccessful artist registration with duplicate login
  Given I am at the "Register" page
  When I fill in "Login" with "vivaldi"
  And I fill in "Password" with "ilPetreRosso"
  And I check "I want to publish music"
  And I click "Register"
  Then I can see the error message "This login is already in use"
  And the artist account is not created
  And the "Login" field is highlighted in red

  Scenario: unsuccessful album registration by non-artist user
    Given I am logged in as listener "Carlos"
    When I try to publish an album named "Four Seasons" with genre "Baroque",
      release date "1725-01-01" and song "Winter" with file "winter.mp3"
    Then I can see the error message "You do not have permission to publish albums"
    And the album "Four Seasons" is not indexed on the platform

Scenario: successful album registration with multiple artists
  Given I am logged in as artist "Vivaldi"
  When I try to publish an album named "Baroque Classics" with genre "Baroque",
    release date "1725-01-01", song "Spring" with file "spring.mp3"
    and collaborator "Bach"
  Then I can see the message "Album published successfully"
  And the album "Baroque Classics" is indexed on the platform
  And the album "Baroque Classics" is listed under "Bach" and "Vivaldi"

