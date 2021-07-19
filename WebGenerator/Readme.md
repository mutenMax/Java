##  Instructions to run the app

######  Prerequisite
 - Docker
 
 Follow this link to download and install Docker : https://www.docker.com/products/docker-desktop 

######  Once docker is installed Clone the repo using below command. 
git clone git@github.com:mutenMax/Java.git

######  Change to the application folder 
cd Java/WebGenerator/

######  Create an Image 
docker build -t web-generator .

######  Start the container with newly created image. This will start the container in interactive mode and will shell into it
docker run -it web-generator

######  Now you are inside the container in the applcation folder. To run tests
mvn test 
######  To run app : 
java -jar ./target/WebGenerator.jar

######  Follow the instructions. 
######  When the applicastion finishes excutuiing. you shoukd see the generated folders/ subfolders by running : 
ls -lart
