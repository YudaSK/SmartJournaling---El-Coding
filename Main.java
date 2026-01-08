import java.util.*;

public class Main {
    public static void main(String[] args) {
        // DEBUG: Show where we are
        System.out.println("Current directory: " + System.getProperty("user.dir"));
        System.out.println("UserData.txt exists: " + new java.io.File("UserData.txt").exists());

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== SMART JOURNAL ===");

        while (true) {
            if (!AuthService.isLoggedIn()) {
                System.out.println("\n1. Login");
                System.out.println("2. Exit");
                System.out.print("Choice: ");

                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    AuthService.login();
                } else if (choice.equals("2")) {
                    System.out.println("Goodbye!");
                    break;
                }
            } else {
                User user = AuthService.getCurrentUser();
                System.out.println("\n=== MAIN MENU ===");
                System.out.println("Logged in as: " + user.getDisplayName());
                System.out.println("1. View my info");
                System.out.println("2. Logout");
                System.out.print("Choice: ");

                String choice = scanner.nextLine();


            }
            if (choice.equals("1")) {
                System.out.println("Email: " + user.getEmail());
                System.out.println("Name: " + user.getDisplayName());
            } else if (choice.equals("2")) {
                AuthService.logout();
            }
        }

        scanner.close();
    }

    private static void showMainApp(User user, Scanner scanner) {

        while (AuthService.isLoggedIn()) {
            // MODULE 2: Show menu
            int choice = WelcomeService.showMainMenu(scanner);

            switch (choice) {
                case 1: // Journals (Module 3)
                    System.out.println("\n Journal feature coming soon (Module 3)!");
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine();
                    break;

                case 2: // Weekly Summary (Module 2)
                    SummaryService.showWeeklySummary(user.getEmail());
                    break;

                case 3: // Profile
                    WelcomeService.showProfile(user);
                    break;

                case 4: // Logout
                    AuthService.logout();
                    System.out.println("Logged out successfully! 👋\n");
                    return;

                default:
                    System.out.println("Invalid option! Please choose 1-4.");
            }
        }
    }
}