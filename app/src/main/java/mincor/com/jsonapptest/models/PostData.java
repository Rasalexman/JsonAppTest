package mincor.com.jsonapptest.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import mincor.com.jsonapptest.consts.Constants;

public class PostData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("text_id")
    @Expose
    private String textId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("last_update")
    @Expose
    private String lastUpdate;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("rubric")
    @Expose
    private String rubric;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("spb")
    @Expose
    private Integer spb;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("forum_id")
    @Expose
    private Integer forumId;
    @SerializedName("lead")
    @Expose
    private String lead;

    private List<JsonData> jsons = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRubric() {
        return rubric;
    }

    public void setRubric(String rubric) {
        this.rubric = rubric;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSpb() {
        return spb;
    }

    public void setSpb(Integer spb) {
        this.spb = spb;
    }

    public List<JsonData> getJsons() {
        return jsons;
    }

    public void setJsons(List<JsonData> jsonsData) {
        this.jsons = jsonsData;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getForumId() {
        return forumId;
    }

    public void setForumId(Integer forumId) {
        this.forumId = forumId;
    }

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public String getJsonContent(String contentType){
        final List<JsonData> jsonDatas = getJsons();
        String contentStr = "";
        String type;
        Object jsonContent;
        if(jsonDatas.size() > 0) {
            for(JsonData jsonData : jsonDatas){
                type = jsonData.getType();
                jsonContent = jsonData.getContent();
                if(type.equals(contentType)) {
                    if(contentType.equals(Constants.CONTENT_IMAGE) && jsonContent instanceof ArrayList){
                        contentStr = ((LinkedTreeMap)((ArrayList) jsonContent).get(0)).get("url").toString();
                        break;
                    }else if(contentType.equals(Constants.CONTENT_TEXT) && jsonContent instanceof String){
                        contentStr = (String)jsonContent;
                        break;
                    }
                }
            }
        }
        return contentStr;
    }
}