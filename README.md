### Building and running the project

Start database by executing:
```bash
docker compose up
```

And run the project:

```bash
mvn clean package
java -jar target/godtask.jar
```

### Testing the project

To test the project, use these endpoints:

- http://localhost:8080/history - lists all the historical requests.
- http://localhost:8080/validate - dialog to enter order JSON and check if it's valid

If you want to test REST requests, please use prepared requests in `etc/Requests.http` file.

### Main technologies used

- Java 11
- Spring Boot
- Docker Compose
- Hibernate Validator
- H2
- PostgreSQL
- Liquibase
- jUnit 5

### Main principle

Request is validated by using Hibernate Validator's annotations - most of them custom made (please see package `info.ernestas.godtask.service` for code) - and later inserting the request into the database.

### Adding a new WorkType

In order to add a new work type - let's call it DESTROYING - which JSON contains the following structure:

```json
{"type":"DESTROYING","department":"GOoD analysis department","start_date":"2020-08-13","end_date":"2020-08-15","currency":"USD","cost":123.12, "action": "boom!"}
```

We need to make the following changes:

- Create a new class:

```java
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DestroyingWorkOrder extends WorkOrder {

    // Validation: it can not be empty
    @NotEmpty
    private String action;

}
```

- Edit `WorkOrder`:

```java
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AnalysisWorkOrder.class, name = "ANALYSIS"),
        @JsonSubTypes.Type(value = RepairWorkOrder.class, name = "REPAIR"),
        @JsonSubTypes.Type(value = ReplacementWorkOrder.class, name = "REPLACEMENT"),
        @JsonSubTypes.Type(value = DestroyingWorkOrder.class, name = "DESTROYING") // Added
})
@Data
// ... Other code
```

- Edit `OrderType` enum:

```java
public enum OrderType {
    ANALYSIS,
    REPAIR,
    REPLACEMENT,
    DESTROYING // Added
}
```

This `DestroyingWorkOrder` is added to the project as an example.
