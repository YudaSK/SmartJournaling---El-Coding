public class JournalEntry {
    private String date;
    private String content;
    private String weather;
    private String mood;

    public JournalEntry(String date, String content, String weather, String mood) {
        this.date = date;
        this.content = content;
        this.weather = weather;
        this.mood = mood;
    }

    // Getters
    public String getDate() { return date; }
    public String getContent() { return content; }
    public String getWeather() { return weather; }
    public String getMood() { return mood; }

    // Setters
    public void setContent(String content) { this.content = content; }
    public void setWeather(String weather) { this.weather = weather; }
    public void setMood(String mood) { this.mood = mood; }

    // Format for saving to text file (using | as separator)
    @Override
    public String toString() {
        return date + "|" + content + "|" + weather + "|" + mood;
    }
}
