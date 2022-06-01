#Description
Bankish exchange currency application

#Dependencies
All dependencies are located in pom.xml

#Installing
https://github.com/ErsinGureli/exchange-currency.git

#Executing program
#Maven : mvn clean package docker:build
#Terminal : run below commands in order
1 - cd docker
2 - docker-compose up -d

see 4 containers are started
- Container pgadmin4_container         Started                                                                                                                                                                                  
- Container pgadmin4_container         Started
- Container postgresdb                 Started 
- Container currency-exchange-service  Started


#For testing:
change spring.profiles.active to test
remove docker images by the comment docker-compose down
run maven test

