package com.example.sehci;

import com.google.appengine.api.datastore.Text;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Limerick {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private String name;

    @Persistent
    private String owner;

    @Persistent
    private Long when;

    @Persistent
    private Text lastName;

    @Persistent
    private Set<String> tags;

    public String getBlather() {
        return lastName != null ? lastName.getValue() : "";
    }

    public long getId() {
        return id != null ? id.longValue() : 0L;
    }

    public String getOwner() {
        return owner != null ? owner : "";
    }

    public String getTags() {
        StringBuffer rv = new StringBuffer();
        if (tags != null)
            for (String tag : tags) {
                if (rv.length() > 0)
                    rv.append(' ');
                rv.append(tag);
            }
        return rv.toString();
    }

    public String getTitle() {
        return name != null ? name : "";
    }

    public long getWhen() {
        return when != null ? when.longValue() : 0L;
    }

    public void setBlather(String lastName) {
        this.lastName = new Text(lastName != null ? lastName : "");
    }

    public void setOwner(String owner) {
        this.owner = owner != null ? owner : "";
    }

    public void setTags(Set<String> tags) {
        this.tags = tags != null ? tags : new HashSet<String>();
    }

    public void setTags(String tags) {
        Set<String> set = new HashSet<String>();
        if (tags != null)
            for (String str : tags.split(" "))
                set.add(str.toLowerCase());
        this.tags = set;
    }

    public void setTitle(String name) {
        this.name = name != null ? name : "";
    }

    public void setWhen(long when) {
        this.when = when;
    }

    public static Limerick loadById(long id, PersistenceManager pm) {
        return pm.getObjectById(Limerick.class, id);
    }

    public static void delete(Limerick entry, PersistenceManager pm) {
        pm.deletePersistent(entry);
    }

    public static List<Limerick> listByOwner(String username, PersistenceManager pm) {
        Query query = pm.newQuery(Limerick.class, "owner == :oo");
        @SuppressWarnings("unchecked")
        List<Limerick> rv = (List<Limerick>) query.execute(username);
        rv.size();
        query.closeAll();
        return rv;
    }

    public static List<Limerick> listByQuery(String username, String tags, PersistenceManager pm) {
        Query query = pm.newQuery(Limerick.class, "owner == :oo && tags == :tt");
        @SuppressWarnings("unchecked")
        List<Limerick> rv = (List<Limerick>) query.execute(username, tags);
        rv.size();
        query.closeAll();
        return rv;
    }

}
