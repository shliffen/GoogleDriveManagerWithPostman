package ua.com.foxminded.taskE.GoogleDriveManager.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.com.foxminded.taskE.GoogleDriveManager.service.GoogleDriveService;
import ua.com.foxminded.taskE.GoogleDriveManager.service.PostAndPatchService;
import java.io.*;
import java.util.List;

@RestController("GoogleDriveControllerV1")
@RequestMapping("/v1/")
public class GoogleDriveController {

    @Autowired
    ApplicationArguments appArgs;

    private String token="";

    @Value("${clientId}")
    private String clientId;

    @Value("${clientSecret}")
    private String clientSecret;

    private String directoryForDownload = "D:\\downloads\\";

    private GoogleDriveService googleDriveService = new GoogleDriveService();
    private PostAndPatchService postAndPatchService = new PostAndPatchService();

    @GetMapping("/drive/v3/files/")
    public String listFiles() {
        if (isTokenAvailable()) {
            return googleDriveService.sendingGET("https://www.googleapis.com/drive/v3/files?access_token=" + token);
        } else {
            return "Please, send the token to Application, first, by using <<Sending token request>>";
        }
    }

    @DeleteMapping("/drive/v3/files/{id}")
    public String deleteFile(@PathVariable String id){
        if (isTokenAvailable()) {
            googleDriveService.sendingDEL("https://www.googleapis.com/drive/v3/files/" + id + "?access_token=" + token);
            return "The file was delete successfully...";
        } else {
            return "Please, send the token to Application, first, by using <<Sending token request>>";
        }
    }

    @PatchMapping("/drive/v3/files/{id}")
    public String patchFile(@PathVariable String id, @RequestParam("file") String fileName){
        String url;
        if (isTokenAvailable()) {
            url = "https://www.googleapis.com/drive/v3/files/" + id + "?access_token=" + token;
        } else {
            return "Please, send the token to Application, first, by using <<Sending token request>>";
        }
        return postAndPatchService.renameFile(url,id,fileName);
     }

    @GetMapping("/drive/v3/files/{id}")
    public String downloadFile(@PathVariable String id, @RequestParam("file") String fileName){
        String urlForDownload = "https://www.googleapis.com/drive/v3/files/" + id + "?alt=media";
        if (isTokenAvailable()) {
            return googleDriveService.downloadFile(urlForDownload, fileName, directoryForDownload, token);
        } else {
            return "Please, send the token to Application, first, by using <<Sending token request>>";
        }

    }

    @PostMapping("/upload/drive/v3/files")
    public String postData(@RequestParam("file") MultipartFile file) throws IOException {
        String url = "https://www.googleapis.com/upload/drive/v3/files";
        if (isTokenAvailable()) {
            return postAndPatchService.postFile(url, file, token);
        } else {
            return "Please, send the token to Application, first, by using <<Sending token request>>";
        }
    }

    @PostMapping("/token")
    private String sendingToken(@RequestParam("access_token") String accessToken){
        setToken(accessToken);
        if (!getToken().equals("")) return "The new token was upload successfully. The new Token is = " + token;
        else return "Please, get the new Token on Authorization tab - and send POST another time.";
    }

    private boolean isTokenAvailable(){
        if (!token.equals("")) return true;
        List<String> args = appArgs.getOptionValues("token");
        if ((args!=null)&&(args.size()>0)){
            token = args.get(0);
            return true;
        } else {
            token = "";
            return false;
        }
    }

    private String getToken() {
        return token;
    }

    private void setToken(String token) {
        this.token = token;
    }


}