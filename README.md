# A Harvest Java Client

[![CircleCI](https://circleci.com/gh/3AP-AG/harvest-client.svg?style=svg)](https://circleci.com/gh/3AP-AG/harvest-client)
[![Download](https://api.bintray.com/packages/mnembrini/3ap/harvest-client/images/download.svg) ](https://bintray.com/mnembrini/3ap/harvest-client/_latestVersion)

harvest-client is a Java client for the Harvest REST API (https://help.getharvest.com/api-v2/)

## Getting started with Development

### Prerequisites
* Java 8 (or higher)
* Gradle

### Run the build end tests
* Clone or fork this repository
* Configuring your Harvest account id and authentication token:
    * Either add the configuration to [reference.conf](src/main/resources/reference.conf)
    * Or set the following environment properties:
    ```bash
        $ export HARVEST_ACCOUNT_ID=YOUR_ACCT_ID
        $ export HARVEST_AUTH_TOKEN=YOUR_AUTH_TOKEN
    ```   
* Run ```gradle build``` in the root directory of the repository


## Introduction

With harvest-client you can use the Harvest v2 API with a nice Java interface:

```java
// Run with default configuration
Harvest harvest = new Harvest();
// list all users
List<User> users = harvest.users().list();

for (User user : users) {
    log.debug("Found user: {}", user);
}
// create new user
UserCreationInfo userInfo = new UserCreationInfo.Builder("testFirst", "testLast", "test@test.ch").build();

User newUser = harvest.users().create(userInfo);
```

Check out more [examples](https://github.com/3AP-AG/harvest-client/tree/develop/src/test/java/ch/aaap/harvestclient/examples) 



## Download

[![Download](https://api.bintray.com/packages/mnembrini/3ap/harvest-client/images/download.svg) ](https://bintray.com/mnembrini/3ap/harvest-client/_latestVersion)

### Gradle
```groovy
compile 'ch.aaap:harvest-client:$version'
```
### Maven
```xml
<dependency>
  <groupId>ch.aaap</groupId>
  <artifactId>harvest-client</artifactId>
  <version>$version</version>
  <type>pom</type>
</dependency>
```

   
