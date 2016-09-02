package cn.jsprun.domain;
public class Plugins  implements java.io.Serializable {
	private static final long serialVersionUID = -8450424719595861740L;
     private Short pluginid;
     private Byte available;
     private Byte adminid;
     private String name;
     private String identifier;
     private String description;
     private String datatables;
     private String directory;
     private String copyright;
     private String modules;
    public Plugins() {
    }
    public Plugins(Byte available, Byte adminid, String name, String identifier, String description, String datatables, String directory, String copyright, String modules) {
        this.available = available;
        this.adminid = adminid;
        this.name = name;
        this.identifier = identifier;
        this.description = description;
        this.datatables = datatables;
        this.directory = directory;
        this.copyright = copyright;
        this.modules = modules;
    }
    public Short getPluginid() {
        return this.pluginid;
    }
    public void setPluginid(Short pluginid) {
        this.pluginid = pluginid;
    }
    public Byte getAvailable() {
        return this.available;
    }
    public void setAvailable(Byte available) {
        this.available = available;
    }
    public Byte getAdminid() {
        return this.adminid;
    }
    public void setAdminid(Byte adminid) {
        this.adminid = adminid;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIdentifier() {
        return this.identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDatatables() {
        return this.datatables;
    }
    public void setDatatables(String datatables) {
        this.datatables = datatables;
    }
    public String getDirectory() {
        return this.directory;
    }
    public void setDirectory(String directory) {
        this.directory = directory;
    }
    public String getCopyright() {
        return this.copyright;
    }
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
    public String getModules() {
        return this.modules;
    }
    public void setModules(String modules) {
        this.modules = modules;
    }
}