package my.TicketLawyers.stablelawfirm.model;

public class DataModel {

    String name;
    String version,phoneno,fax;
    int id_;
    int image;

    public DataModel(String name, String version, int id_, int image,String phoneno,String fax) {
        this.name = name;
        this.version = version;
        this.id_ = id_;
        this.image=image;
        this.phoneno=phoneno;
        this.fax=fax;
    }

    public String getName() {
        return name;
    }
    public String getPhoneno() {
        return phoneno;
    }
    public String getFax() {
        return fax;
    }
    public String getVersion() {
        return version;
    }
    public int getImage() {
        return image;
    }
    public int getId() {
        return id_;
    }
}