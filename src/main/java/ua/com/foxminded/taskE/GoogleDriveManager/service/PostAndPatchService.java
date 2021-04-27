package ua.com.foxminded.taskE.GoogleDriveManager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ua.com.foxminded.taskE.GoogleDriveManager.model.FilesDataInResponseJson;
import org.apache.http.client.HttpClient;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class PostAndPatchService {

    public String postFile(String url, MultipartFile file, String token) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        byte[] fileByteArray = file.getBytes();
        String fileName = file.getOriginalFilename();
        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition =
                ContentDisposition.builder("form-data").name("file").filename(fileName).build();

        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(fileByteArray, fileMap);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileEntity);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            String responseHeaders = response.getBody();
            ObjectMapper mapper = new ObjectMapper();
            FilesDataInResponseJson dataInResponseJson = new FilesDataInResponseJson();
            dataInResponseJson = mapper.readValue(responseHeaders, FilesDataInResponseJson.class);
            renameFileOnServer(dataInResponseJson.getId(), fileName, token);
            dataInResponseJson.setName(fileName);
            return "File " + dataInResponseJson.getName() + " was created, id = " + dataInResponseJson.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return "there is error " + e.getMessage();
        }
    }

    public String renameFile(String url, String id, String fileName){
        String content = "{\"name\": \"" + fileName + "\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(content, headers);
        RestTemplate restTemplate = new RestTemplate();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(4000);
        requestFactory.setReadTimeout(4000);
        restTemplate.setRequestFactory(requestFactory);

        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<String> formEntity = restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class);
        return formEntity.getStatusCode().toString();
    }

    private String renameFileOnServer(String id, String fileName, String token){
        String url = "https://www.googleapis.com/drive/v3/files/" + id + "?access_token="+ token;
        String content = "{\"name\": \"" + fileName + "\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(content, headers);
        RestTemplate restTemplate = new RestTemplate();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(4000);
        requestFactory.setReadTimeout(4000);
        restTemplate.setRequestFactory(requestFactory);

        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<String> formEntity = restTemplate.exchange(url, HttpMethod.PATCH, entity, String.class);
        return formEntity.getStatusCode().toString();
    }
}
