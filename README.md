# IDV Serverless

[![Build Status](https://travis-ci.org/michaelruocco/idv-serverless.svg?branch=master)](https://travis-ci.org/michaelruocco/idv-serverless)
[![Coverage Status](https://coveralls.io/repos/github/michaelruocco/idv-serverless/badge.svg?branch=master)](https://coveralls.io/github/michaelruocco/idv-serverless?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4b2b137a83d14e9382fcad07090785ad)](https://www.codacy.com/manual/michaelruocco/idv-serverless?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=michaelruocco/idv-serverless&amp;utm_campaign=Badge_Grade)
[![BCH compliance](https://bettercodehub.com/edge/badge/michaelruocco/idv-serverless?branch=master)](https://bettercodehub.com/)

## Running Unit Tests

You can run the unit tests for this project by running:

```bash
make clean build
```

## Deploying

You can deploy the code once it is built you can run the command below:

```bash
make deploy
```

This command will install serverless if you don't have it already and
then attempt to use it to deploy the code into AWS.

## Running Postman Tests

To run the postman tests you will need to have deployed the using the
deploy command above, once this is done you can  run the command below
to install the newman npm package and then run the postman tests using
newman by running:

```bash
make postman
```

## Checking dependencies

You can check the current dependencies used by the project to see if
they are up to date by running the following command:

```bash
make checkDependencies
```

## Fix code formatting issues

You can tidy up and code formatting issues by running:

```bash
make spotless
```

## Putting it all together

All of the above commands can be run in combination too e.g:

```bash
make clean spotless build deploy postman
```