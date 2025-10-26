# Pet Store API

This is a Micronaut-based REST API for managing pets.

## Requirements
- **JDK Version:** 21 or newer
- **Build Tool:** Gradle

## How to Start the Application

1. Ensure JDK 21+ is installed.
2. Run the following command from the project root:
   
   ```bash
   ./gradlew run
   ```

3. The application will start on [http://localhost:8080](http://localhost:8080)

## Running on a Custom Port

To run the service on a different port (e.g., 9090), use one of the following commands:

```bash
MICRONAUT_SERVER_PORT=9090 ./gradlew run
```

or

```bash
./gradlew run -Dmicronaut.server.port=9090
```

The application will start on [http://localhost:9090](http://localhost:9090)

## Swagger Documentation

Once the application is running, access the Swagger UI at:

[http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui)

If running on a custom port, use:

[http://localhost:9090/swagger-ui](http://localhost:9090/swagger-ui)

---

## Micronaut 4.8.3 Documentation

- [User Guide](https://docs.micronaut.io/4.8.3/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.8.3/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.8.3/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [GraalVM Gradle Plugin documentation](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)
- [Shadow Gradle Plugin](https://gradleup.com/shadow/)
## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)

## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)

