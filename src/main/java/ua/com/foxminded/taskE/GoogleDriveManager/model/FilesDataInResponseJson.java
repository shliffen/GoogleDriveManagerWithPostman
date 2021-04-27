package ua.com.foxminded.taskE.GoogleDriveManager.model;

public class FilesDataInResponseJson {
    private String kind;
    private String id;
    private String name;
    private String mimeType;

    public FilesDataInResponseJson(String kind, String id, String name, String mimeType) {
        this.kind = kind;
        this.id = id;
        this.name = name;
        this.mimeType = mimeType;
    }

    public FilesDataInResponseJson(){
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}