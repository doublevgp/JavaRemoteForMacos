package wgp.task2.data;

public class FileTypeData {
    String filetype;

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public FileTypeData(String filetype) {
        this.filetype = filetype;
    }

    @Override
    public String toString() {
        return "FileTypeData{" +
                "filetype='" + filetype + '\'' +
                '}';
    }
}
