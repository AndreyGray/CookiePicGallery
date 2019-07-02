package andreyskakunenko.myapplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("license")
    @Expose
    private String license;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("filter")
    @Expose
    private String filter;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("tagMode")
    @Expose
    private String tagMode;

    public Item(String file, String owner) {
        this.file = file;
        this.owner = owner;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTagMode() {
        return tagMode;
    }

    public void setTagMode(String tagMode) {
        this.tagMode = tagMode;
    }

}
