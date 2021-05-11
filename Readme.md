#XML-Parser

This is a simple Spring Boot based REST application that is able to parse basic information about StackOverflow posts. 

## Running the application
1. Via JAR

Execute following command:
   
   `mvn clean package spring-boot:repackage`

to get the fat jar, and then:
   
`java -jar target/xml-parser-spring-boot.jar `

2. Via Docker

First pull the image using `docker pull zytawisz/xmlparser:xml-parser` and the run it via `docker run -p 8080:8080 zytawisz/xmlparser:xml-parser`

## Usage
Application accepts JSON Get request with one parameter `url` pointing to the XML file.

Example Request:
```
curl -i -X POST \
-H "Content-Type:application/json" \
-d \ '{
"url": "https://s3-eu-west-1.amazonaws.com/merapar-assessment/arabic-posts.xml"
}' \
'http://localhost:8080/analyze/'
```
It will return either JSON containing parsing result, or error response in case of any exceptions.  