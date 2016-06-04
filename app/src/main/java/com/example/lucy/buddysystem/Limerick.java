package com.example.lucy.buddysystem;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by claudiamini on 5/17/16.
 */
public class Limerick {
    public Limerick() {
        setWhen(System.currentTimeMillis());
    }

    private String name;

    private Long when;

    private String lastName;

    private String tags;

    public String getBlather() {
        return lastName != null ? lastName : "";
    }

    public String getTags() {
        return tags != null ? tags : "";
    }

    public String getTitle() {
        return name != null ? name : "";
    }

    public long getWhen() {
        return when != null ? when.longValue() : 0L;
    }

    public void setBlather(String lastName) {
        this.lastName = (lastName != null ? lastName : "");
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setTitle(String name) {
        this.name = name != null ? name : "";
    }

    public void setWhen(long when) {
        this.when = when;
    }

    public String toJson() throws JSONException {
        JSONObject tmp = new JSONObject();
        tmp.put("when", getWhen());
        tmp.put("name", getTitle());
        tmp.put("lastName", getBlather());
        tmp.put("tags", getTags());
        return tmp.toString();

    }

    public static Limerick fromJson(String json) throws JSONException {
        JSONObject tmp = new JSONObject(json);
        Limerick entry = new Limerick();
        entry.setWhen(tmp.getLong("when"));
        entry.setTitle(tmp.getString("name"));
        entry.setBlather(tmp.getString("lastName"));
        entry.setTags(tmp.getString("tags"));
        return entry;

    }
}
