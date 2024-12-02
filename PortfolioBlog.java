import java.sql.*;
import java.util.Scanner;

public class PortfolioBlog {
    // JDBC connection variables
    private static final String URL = "jdbc:mysql://localhost:3306/PortfolioDB"; // Update with your DB URL
    private static final String USER = "root"; // Update with your MySQL username
    private static final String PASSWORD = "sql123"; // Your SQL password

    // Connect to the database
    private static Connection connect() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Connection failed!", e);
        }
    }

    // Add a new user
    public static void addUser(String username, String email, String password) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("User added successfully, Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if the user exists
    public static boolean isUserExist(int userId) {
        String sql = "SELECT COUNT(*) FROM users WHERE user_id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Add a new blog post
    public static void addBlogPost(int userId, String title, String content) {
        if (!isUserExist(userId)) {
            System.out.println("User with ID " + userId + " does not exist. Please add the user first.");
            return;
        }

        String sql = "INSERT INTO blog_posts (user_id, title, content) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, title);
            stmt.setString(3, content);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Blog post added successfully, Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch all blog posts
    public static void fetchAllBlogPosts() {
        String sql = "SELECT * FROM blog_posts";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("Post ID: " + rs.getInt("post_id"));
                System.out.println("User ID: " + rs.getInt("user_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Content: " + rs.getString("content"));
                System.out.println("Date Posted: " + rs.getTimestamp("date_posted"));
                System.out.println("----------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get user input for blog content
    public static void getBlogContentFromUser() {
        Scanner scanner = new Scanner(System.in);

        // Get user ID (for simplicity, we are assuming a valid user ID is provided)
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character left by nextInt()

        // Get blog title
        System.out.print("Enter blog title: ");
        String title = scanner.nextLine();

        // Get blog content
        System.out.print("Enter blog content: ");
        String content = scanner.nextLine();

        // Add blog post to the database
        addBlogPost(userId, title, content);
    }

    public static void main(String[] args) {
        // Uncomment below to add a user if needed
        // addUser("JohnDoe", "john.doe@example.com", "password123");

        // Get blog content from user and add to the database
        getBlogContentFromUser();

        // Fetch and display all blog posts
        fetchAllBlogPosts();
    }
}
