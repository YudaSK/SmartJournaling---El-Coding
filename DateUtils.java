import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    // Get current time in GMT+8 (Malaysia time)
    public static LocalDateTime getMalaysiaTime() {
        return LocalDateTime.now(ZoneId.of("Asia/Kuala_Lumpur"));
    }

    // Get time-based greeting
    public static String getGreeting() {
        int hour = getMalaysiaTime().getHour();

        if (hour >= 0 && hour < 12) {
            return "Good Morning";
        } else if (hour >= 12 && hour < 17) {
            return "Good Afternoon";
        } else {
            return "Good Evening";
        }
    }

    // Get current date in "yyyy-MM-dd" format
    public static String getCurrentDate() {
        return getMalaysiaTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    // Get date for display "Oct 11, 2025"
    public static String getFormattedDate() {
        return getMalaysiaTime().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
    }

    // Get day name "Monday", "Tuesday", etc.
    public static String getDayOfWeek() {
        return getMalaysiaTime().format(DateTimeFormatter.ofPattern("EEEE"));
    }
}