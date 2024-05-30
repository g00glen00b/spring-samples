# `CompromisedPasswordChecker` example

This example demonstrates how to use the `CompromisedPasswordChecker` interface to check if a password has been compromised.

To run the application, run it with any of the following profiles:

To use the Have I Been Pwned API:

```shell
./mvnw spring-boot:run -Dspring-boot.run.profiles=haveibeenpwned
```

To use SecList's top 1 million common passwords:

```shell
./mvnw spring-boot:run -Dspring-boot.run.profiles=resource
```

To use nbvcxz's password entropy checker:

```shell
./mvnw spring-boot:run -Dspring-boot.run.profiles=nbvcxz
```

The application will start on [http://localhost:8080](http://localhost:8080).

To authenticate with a compromised password, use:

```shell
curl \
  -X GET \
  --location "http://localhost:8080/api/user/current" \
  -H "Content-Type: application/json" \
  --basic --user user:password
```

To authenticate with a secure password, use:

```shell
curl \
  -X GET \
  --location "http://localhost:8080/api/user/current" \
  -H "Content-Type: application/json" \
  --basic --user user2:A-Password-That-Is-Not-Common-Or-Shared
```