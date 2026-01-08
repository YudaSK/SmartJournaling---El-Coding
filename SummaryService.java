import java.util.ArrayList;
import java.util.List;

public class SummaryService {

    // Mock data for testing - replace with real data later
    public static void showWeeklySummary(String userEmail) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📊 WEEKLY SUMMARY");
        System.out.println("=".repeat(50));

        // Mock data - will connect to real journals in Module 3
        System.out.println("Date       | Weather       | Mood      | Journal Preview");
        System.out.println("-".repeat(50));

        String[][] mockData = {
                {"2025-10-05", "Sunny ☀️", "POSITIVE 😊", "Had a great day at the park..."},
                {"2025-10-06", "Rainy 🌧️", "NEUTRAL 😐", "Stayed indoors, watched movies..."},
                {"2025-10-07", "Cloudy ⛅", "POSITIVE 😊", "Finished my assignment!"},
                {"2025-10-08", "Stormy ⛈️", "NEGATIVE 😔", "Traffic was terrible..."},
                {"2025-10-09", "Sunny ☀️", "POSITIVE 😊", "Met friends for lunch 🍔"},
                {"2025-10-10", "Rainy 🌧️", "NEUTRAL 😐", "Just another day..."},
                {"2025-10-11", "Cloudy ⛅", "POSITIVE 😊", "Working on Smart Journal! 💻"}
        };

        for (String[] day : mockData) {
            System.out.printf("%-10s | %-13s | %-9s | %s\n",
                    day[0], day[1], day[2], day[3]);
        }

        System.out.println("\n💡 Insights: You had 4 positive days this week!");
        System.out.println("🌤️  Weather: Mostly sunny with some rain");
        System.out.println("=".repeat(50) + "\n");

        System.out.println("Press Enter to continue...");
        try {
            System.in.read();
        } catch (Exception e) {}
    }
}