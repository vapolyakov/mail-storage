Mail Storage
============

Mail Storage for keeping and analyzing emails.

## Requirements:
- java 8 and maven 3
- running Hadoop 2.7.3 on hdfs://localhost:9000 (configurable) in pseudo distributed mode
- running HBase 1.2.2 on localhost with default configurations

## Offers:
- .eml files storage
- specific email analysis
- continuous email data analysis for specific users and time intervals

## How to use:
- upload .eml file as octet stream via `/upload` handle and watch extracted data in HBase
Example: `curl --header "Content-Type:application/octet-stream" --data-binary @test_mail.eml 'http://localhost:8080/upload?filename=test_mail.eml&user_id=some_user_id'`

## How to start application:
- run `mvn exec:java` in web-service folder
- or explicitly start com.mailstorage.web.MailStorageMain.main()