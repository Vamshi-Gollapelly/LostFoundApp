package com.vamshigollapelly.lostfoundapp;

public class LostFoundItem {
    private int    id;
    private String type;         // "Lost" or "Found"
    private String name;
    private String phone;
    private String description;
    private String date;
    private String location;
    private String category;
    private String imageUri;
    private String timestamp;    // Auto-generated

    public LostFoundItem() {}

    // ----- Getters -----
    public int    getId()           { return id; }
    public String getType()         { return type; }
    public String getName()         { return name; }
    public String getPhone()        { return phone; }
    public String getDescription()  { return description; }
    public String getDate()         { return date; }
    public String getLocation()     { return location; }
    public String getCategory()     { return category; }
    public String getImageUri()     { return imageUri; }
    public String getTimestamp()    { return timestamp; }

    // ----- Setters -----
    public void setId(int id)                  { this.id = id; }
    public void setType(String type)           { this.type = type; }
    public void setName(String name)           { this.name = name; }
    public void setPhone(String phone)         { this.phone = phone; }
    public void setDescription(String d)       { this.description = d; }
    public void setDate(String date)           { this.date = date; }
    public void setLocation(String location)   { this.location = location; }
    public void setCategory(String category)   { this.category = category; }
    public void setImageUri(String imageUri)   { this.imageUri = imageUri; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

}
