professional-reference-data-api


# professional-reference-data-api
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Build Status](https://travis-ci.org/hmcts/ref-professional-user-profile.svg?branch=master)](https://travis-ci.org/hmcts/ref-professional-user-profile)
[![codecov](https://codecov.io/gh/hmcts/ref-professional-user-profile/branch/master/graph/badge.svg)](https://codecov.io/gh/hmcts/ref-professional-user-profile)

____

## Getting started

### Prerequisites

- [JDK 8](https://www.oracle.com/java)

#### Environment variables

The following environment variables are required:

| Name | Default | Description |
|------|---------|-------------|
| SPRING_DATASOURCE_USERNAME | - | Username for database |
| SPRING_DATASOURCE_PASSWORD | - | Password for database |
| S2S_NAMES_WHITELIST | sscs,divorce | Authorised micro-service names for S2S calls |
| S2S_BASE_URI | - | Base URL for IdAM's S2S API service (service-auth-provider). `http://localhost:4502` for the dockerised local instance or tunneled `dev` instance. |
| IDAM_ROLES_WHITELIST | solicitor,citizen | Authorised micro-service names for S2S calls |
| IDAM_USER_BASE_URI | - | Base URL for IdAM's S2S API service (service-auth-provider). `http://localhost:4502` for the dockerised local instance or tunneled `dev` instance. |

### Building

The project uses [Gradle](https://gradle.org/).

To build project please execute the following command:

```bash
./gradlew clean build
```

### Running

If you want your code to become available to other Docker projects (e.g. for local environment testing), you need to build the image:

```bash
docker-compose build
```

When the project has been packaged in `target/` directory,
you can run it by executing following command:

```bash
docker-compose up
```

As a result the following containers will get created and started:

 - API exposing ports `8080`

#### Handling database

Database will get initiated when you run `docker-compose up` for the first time by execute all scripts from `database` directory.

### Idam

TODO

### API

The sawgger documention is accessible to localhost:8080/swagger-ui.html

#### Create a Organisation

TODO

## LICENSE

This project is licensed under the MIT License - see the [LICENSE](LICENSE.md) file for details.

