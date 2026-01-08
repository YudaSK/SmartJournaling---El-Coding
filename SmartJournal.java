import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Scanner;

public class SmartJournal {

    // --- 1. MAIN METHOD (JANTUNG PROGRAM) ---
    // Ini harus ada agar program bisa di-run
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // A. Load Data User
        ArrayList<User> userList = loadUsers();
        
        // Cek apakah data berhasil dimuat
        if (userList.isEmpty()) {
            System.out.println("Gagal memuat data user atau UserData.txt kosong.");
            System.out.println("Pastikan file UserData.txt ada di folder proyek.");
            return; // Stop program jika data tidak ada
        }

        // B. Proses Login
        User currentUser = handleLogin(scanner, userList);

        // C. Tampilkan Menu Utama jika login berhasil
        if (currentUser != null) {
            showMainMenu(currentUser, scanner);
        }
        
        scanner.close();
    }

    // --- 2. METODE PENDUKUNG ---

    // Metode: Memuat user dari UserData.txt
    public static ArrayList<User> loadUsers() {
        ArrayList<User> userList = new ArrayList<>();
        // Menggunakan relative path [cite: 205]
        File file = new File("UserData.txt"); 

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // Membaca 3 baris: Email -> Nama -> Password [cite: 38-43]
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
            // Optional: Hapus baris print ini jika mengganggu
            // System.out.println("Debug: Berhasil memuat " + userList.size() + " user.");

        } catch (FileNotFoundException e) {
            System.out.println("Error: File UserData.txt tidak ditemukan.");
        }
        return userList;
    }

    // Metode: Menangani Login
    public static User handleLogin(Scanner scanner, ArrayList<User> userList) {
        System.out.println("\n=== LOGIN ===");
        while (true) {
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Password: ");
            String pass = scanner.nextLine();
            
            // Cek kecocokan data
            for (User u : userList) {
                if (u.getEmail().equals(email) && u.getPassword().equals(pass)) {
                    return u; // Login sukses
                }
            }
            System.out.println("Login gagal! Email atau password salah. Coba lagi.\n");
        }
    }

    // Metode: Mendapatkan sapaan waktu GMT+8 [cite: 56]
    public static String getGreeting() {
        LocalTime now = LocalTime.now(ZoneId.of("Asia/Kuala_Lumpur"));
        int hour = now.getHour();
        if (hour >= 0 && hour < 12) return "Good Morning";
        else if (hour >= 12 && hour < 17) return "Good Afternoon";
        else return "Good Evening";
    }

    // Metode: Menampilkan Menu Utama (Lengkap dengan Switch Case)
    public static void showMainMenu(User user, Scanner scanner) {
        boolean running = true;

        while (running) {
            // Tampilkan sapaan dan nama user [cite: 54]
            System.out.println("\n========================================");
            System.out.println(getGreeting() + ", " + user.getDisplayName());
            System.out.println("========================================");
            
            System.out.println("1. Create, Edit & View Journals");
            System.out.println("2. View Weekly Mood Summary");
            System.out.println("3. Exit");
            System.out.print("Pilih opsi (1-3): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // Nanti kita isi logika Jurnal di sini (Step 4)
                    System.out.println(">> Masuk ke menu Jurnal... (Fitur sedang dibuat)");
                    break;
                case "2":
                    // Nanti kita isi logika Summary di sini (Step 6)
                    System.out.println(">> Masuk ke menu Summary... (Fitur sedang dibuat)");
                    break;
                case "3":
                    System.out.println("Terima kasih, sampai jumpa!");
                    running = false; // Keluar dari loop while
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Harap pilih 1, 2, atau 3.");
            }
        }
    }
}