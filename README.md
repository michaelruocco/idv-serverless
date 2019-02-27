# IDV

[![Build Status](https://travis-ci.org/michaelruocco/idv.svg?branch=master)](https://travis-ci.org/michaelruocco/idv)
[![Coverage Status](https://coveralls.io/repos/github/michaelruocco/idv/badge.svg?branch=master)](https://coveralls.io/github/michaelruocco/idv?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/bf0e7c1e4ce54853bdad4570230cc33c)](https://app.codacy.com/app/michaelruocco/idv?utm_source=github.com&utm_medium=referral&utm_content=michaelruocco/idv&utm_campaign=Badge_Grade_Dashboard)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/idv?branch=master)](https://bettercodehub.com/)

## To Do

need proper error handling for:

1. id or alias type and value not provided
2. identity not found
3. need to add dynamo db dao rather than hard coded
4. add missing test coverage for alias factory
5. add missing test coverage for handler
6. package lambda functions individually
7. identity merging, when create identity raise event with created identity, this can be used
for both MI reporting and to trigger an "offline" lambda event to check for matching aliases and merge
identities if and duplicated aliases


## Running the Tests

You can run the unit tests for this project by running:

```
./gradlew clean build
```

## Checking dependencies

You can check the current dependencies used by the project to see whether
or not they are currently up to date by running the following command:

```
./gradlew dependencyUpdates
```