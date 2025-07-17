package edu.csueb.android.zoodirectory;

public class Animal {
    private String name;
    private String description;
    private int thumbnailId;
    private int imageId;

    public Animal(String name, String description, int thumbnailId, int imageId) {
        this.name = name;
        this.description = description;
        this.thumbnailId = thumbnailId;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getThumbnailId() {
        return thumbnailId;
    }

    public int getImageId() {
        return imageId;
    }
}