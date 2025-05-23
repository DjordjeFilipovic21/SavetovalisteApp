package app.model;

public class Psychotherapist {
    private int psyId;
    private String username;
    private String fullName;
    private String password;
    private int imageId;





    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "Psychotherapist: " + username + ", Full Name: " + fullName;
    }

    public int getPsyId() {
        return psyId;
    }

    public void setPsyId(int psyId) {
        this.psyId = psyId;
    }
}