# GoogleDriveManagerWithPostman
A simple web service which can be used to manipulate files from google drive. Postman is used as a frontend.

**How to run?**
You can run it from the dist folder - by running first on make.sh/make.bat (to build the project using Maven and create a "uber"-jar), and then run it using GoogleDriveManager.bat / GoogleDriveManager.sh:

1. After launch, import the GoogleDriveManager API collection (located in the docs folder) to Postman

2. First user have to send the POST request "Sending token request" (after updating the token in the Authorization tab)

3. Then we can send requests at will

4. The only thing - to request Get File, you must enter the desired file name of the downloaded file on your PC (it will be saved in the "D\downloads\" folder - the folder will be created)

5. When there was a desire to terminate the client's work - in the launch window, press "CTRL + C" to exit. 

**Can I see how it works on the video?**
Link to the video with the "presentation": 
https://www.youtube.com/watch?v=D4Ho_rc_Ndw

Version 1.0
**Technologies used**
* Java + Maven + Spring Boot MVC + REST + Postman (as FrontEnd)

Version 2.0
**Technologies used**
* Java + Maven + Spring Boot MVC + REST + Postman (as FrontEnd) + GoogleWebService Libraries + Swagger (Documentation)