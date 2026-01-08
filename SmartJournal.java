import java.io.File; // Untuk akses file
import java.io.FileNotFoundException; // Untuk menangani error jika file tidak ada
import java.util.ArrayList; // Untuk menyimpan daftar user
import java.util.Scanner; // Untuk membaca isi file

public class SmartJournal {

    // Metode static untuk memuat user dari UserData.txt
    public static ArrayList<User> loadUsers() {
        // Siapkan list kosong untuk menampung data user
        ArrayList<User> userList = new ArrayList<>();
        
        // Gunakan relative path sesuai tips di dokumen [cite: 205, 206]
        File file = new File("UserData.txt"); 

        try {
            Scanner scanner = new Scanner(file);

            // Loop selama masih ada baris di dalam file
            while (scanner.hasNextLine()) {
                // Baca 3 baris berturut-turut sesuai format soal [cite: 38-43]
                String email = scanner.nextLine();
                
                // Cek untuk menghindari error jika file tidak lengkap
                if (scanner.hasNextLine()) {
                    String name = scanner.nextLine();
                    
                    if (scanner.hasNextLine()) {
                        String password = scanner.nextLine();
                        
                        // Buat objek User baru dan masukkan ke dalam list
                        User newUser = new User(email, name, password);
                        userList.add(newUser);
                    }
                }
            }
            scanner.close();
            System.out.println("Berhasil memuat " + userList.size() + " user.");

        } catch (FileNotFoundException e) {
            System.out.println("Error: File UserData.txt tidak ditemukan.");
            e.printStackTrace();
        }

        return userList;
    }

    // Main method untuk mengetes apakah data terbaca
    public static void main(String[] args) {
        // Panggil metode loadUsers
        ArrayList<User> loadedUsers = loadUsers();

        // Tes print data user pertama untuk memastikan benar
        if (!loadedUsers.isEmpty()) {
            System.out.println("User pertama: " + loadedUsers.get(0).getDisplayName());
        }
    }
}