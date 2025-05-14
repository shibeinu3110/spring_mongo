FROM openjdk:17

ARG JAR_FILE=target/*.jar
  #cũng có thể ghi là target/spring-sample-project-01-0.0.1-SNAPSHOT.jar. Nếu * thì nó sẽ chỉ đến file jar duy nhất

  #copy file jar into docker image
ADD ${JAR_FILE} springboot-mongodb-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "springboot-mongodb-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080