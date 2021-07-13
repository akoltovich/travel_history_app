# Guide for reviewer

Run the application with the command from terminal mvn spring-boot:run

You can use Swagger UI to testing application http://localhost:8080/travelhistory/swagger-ui/

Main path for app is http://localhost:8080/travelhistory + /users or /travels

#Testing with Postman

#### http://localhost:8080/travelhistory/users

######Get mapping

/users must return all users from database

/id must return from database user by his id. Example of input: http://localhost:8080/travelhistory/users/id?id=1

######Post mapping

/users must add user in database. Example of body:
```json
{
    "firstName": "Alexey",
    "lastName": "Koltovich",
    "dateOfBirth":"1996-07-25"
}
```

######Put mapping

/{id} must update user information in database by his id. Example of input: http://localhost:8080/travelhistory/users/1

Example of body:
```json
{
    "firstName": "NoAlexey",
    "lastName": "NoKoltovich",
    "dateOfBirth":"1997-08-26"
}
```

######Delete mapping

/{id} must delete user and his travels from database by his id. Example of input: http://localhost:8080/travelhistory/users/1

#### http://localhost:8080/travelhistory/travels

######Get mapping

/travels must return all travels from database

/id must return from database travel by its id. Example of input: http://localhost:8080/travelhistory/travels/id?id=1

######Post mapping

/travels must add travel in database, only if traveller already exists! Example of body:
```json
{
  "country": "Germany",
  "yearOfTravel":"2000",
  "description":"Some description",
  "userId":"1"
}
```

######Put mapping

/{id} must update travel information in database by its id. Example of input: http://localhost:8080/travelhistory/travels/1

Example of body:
```json
{
  "country": "Russia",
  "yearOfTravel":"1999",
  "description":"Other description",
  "userId":"1"
}
```

######Delete mapping

/{id} must delete travel from database by its id. Example of input: http://localhost:8080/travelhistory/travels/1

#I hope you will like it :)