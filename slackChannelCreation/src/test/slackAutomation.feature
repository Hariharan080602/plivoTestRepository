Feature : Slack channel creation automation

  @Positive
  Scenario: Successfully create a public channel
    When user create a channel
    Then channel should be created successfully

  @Positive
  Scenario: Create a new channel and join the channel successfully
    Given user create a channel
    Then channel should be created successfully
    And user joins the created channel
    Then user verifies they joined the channel correctly

  @Positive
  Scenario: Create a new channel and rename the channel name
    Given user create a channel
    Then channel should be created successfully
    And user tries to rename the created channel

  @Negative
  Scenario: Create a new public channel by using an invalid auth token
    When user create a channel with invalid auth token
    Then channel should not be created successfully
