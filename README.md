V3-JAVA-SDK
===========

V3-JAVA-SDK


The QuickBooks Online Java SDK provides a set of Java class libraries that make it easier to call QuickBooks Online APIs, and access to QuickBooks Online data. Some of the features included in this SDK are:

* Ability to perform single and batch processing of CRUD operations on all QuickBooks Online entities.
* A common interface to the Request and Response Handler with two implemented classes to handle both synchronous and asynchronous requests.
* Support for both XML and JSON Request and Response format.
* Ability to configure app settings in the configuration file requiring no additional code change.
* Support for Gzip and Deflate compression formats to improve performance of Service calls to QuickBooks Online.
* Retry policy constructors to help apps handle transient errors.
* Logging mechanisms for trace and request/response logging.
* Sync APIs that assist with data synchronization between QuickBooks Desktop (Windows) and Intuit's cloud.
* Query Filters that enable you to write Intuit queries to retrieve QuickBooks Online entities whose properties meet a specified criteria.
* Queries for accessing QuickBooks Reports.
* Sparse Update to update writable properties specified in a request and leave the others unchanged.
* Change data that enables you to retrieve a list of entities modified during specified time points.

## Project Overview
* ipp-v3-java-data - contains all entities and entity dependencies that are used in data services operations
* ipp-v3-java-devkit - core component, contains rest API support
* ipp-v3-java-devkit-assembly - builds final deployment package (zip) which includes everything
* ipp-v3-java-devkit-shaded-assembly - builds lightweight version (some dependencies excluded)
* ipp-v3-java-devkit-javadoc - contains javadoc for data and devkit classes

## Sample Apps

* [CRUD Sample](https://github.com/IntuitDeveloper/SampleApp-CRUD-Java)
* [Webhooks Sample](https://github.com/IntuitDeveloper/SampleApp-Webhooks-Java)
* [OAuth Sample](https://github.com/IntuitDeveloper/oauth-java)
* [OpenId-OAuth Sample](https://github.com/IntuitDeveloper/SampleApp-OpenID-Oauth-Java)

## Documentation
* [Class Library Reference](https://developer-static.intuit.com/SDKDocs/QBV3Doc/ipp-v3-java-devkit-javadoc/index.html)
* [Installation and SDK user guide](https://developer.intuit.com/docs/0100_quickbooks_online/0400_tools/0005_accounting/0200_java/0001_quick_start)

## Building components
To build out individual components such as ipp-v3-java-data.jar or ipp-v3-java-devkit.jar, remove parent dependency from the pom.xml and run maven install.

## Release Notes:

* V2.8.0 (12/19/2016)
  * Removed QBD and IAM Cookie code
  * Added support for AuthorizationFault response
  * Updated javadoc for findAll method
  * Updated httpclient library for platform API (ipp-java-qbapihelper-1.2.2.jar)
  * Updated disconnect and reconnect API to return response in platform API (ipp-java-qbapihelper-1.2.2.jar)

* V2.7.1 (11/28/2016)
  * Minor version 8 support
  * cc/bcc field support for Invoice and Preferences entities
  * HttpClient 4.5.2 support

* V2.6.0 (10/14/2016)
  * Webhooks support
  * Error code 429

* V2.5.0 (05/15/2016)
  * Minor version 5 support
  * cc/bcc field support for Invoice and Preferences entities
  * HttpClient 4.5.2 support

* V2.4.0 (09/28/2015)
  * This version includes minor version 3 (default) and 4 support. It also has number of bugfixes.
  * Support for voiding transactions, viewing transactions as PDF, and sending transactions as email

* V2.3.2
  * Added JSON support for SyncErrorResponse
  * Adding data jar packages

* V2.3.1
  * Added reports, General Ledger, Vendor Balance Detail, AP Aging Detail.
  * Added timeout support for AMEX.

* V2.3.0
  * HttpURLConnection
  * Tax Agency create
  * Tax Code create

* V2.2.1
  * v72 Reports

* V2.2.0 
  * Include param support

* V2.1.2
  * Default minor version 1

* V2.1.1
  * Modifications for CRT APIs

* V2.1.0 
  * Regenerated POJOs - v71
  * Added minorVersion flag
  * Added code to ignore JSON additional fields

* V2.0.4 
  * Regenerated POJOs - v70 
  * Added AuthenticationApplicationFailed fault type

* V2.0.3
  * Separate jars
  * Tested for 8.1 and v69

* V2.0.2
  * consolidated jar

* V2.0.1 
  * Request Id non static method
  * Tracking id implementation
  * Platform API deprecation - including qbapihelper jar

* V2.0.0
  * Regenerated classes with new XSD (v67 + IDs 8.0)

* V1.0.9
  * Added support for request id as a param

* V1.0.8
  * Connection and Socket Timeout support
  * Added environment variables (for builds) in devkit project 




