# A Harvest Java Client

[![CircleCI](https://circleci.com/gh/3AP-AG/harvest-client.svg?style=svg)](https://circleci.com/gh/3AP-AG/harvest-client)
[![Download](https://api.bintray.com/packages/mnembrini/3ap/harvest-client/images/download.svg) ](https://bintray.com/mnembrini/3ap/harvest-client/_latestVersion)
[![codecov](https://codecov.io/gh/3AP-AG/harvest-client/branch/develop/graph/badge.svg)](https://codecov.io/gh/3AP-AG/harvest-client)
[![Known Vulnerabilities](https://snyk.io/test/github/3ap-ag/harvest-client/badge.svg?targetFile=build.gradle)](https://snyk.io/test/github/3ap-ag/harvest-client?targetFile=build.gradle)

harvest-client is a Java client for the Harvest REST API (https://help.getharvest.com/api-v2/)

[Javadoc](https://3ap-ag.github.io/harvest-client/)

## Introduction

With harvest-client you can use the Harvest v2 API with a nice Java interface:

```java
// Load configuration from application.conf in src/main/resources of your application
Harvest harvest = new Harvest();
// list all users
List<User> users = harvest.users().list();

for (User user : users) {
    log.debug("Found user: {}", user);
}
// create new user
User userInfo = ImmutableUser.builder()
                .firstName("first")
                .lastName("last")
                .email("test@example.com")
                .build();

User newUser = harvest.users().create(userInfo);
```

Have a look in the test folders for [examples](https://github.com/3AP-AG/harvest-client/tree/develop/src/test/java/ch/aaap/harvestclient/impl/).
Each API endpoint has a Create, List and Update test class. 

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
</dependency>
```

## Getting started with Development

### Prerequisites
* Java 8 (or higher)
* Gradle

### Run the build end tests
* Clone or fork this repository
* Get a personal access token [from Harvest](https://id.getharvest.com/developers)
    * Attention: unit tests assume admin access. Don't run them against a production account!
* Configuring your Harvest account id and authentication token:
    * Either copy [reference.conf](src/main/resources/reference.conf) to src/test/resources/admin.conf and insert your credentials
    * Or set the following environment properties:
    ```bash
        $ export HARVEST_ACCOUNT_ID=YOUR_ACCT_ID
        $ export HARVEST_AUTH_TOKEN=YOUR_AUTH_TOKEN
    ```   
* Run ```gradle build``` in the root directory of the repository

### Debugging

You can see the actual HTTP request and response by setting the log level for _ch.aaap.harvestclient.core.Harvest.http_ to TRACE

#### Log4j2
```xml
<Logger name="ch.aaap.harvestclient.core.Harvest.http" level="trace" additivity="false">
    <AppenderRef ref="Console"/>
</Logger>
```


### Found an Harvest Bug?

Check out [TestSetupUtil.prepareForHarvestBugReport()](https://github.com/3AP-AG/harvest-client/blob/8f9dfda8fa07599de0939177e86f0126fdb1d9b7/src/test/java/util/TestSetupUtil.java#L81) for inspiration


### Contributing

We welcome contributions in the form of pull requests or bug reports!
Please read [Developers](wiki/Developers) before submitting a pull request. If you plan a big change, please
open an issue first so you can get early feedback on your design. 

   
