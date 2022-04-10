package wgp.task2.data;

import wgp.task2.utils.FileIconUtil;

public class DlfData extends NetFileData {
    private String file_name;
    private String file_path;
    private String suffixName;
    private long file_size;
    private long file_down_size;
    private String ip;


    private int port;
    public DlfData(String name, String path, long fileSize, long downSize, String ip, int port) {
        super();
        this.file_name = name;
        this.file_path = path;
        this.file_size = fileSize;
        this.file_down_size = downSize;
        this.ip = ip;
        this.port = port;
        this.suffixName = FileIconUtil.getFileSuffixName(name);
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public long getFile_size() {
        return file_size;
    }

    public void setFile_size(long file_size) {
        this.file_size = file_size;
    }

    public long getFile_down_size() {
        return file_down_size;
    }

    public void setFile_down_size(long file_down_size) {
        this.file_down_size = file_down_size;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    public String getSuffixName() {
        return suffixName;
    }

    public void setSuffixName(String suffixName) {
        this.suffixName = suffixName;
    }


    @Override
    public String toString() {
        return "DlfData{" +
                "file_name='" + file_name + '\'' +
                ", file_path='" + file_path + '\'' +
                ", file_size=" + file_size +
                ", file_down_size=" + file_down_size +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
