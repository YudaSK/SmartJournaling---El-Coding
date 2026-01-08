import java.util.Scanner;

public class WelcomeService {

    public static void showWelcome(String displayName) {
        String greeting = DateUtils.getGreeting();
        String date = DateUtils.getFormattedDate();
        String day = DateUtils.getDayOfWeek();

        System.out.println("\n" + "=".repeat(40));
        System.out.println(greeting + ", " + displayName + "! 👋");
        System.out.println("Today is " + day + ", " + date);
        System.out.println("=".repeat(40) + "\n");
    }

    public static int showMainMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("📖 MAIN MENU");
        System.out.println("1. 📝 Create / View Journals");
        System.out.println("2. 📊 View Weekly Summary");
        System.out.println("3. 👤 View Profile");
        System.out.println("4. 🚪 Logout");
        System.out.print("\nChoose an option (1-4): ");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1; // Invalid input
        }
    }

    public static void showProfile(User user) {
        System.out.println("\n=== YOUR PROFILE ===");
        System.out.println("Email: " + user.getEmail());
        System.out.println("Display Name: " + user.getDisplayName());
        System.out.println("Member since: Today! 😄");
        System.out.println("====================\n");
    }
}