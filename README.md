# IDV

[![Build Status](https://travis-ci.org/michaelruocco/idv.svg?branch=master)](https://travis-ci.org/michaelruocco/idv)
[![Coverage Status](https://coveralls.io/repos/github/michaelruocco/idv/badge.svg?branch=master)](https://coveralls.io/github/michaelruocco/idv?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/bf0e7c1e4ce54853bdad4570230cc33c)](https://app.codacy.com/app/michaelruocco/idv?utm_source=github.com&utm_medium=referral&utm_content=michaelruocco/idv&utm_campaign=Badge_Grade_Dashboard)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/idv?branch=master)](https://bettercodehub.com/)

## To Do

* implement deploying to aws from travis ci and running tests (try out newman / postman) against deployed service

* see how to package lambda functions individually, might be easier to wait until second one is built

* error handling for id or alias type and value not provided with unit tests

* error handling for identity not found with unit tests

* need to add dynamo db dao rather than hard coded with unit tests

* identity merging, when create identity raise event with created identity, this can be used
for both MI reporting and to trigger an "offline" lambda event to check for matching aliases and merge
identities if and duplicated aliases

## Running the Tests

You can run the unit tests for this project by running:

```bash
./gradlew clean build
```

## Checking dependencies

You can check the current dependencies used by the project to see whether
or not they are currently up to date by running the following command:

```bash
./gradlew dependencyUpdates
```