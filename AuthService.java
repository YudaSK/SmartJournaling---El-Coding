import java.util.*;

public class AuthService {
    private static User currentUser = null;
    private static final String USER_FILE = "UserData.txt";

    public static User login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n=== LOGIN ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        List<User> users = FileHandler.loadUsers(USER_FILE);

        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Welcome back, " + user.getDisplayName() + "!");
                return user;
            }
        }

        System.out.println("Wrong email or password!");
        return null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void logout() {
        currentUser = null;
        System.out.println("Logged out!");
    }
}