package DataModels;

public class ReviewModel {

    String Name;
    String Description;
    String ImageUrl;
    float Ratings;

    public ReviewModel() {
        // to prevent from crashing.
    }

    public ReviewModel(String name, String description, String imageUrl, float ratings) {
        Name = name;
        Description = description;
        ImageUrl = imageUrl;
        Ratings = ratings;
    }

    public float getRatings() {
        return Ratings;
    }

    public void setRatings(float ratings) {
        Ratings = ratings;
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
