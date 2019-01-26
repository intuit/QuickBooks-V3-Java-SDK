[![SDK Banner](views/SDK.png)][ss1]

V3-JAVA-SDK
===========

**Help:** [Support](https://developer.intuit.com/help), [Samples](https://developer.intuit.com/docs/0100_quickbooks_online/0400_tools/0005_sdks/0200_java/0004_sample_code_and_sample_apps) <br/>
**Documentation:** [User Guide](https://developer.intuit.com/app/developer/qbo/docs/develop/sdks-and-samples-collections/java), [JavaDocs](https://developer-static.intuit.com/SDKDocs/QBV3Doc/ipp-v3-java-devkit-javadoc/index.html)
<br/>
**Continuous Integration:** [![Build Status](https://travis-ci.org/intuit/QuickBooks-V3-Java-SDK.svg?branch=develop)](https://travis-ci.org/intuit/QuickBooks-V3-Java-SDK)
<br/>
**Maven:** [![Data](https://maven-badges.herokuapp.com/maven-central/com.intuit.quickbooks-online/ipp-v3-java-data/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.intuit.quickbooks-online/ipp-v3-java-data) 
[![Devkit](https://maven-badges.herokuapp.com/maven-central/com.intuit.quickbooks-online/ipp-v3-java-devkit/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.intuit.quickbooks-online/ipp-v3-java-devkit) 
<br/>
**License:** [![Apache 2](http://img.shields.io/badge/license-Apache%202-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0) <br/>


## Overview
The QuickBooks Online Java SDK provides a set of Java class libraries that make it easier to call QuickBooks Online APIs, and access to QuickBooks Online data. Some of the features included in this SDK are:

* Ability to perform single and batch processing of CRUD operations on all QuickBooks Online entities.
* A common interface to the Request and Response Handler with two implemented classes to handle both synchronous and asynchronous requests.
* Support for both XML and JSON Request and Response formats.
* Ability to configure app settings in the configuration file requiring no additional code change.
* Support for Gzip and Deflate compression formats to improve performance of Service calls to QuickBooks Online.
* Retry policy constructors to help apps handle transient errors.
* Logging mechanisms for trace and request/response logging.
* Query Filters that enable you to write Intuit queries to retrieve QuickBooks Online entities whose properties meet a specified criteria.
* Queries for accessing QuickBooks Reports.
* Sparse Update to update writable properties specified in a request and leave the others unchanged.
* Change data that enables you to retrieve a list of entities modified during specified time points.

## Project Structure
* ipp-v3-java-data - contains all entities and entity dependencies that are used in data services operations.
* ipp-v3-java-devkit - core component, contains REST API support.
* ipp-java-qbapihelper - contains QuickBooks Online API Helper methods for OAuth, Disconnect and Reconnect API.
* oauth2-platform-api - contains QuickBooks Online API Helper methods for obtaining OAuth2 tokens, Disconnect and Reconnect API for OAuth2 apps.
* payments-api - Payments SDK for V2 API, contains methods for charge, echeck, token, card and bank account APIs.

## System Requirements
The SDK works on JDK 1.6 and above.

## First Use Instructions
1. Clone the GitHub repo to your computer.
2. Import it to the IDE of your choice.

## Testing the Code & Building Artifacts

To test the code locally, follow the steps below:

1. cd to the project directory
2. Run the command: `mvn install` - this will run the unit test, build the project and generate data, devkit, qbapihelper(OAuth1.0a), oauth2-platform(OAuth2) jars

Note: To build out individual components such as ipp-v3-java-data.jar or ipp-v3-java-devkit.jar, remove parent dependency from the pom.xml of the respective projects and run maven install on the individual project folders.

## Release Notes
Refer to [Java SDK Release Notes](https://developer.intuit.com/docs/0100_quickbooks_online/0400_tools/0005_sdks/0200_java/0080_quickbooks_java_sdk_release_notes).

## Contribute
We greatly encourage contributions! You can add new features, report and fix existing bugs, write docs and
tutorials, or any of the above. Feel free to open issues and/or send pull requests.

The `master` branch of this repository contains the latest stable release of the SDK, while snapshots are published to the `develop` branch. In general, pull requests should be submitted against `develop` by forking this repo into your account, developing and testing your changes, and creating pull requests to request merges. See the [Contributing to a Project](https://guides.github.com/activities/contributing-to-open-source/)
article for more details about how to contribute.

Steps to contribute:

1. Fork this repository into your account on GitHub.
2. Clone *your forked repository* (not our original one) to your hard drive with `git clone https://github.com/YOURUSERNAME/QuickBooks-V3-Java-SDK.git`.
3. Design and develop your changes.
4. Add/update unit tests.
5. Create a pull request for review to request merge.
6. Obtain approval before your changes can be merged.

Note: Before you submit the pull request, make sure to remove the keys and tokens from [ippdevkit.properties](https://github.com/intuit/QuickBooks-V3-Java-SDK/blob/master/ipp-v3-java-devkit/src/test/resources/ippdevkit.properties) that you might have added for testing.

Thank you for your contribution!

[ss1]: https://help.developer.intuit.com/s/SDKFeedback?cid=1180


