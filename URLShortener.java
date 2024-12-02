import java.util.HashMap;
import java.util.Scanner;

public class URLShortener {
    private final HashMap<String, String> shortToLongMap = new HashMap<>();
    private final HashMap<String, String> longToShortMap = new HashMap<>();
    private final String domain = "https://www.youtube.com/watch?v=bDTUFufX-1s&t=11s";

    /**
     * Generate a short URL for the given long URL.
     * 
     * @param longURL The original URL to shorten.
     * @return The shortened URL.
     */
    public String shortenURL(String longURL) {
        if (longToShortMap.containsKey(longURL)) {
            return longToShortMap.get(longURL); // Return existing short URL for the long URL
        }
        String shortURL = domain + hashFunction(longURL);
        if (shortToLongMap.containsKey(shortURL)) {
            throw new IllegalStateException("Hash collision detected! Please use a better hash function.");
        }
        shortToLongMap.put(shortURL, longURL);
        longToShortMap.put(longURL, shortURL);
        return shortURL;
    }

    /**
     * Expand a short URL back to its original long URL.
     * 
     * @param shortURL The shortened URL to expand.
     * @return The original long URL.
     * @throws IllegalArgumentException if the short URL is invalid.
     */
    public String expandURL(String shortURL) {
        if (!shortToLongMap.containsKey(shortURL)) {
            throw new IllegalArgumentException("Short URL not found: " + shortURL);
        }
        return shortToLongMap.get(shortURL);
    }

    /**
     * A basic hash function to generate a unique identifier for the long URL.
     * 
     * @param longURL The original URL to hash.
     * @return A short hash string.
     */
    private String hashFunction(String longURL) {
        return Integer.toHexString(longURL.hashCode());
    }

    public static void main(String[] args) {
        URLShortener urlShortener = new URLShortener();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the URL Shortener!");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Shorten a URL");
            System.out.println("2. Expand a short URL");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter the long URL: ");
                    String longURL = scanner.nextLine();
                    try {
                        String shortURL = urlShortener.shortenURL(longURL);
                        System.out.println("Shortened URL: " + shortURL);
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.print("Enter the short URL: ");
                    String shortURL = scanner.nextLine();
                    try {
                        String longURL = urlShortener.expandURL(shortURL);
                        System.out.println("Original URL: " + longURL);
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.err.println("Invalid choice. Please try again.");
            }
        }
    }
}
