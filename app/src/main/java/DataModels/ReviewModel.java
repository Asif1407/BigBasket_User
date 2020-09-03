package DataModels;

public class ReviewModel {

    String Name;
    String Description;
    String ImageUrl;

    public ReviewModel() {
        // to prevent from crashing.
    }

    public ReviewModel(String name, String description, String imageUrl) {
        Name = name;
        Description = description;
        ImageUrl = imageUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
