# Country Covid Stats API

### Prerequisites

For building and running the application the following requirements need to be fulfilled:

* Install [Java 17]
* Install [Maven]

### Setting up the Spring Boot project on your local machine 

* Clone the git repository

```shell
git clone https://github.com/ddimov99/a1sR67DdK-3na8-s81eA-aeh1-ps4sVc1DoGd4.git
```

* Build the app

```shell
mvn clean install
```

* Run the app

```shell
mvn spring-boot:run
```

### Making requests

* Example
http://localhost:8080/api/covid-stats/country/BG

to make a request http://localhost:8080/api/covid-stats/country/{countryCode} -- only upper case letters
