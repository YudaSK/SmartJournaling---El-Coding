import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SmartJournal {

    // Global variables for storage
    private static ArrayList<JournalEntry> journalList = new ArrayList<>();
    private static final String JOURNAL_FILE = "JournalData.txt";
    
    // API & Env variables
    private static API api = new API();
    private static Map<String, String> env;
    
    // API URLs
    private static final String WEATHER_API_URL = "https://api.data.gov.my/weather/forecast/?contains=WP%20Kuala%20Lumpur@location__location_name&sort=date&limit=1";
    private static final String SENTIMENT_API_URL = "https://router.huggingface.co/hf-inference/models/distilbert/distilbert-base-uncased-finetuned-sst-2-english";

    // --- 1. MAIN METHOD (HEART OF THE PROGRAM) ---
    // This must exist so the program can be run
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // A. Load Environment Variables (.env)
        env = EnvLoader.loadEnv(".env");
        if (!env.containsKey("BEARER_TOKEN")) {
            System.out.println("Warning: BEARER_TOKEN not found in .env file.");
        }

        // B. Load Data (Users and Journals)
        ArrayList<User> userList = loadUsers();
        loadJournals(); // Load existing journals

        // Check if data loaded successfully
        if (userList.isEmpty()) {
            System.out.println("Failed to load user data or UserData.txt is empty.");
            System.out.println("Make sure UserData.txt file exists in the project folder.");
            return; // Stop program if data doesn't exist
        }

        // C. Process Login
        User currentUser = handleLogin(scanner, userList);

        // D. Display Main Menu if login is successful
        if (currentUser != null) {
            showMainMenu(currentUser, scanner);
        }
        
        scanner.close();
    }

    // --- 2. SUPPORTING METHODS ---

    // Method: Load users from UserData.txt
    public static ArrayList<User> loadUsers() {
        ArrayList<User> userList = new ArrayList<>();
        File file = new File("UserData.txt"); 

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // Read 3 lines: Email -> Name -> Password
                String email = scanner.nextLine();
                if (scanner.hasNextLine()) {
                    String name = scanner.nextLine();
                    if (scanner.hasNextLine()) {
                        String password = scanner.nextLine();
                        userList.add(new User(email, name, password));
                    }
                }
            }
            scanner.close();
            // Optional: Delete this print line if it's annoying
            // System.out.println("Debug: Successfully loaded " + userList.size() + " users.");

        } catch (FileNotFoundException e) {
            System.out.println("Error: File UserData.txt not found.");
        }
        return userList;
    }

    public static void loadJournals() {
        File file = new File(JOURNAL_FILE);
        if (!file.exists()) return; // If no file, just start empty

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Format: Date|Content|Weather|Mood
                String[] parts = line.split("\\|"); 
                if (parts.length >= 4) {
                    journalList.add(new JournalEntry(parts[0], parts[1], parts[2], parts[3]));
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error loading journals: " + e.getMessage());
        }
    }

    public static void saveJournals() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(JOURNAL_FILE))) {
            for (JournalEntry entry : journalList) {
                writer.println(entry.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving journals.");
        }
    }

    // Method: Handle Login
    public static User handleLogin(Scanner scanner, ArrayList<User> userList) {
        System.out.println("\n=== LOGIN ===");
        while (true) {
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Password: ");
            String pass = scanner.nextLine();
            
            // Check data match
            for (User u : userList) {
                if (u.getEmail().equals(email) && u.getPassword().equals(pass)) {
                    return u; // Login successful
                }
            }
            System.out.println("Login failed! Email or password is incorrect. Try again.\n");
        }
    }

    // Method: Get time greeting GMT+8
    public static String getGreeting() {
        LocalTime now = LocalTime.now(ZoneId.of("Asia/Kuala_Lumpur"));
        int hour = now.getHour();
        if (hour >= 0 && hour < 12) return "Good Morning";
        else if (hour >= 12 && hour < 17) return "Good Afternoon";
        else return "Good Evening";
    }

    // Method: Display Main Menu (Complete with Switch Case)
    public static void showMainMenu(User user, Scanner scanner) {
        boolean running = true;

        while (running) {
            // Display greeting and user name
            System.out.println("\n========================================");
            System.out.println(getGreeting() + ", " + user.getDisplayName());
            System.out.println("========================================");
            
            System.out.println("1. Create, Edit & View Journals");
            System.out.println("2. View Weekly Mood Summary");
            System.out.println("3. Exit");
            System.out.print("Choose option (1-3): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // We'll fill in the Journal logic here (Feature)
                    handleJournalPage(scanner);
                    break;
                case "2":
                    // We'll fill in the Summary logic here (Feature)
                    showWeeklySummary();
                    break;
                case "3":
                    System.out.println("Thank you, see you later!");
                    running = false; // Exit the while loop
                    break;
                default:
                    System.out.println("Invalid choice. Please choose 1, 2, or 3.");
            }
        }
    }

    // --- 3. FEATURE 1: JOURNAL PAGE ---

    public static void handleJournalPage(Scanner scanner) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        System.out.println("\n=== Journal Dates ===");
        // Show last 4 days including today
        for (int i = 3; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            String dateStr = date.format(formatter);
            String label = (i == 0) ? " (Today)" : "";
            System.out.println((4 - i) + ". " + dateStr + label);
        }

        System.out.print("Select a date to view/create journal: ");
        String choice = scanner.nextLine();
        LocalDate selectedDate = null;

        // Determine date from selection
        try {
            int selection = Integer.parseInt(choice);
            if (selection >= 1 && selection <= 4) {
                selectedDate = today.minusDays(4 - selection);
            }
        } catch (NumberFormatException e) { }

        if (selectedDate == null) {
            System.out.println("Invalid selection.");
            return;
        }

        String dateStr = selectedDate.format(formatter);
        JournalEntry entry = findEntryByDate(dateStr);

        // Logic based on date
        if (selectedDate.equals(today)) {
            if (entry == null) {
                System.out.println("No journal found for today.");
                System.out.println("1. Create Journal");
                System.out.println("2. Back");
                if (scanner.nextLine().equals("1")) createJournal(dateStr, scanner);
            } else {
                System.out.println("Journal exists.");
                System.out.println("1. View Journal");
                System.out.println("2. Edit Journal");
                System.out.println("3. Back");
                String subChoice = scanner.nextLine();
                if (subChoice.equals("1")) viewJournalEntry(entry);
                else if (subChoice.equals("2")) editJournal(entry, scanner);
            }
        } else {
            // Past dates
            if (entry != null) viewJournalEntry(entry);
            else System.out.println("No entry found for " + dateStr);
        }
    }

    public static JournalEntry findEntryByDate(String date) {
        for (JournalEntry entry : journalList) {
            if (entry.getDate().equals(date)) return entry;
        }
        return null;
    }

    public static void createJournal(String date, Scanner scanner) {
        System.out.println("\nEnter your journal entry for " + date + ":");
        String content = scanner.nextLine();

        // 1. Fetch Weather from API
        String weather = fetchWeather();
        
        // 2. Fetch Mood from API
        String mood = fetchMood(content);

        JournalEntry newEntry = new JournalEntry(date, content, weather, mood);
        journalList.add(newEntry);
        saveJournals(); // Save to file immediately
        
        System.out.println("Journal saved! [Weather: " + weather + ", Mood: " + mood + "]");
    }

    public static void editJournal(JournalEntry entry, Scanner scanner) {
        System.out.println("\nOriginal: " + entry.getContent());
        System.out.println("Enter new content:");
        String newContent = scanner.nextLine();
        
        entry.setContent(newContent);
        // Recalculate mood since content changed
        entry.setMood(fetchMood(newContent)); 
        
        saveJournals();
        System.out.println("Journal updated! New Mood: " + entry.getMood());
    }

    public static void viewJournalEntry(JournalEntry entry) {
        System.out.println("\n=== Journal Entry for " + entry.getDate() + " ===");
        System.out.println("Weather: " + entry.getWeather());
        System.out.println("Mood: " + entry.getMood());
        System.out.println("Content: " + entry.getContent());
        System.out.println("---------------------------------");
        System.out.println("Press Enter to go back.");
        Scanner tempScanner = new Scanner(System.in);
        tempScanner.nextLine();
        tempScanner.close();
    }

    // --- 4. API INTEGRATION METHODS ---

    public static String fetchWeather() {
        System.out.print("Fetching weather... ");
        try {
            // Call GET from API.java
            String jsonResponse = api.get(WEATHER_API_URL);
            
            // Extract "summary_forecast" value manually
            String key = "\"summary_forecast\":\"";
            int startIndex = jsonResponse.indexOf(key);
            if (startIndex != -1) {
                startIndex += key.length();
                int endIndex = jsonResponse.indexOf("\"", startIndex);
                return jsonResponse.substring(startIndex, endIndex);
            }
        } catch (Exception e) {
            System.out.println("(Offline/Error)");
        }
        return "Unknown";
    }

    public static String fetchMood(String text) {
        System.out.print("Analyzing mood... ");
        try {
            // Check for token
            String token = env.get("BEARER_TOKEN");
            if (token == null) return "Unknown (Token Missing)";

            // Prepare JSON body
            String jsonBody = "{\"inputs\": \"" + text + "\"}";
            
            // Call POST from API.java
            String jsonResponse = api.post(SENTIMENT_API_URL, token, jsonBody);

            // Extract "label" value manually
            String key = "\"label\":\"";
            int startIndex = jsonResponse.indexOf(key);
            if (startIndex != -1) {
                startIndex += key.length();
                int endIndex = jsonResponse.indexOf("\"", startIndex);
                return jsonResponse.substring(startIndex, endIndex);
            }
        } catch (Exception e) {
            System.out.println("(API Error)");
        }
        return "Neutral";
    }

    // --- 5. FEATURE 2: WEEKLY SUMMARY ---

    public static void showWeeklySummary() {
        System.out.println("\n=== Weekly Mood Summary ===");
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Loop last 7 days
        for (int i = 6; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            String dateStr = date.format(formatter);
            JournalEntry entry = findEntryByDate(dateStr);
            
            String mood = (entry != null) ? entry.getMood() : "No Entry";
            String weather = (entry != null) ? entry.getWeather() : "-";
            
            System.out.printf("%s : %-10s (Weather: %s)%n", dateStr, mood, weather);
        }
        System.out.println("Press Enter to continue...");
        Scanner tempScanner = new Scanner(System.in);
        tempScanner.nextLine();
        tempScanner.close();
    }
}