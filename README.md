# IDV

[![Build Status](https://travis-ci.org/michaelruocco/idv.svg?branch=master)](https://travis-ci.org/michaelruocco/idv)
[![Coverage Status](https://coveralls.io/repos/github/michaelruocco/idv/badge.svg?branch=master)](https://coveralls.io/github/michaelruocco/idv?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/bf0e7c1e4ce54853bdad4570230cc33c)](https://app.codacy.com/app/michaelruocco/idv?utm_source=github.com&utm_medium=referral&utm_content=michaelruocco/idv&utm_campaign=Badge_Grade_Dashboard)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/idv?branch=master)](https://bettercodehub.com/)

## To Do

 1. fix coverage gaps on verification context deserializer by adding tests to cover all activity types and all
verification methods, also improve coverage on verification context json api document and dynamo verification context
dao if possible

 2. add json api types for verification context request and response

 3. implement aws lambda handler for posting verification contexts

 4. implement aws lambda handler for getting verification contexts

 5. identity merging, when create identity raise event with created identity, this can be used
for both MI reporting and to trigger an "offline" lambda event to check for matching aliases and merge
identities if and duplicated aliases

 6. figure out how to get deployed host name after serverless deploy has run and add into
postman environment file or pass as argument when postman tests run

## Running Unit Tests

You can run the unit tests for this project by running:

```bash
./gradlew clean build
```

## Running Postman Tests

To run the postman tests you will need to install the newman npm package by running:

```bash
npm install newman
```

Once newman is installed you can run the postman tests by running the following command:

```bash
newman run postman/idv.postman_collection.json -e postman/environment/idv-dev.postman_environment.json
```

## Checking dependencies

You can check the current dependencies used by the project to see whether
or not they are currently up to date by running the following command:

```bash
./gradlew dependencyUpdates
```