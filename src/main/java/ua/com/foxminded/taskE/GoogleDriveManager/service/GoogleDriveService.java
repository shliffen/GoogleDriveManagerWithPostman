package ua.com.foxminded.taskE.GoogleDriveManager.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
public class GoogleDriveService {

    public GoogleDriveService(){
    }

    public String sendingGET(String url){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).build();
        String result = "";
        try {
            result =
                    client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void sendingDEL(String url){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).DELETE().build();
        client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    public String downloadFile(String url, String fileName,
                               String directoryForDownloadFiles, String token){
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<byte[]> response = restTemplate.exchange(url,
                                                                    HttpMethod.GET, entity, byte[].class);

            File file = new File(directoryForDownloadFiles + fileName);
            file.createNewFile();
            Files.write(Paths.get(file.getPath()), response.getBody());

            return "" + fileName + " was downloaded to the " + directoryForDownloadFiles + " directory...";
        } catch (Exception e){
            e.printStackTrace();
            return "Ooops, there is a problems here...";
        }
    }

}

