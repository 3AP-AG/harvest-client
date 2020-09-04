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

### Using a build published on Github Packages

Packages for this project are first pushed to Github Package Registry (and then to maven central, not implemented yet).
To use it as a dependency from there:

1. add the following to your build.gradle file:
#### Gradle
```groovy
repositories {
    maven {
          name = "GitHubPackages"
          url = uri("https://maven.pkg.github.com/3ap-ag/harvest-client")
          credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("GH_USERNAME")
            password = project.findProperty("gpr.key") ?: System.getenv("GH_TOKEN")
          }
          content {
            // this repository *only* contains artifacts with this group
            includeGroup "ch.aaap"
          }
        }
}
```
#### maven
```xml
    <server>
      <id>github</id>
      <username>GH_USERNAME</username>
      <password>GH_TOKEN</password>
    </server>
```

or check the [link](https://help.github.com/en/packages/using-github-packages-with-your-projects-ecosystem/configuring-apache-maven-for-use-with-github-packages#authenticating-to-github-packages).

Using Github package repositories need authentication: see [Installing a Package from Github Registry](https://help.github.com/en/packages/publishing-and-managing-packages/installing-a-package)
2. You need to create a personal github token with the permissions: `read:packages`

3. Then have it defined either as env. variables in `GH_USERNAME` and `GH_TOKEN` or you can add it as gradle properties,
e.g. in ~/.gradle/gradle.properties using `gpr.user` and `gpr.key`.

4. Add the dependency, e.g. add to build.gradle: 

```groovy
dependencies {
    implementation 'ch.aaap:harvest-client:1.1.2.001'
}
```

### Publishing a new version manually

1. Set new project.version in build.gradle
2. Use a github token with at least these permissions: `write:packages`. See [About Tokens](https://help.github.com/en/packages/publishing-and-managing-packages/about-github-packages#about-tokens)
3. Run `gradle publish`

### Prerequisites
* Java 8 (or higher)
* Gradle

### Run the build end tests
* Clone or fork this repository
* Get a personal access token [from Harvest](https://id.getharvest.com/developers)
    * Attention: unit tests assume admin access. Don't run them against a production account!
* Configuring your Harvest account id and authentication token:
    * You will need 2 Harvest accounts with admin access (it's a limitation of the current Harvest API)
    * Either copy [reference.conf](src/main/resources/reference.conf) to src/test/resources/admin{1,2}.conf and insert your credentials
    * Or set the following environment properties:
    ```bash
        $ export HARVEST_ACCOUNT_ID_ADMIN1=YOUR_ACCT_ID_1
        $ export HARVEST_AUTH_TOKEN_ADMIN1=YOUR_AUTH_TOKEN_1
        $ export HARVEST_ACCOUNT_ID_ADMIN2=YOUR_ACCT_ID_2
        $ export HARVEST_AUTH_TOKEN_ADMIN2=YOUR_AUTH_TOKEN_2
    ```   
* Run ```gradle build``` in the root directory of the repository

#### Caution when running tests locally
Harvest API uses Rate limiting. API v2 limits are documented [here](https://help.getharvest.com/api-v2/introduction/overview/general/#rate-limiting).

The rate limiting is bound to your IP address (client address). No matter how many Harvest trial accounts you use, your requests will be counted and you might get blocked.    

There are two variants of rate limiting, depending on the API type (general API, reports API).  
Reports API is more restrictive (our expense and invoice tests depend on it). 

The admin.conf file allows to configure the throttling on the client side (we limit how often we hit the Harvest API), 
however currently there's only one setting, that applies to general API.
```
// max number of requests to send in the given time window
max_request_per_window: 95
// size of the rate limit window in seconds
window_size_seconds: 15
```  
We can't control the report API at the moment (has to be implemented on our side), so this may lead to API request failures, returning HTTP 429.

### Creating a test Harvest account
Go to https://www.getharvest.com/signup

Use your 3ap email with a "+<month><year>" added to the name, e.g. marco+Jul2020@3ap.ch (this way you can create a filter in gmail to archive the emails you get).

Choose "Me and my team"  
Select "Next Step" until the end.  

Setting for both accounts:  
Go to "Settings" -> "Chose Modules" -> Tick all modules

Setting only for the second account:   
Go to "Settings" -> "Edit Preferences" -> Set Time Mode to "Track time via start and end time"

_The default Time Mode is the Duration mode. Keep that for your first account, as there are tests that require this._

#### Getting an API key
Account ID and token are needed for authentication. You can generate these by navigating to your profile.  
My Profile -> link under work email to your Harvest ID settings -> Developers -> create new personal Token (name does not matter).  


Add your token to the local files admin1.conf and admin2.conf under src/test/resources/admin*.conf

Update the CircleCI project environment variables with these Id and tokens here:
https://app.circleci.com/settings/project/github/3AP-AG/harvest-client/environment-variables

### Debugging

You can see the actual HTTP request and response by setting the log level for _ch.aaap.harvestclient.core.Harvest.http_ to TRACE

#### Log4j2
```xml
<Logger name="ch.aaap.harvestclient.core.Harvest.http" level="trace" additivity="false">
    <AppenderRef ref="Console"/>
</Logger>
```


#### Found an Harvest Bug?

Check out [TestSetupUtil.prepareForHarvestBugReport()](https://github.com/3AP-AG/harvest-client/blob/8f9dfda8fa07599de0939177e86f0126fdb1d9b7/src/test/java/util/TestSetupUtil.java#L81) to setup your logs before sending them to Harvest.


### Contributing

We welcome contributions in the form of pull requests or bug reports!
Please read [Developers](https://github.com/3AP-AG/harvest-client/wiki/Developers) before submitting a pull request. If you plan a big change, please
open an issue first so you can get early feedback on your design. 

   
