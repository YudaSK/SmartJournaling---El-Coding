import java.io.*;
import java.util.*;

public class FileHandler {

    public static List<User> loadUsers(String filePath) {
        List<User> users = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String email = line.trim();

                // Read next line for DISPLAY NAME
                String displayName = reader.readLine();
                if (displayName == null) break;

                // Read next line for PASSWORD
                String password = reader.readLine();
                if (password == null) break;

                // Skip any blank lines between users
                String blankLine = reader.readLine();

                // Create user with CORRECT order
                users.add(new User(email, displayName, password));
            }
            reader.close();

        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }

        return users;
    }

    public static void saveUser(String filePath, User user) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.write(user.getEmail() + "\n");
            writer.write(user.getDisplayName() + "\n");  // Display name SECOND
            writer.write(user.getPassword() + "\n");      // Password THIRD
            writer.write("\n"); // blank line between users
            writer.close();

        } catch (Exception e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }
}