To run the application run below steps : 

git clone https://github.com/mutenMax/Java.git
cd Java/upload-file/
docker build -t upload-file . 
docker run -p 9090:8080 upload-file


Open browser and go to : http://localhost:9090/

