package wgp.task2.data;

public class NetFileData {
    //自定义的文件类，能将网络传输过来的"filename>filedate>filesize>isdir>"信息作为构造函数的传递参数，进而实现相关属性的获取
    //自定义的文件类，能将网络传输过来的"filename>filedate>filesize>isdir>filepath"信息作为构造函数的传递参数，进而实现相关属性的获取
    private long fileSize = 0;// 文件长度应该long型数据，否则大于2GB的文件大小无法表达
    private String fileName = "$error";// 文件名称，不含目录信息,默认值用于表示文件出错
    private String filePath = ".\\";// 该文件对象所处的目录，默认值为当前相对目录
    private String fileSizeStr = "0";// 文件的大小，用字符串表示，能智能地选择B、KB、MB、GB来表达
    private int fileType = 0;// fileType=0为文件，fileType=1为目录，fileType=2为盘符
    private String fileModifiedDate = "1970-01-01 00:00:00";// 文件最近修改日期，默认值为1970年基准时间

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSizeStr() {
        return fileSizeStr;
    }

    public void setFileSizeStr(String fileSizeStr) {
        this.fileSizeStr = fileSizeStr;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getFileModifiedDate() {
        return fileModifiedDate;
    }

    public void setFileModifiedDate(String fileModifiedDate) {
        this.fileModifiedDate = fileModifiedDate;
    }

    public NetFileData(String fileInfo, String filePath) {
        this.filePath = filePath.split(">")[4];
//        System.out.println("filePath:" + filePath);
        String[] split = fileInfo.split(">");
//        for (int i = 0; i < split.length; i++) {
//            System.out.println(String.format("%d's string is %s", i, split[i]));
//        }
        this.fileName = split[0];
        this.fileModifiedDate = split[1];
        this.fileSize = Long.parseLong(split[2]);
        this.fileType = Integer.parseInt(split[3]);
        if (this.fileSize > 800) {
            this.fileSizeStr = this.fileSize / 1024 + "KB";
            if ((this.fileSize / 1024) > 800) {
                Double fileMB = Double.parseDouble(String.valueOf(this.fileSize / 1024.0 / 1024.0));
                if (fileMB > 800) {
                    Double fileGB = Double.parseDouble(String.valueOf(fileMB / 1024.0));
                    this.fileSizeStr = String.format("%.2f GB", fileGB);
                } else {
                    this.fileSizeStr = String.format("%.2f MB", fileMB);
                }
            }
        } else {
            this.fileSizeStr = this.fileSize + " B";
        }

    }

    public NetFileData(long fileSize, String fileName, String filePath, String fileSizeStr, int fileType, String fileModifiedDate) {
        this.fileSize = fileSize;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSizeStr = fileSizeStr;
        this.fileType = fileType;
        this.fileModifiedDate = fileModifiedDate;
    }

    @Override
    public String toString() {
        return "NetFileData{" +
                "fileSize=" + fileSize +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileSizeStr='" + fileSizeStr + '\'' +
                ", fileType=" + fileType +
                ", fileModifiedDate='" + fileModifiedDate + '\'' +
                '}';
    }
}
