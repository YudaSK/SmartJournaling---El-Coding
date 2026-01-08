public class User {
    private String email;
    private String displayName;
    private String password;

    // Constructor
    public User(String email, String displayName, String password) {
        this.email = email;
        this.displayName = displayName;
        this.password = password;
    }

    // Getter methods (untuk mengambil data nanti saat Login)
    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {
        return password;
    }
}
