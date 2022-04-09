package wgp.task2.data;

public class LinkData {
    String linkName;
    String linkIp;
    int linkPort;
    long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LinkData(String linkName, String linkIp, int linkPort) {
        this.linkName = linkName;
        this.linkIp = linkIp;
        this.linkPort = linkPort;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkIp() {
        return linkIp;
    }

    public void setLinkIp(String linkIp) {
        this.linkIp = linkIp;
    }

    public int getLinkPort() {
        return linkPort;
    }

    public void setLinkPort(int linkPort) {
        this.linkPort = linkPort;
    }

    @Override
    public String toString() {
        return "LinkData{" +
                "linkName='" + linkName + '\'' +
                ", linkIp='" + linkIp + '\'' +
                ", linkPort=" + linkPort +
                ", id=" + id +
                '}';
    }
}
