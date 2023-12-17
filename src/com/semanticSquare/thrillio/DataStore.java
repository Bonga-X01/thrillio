package com.semanticSquare.thrillio;

import com.semanticSquare.thrillio.constants.BookGenre;
import com.semanticSquare.thrillio.constants.Gender;
import com.semanticSquare.thrillio.constants.MovieGenre;
import com.semanticSquare.thrillio.constants.UserType;
import com.semanticSquare.thrillio.entities.*;
import com.semanticSquare.thrillio.managers.BookmarkManager;
import com.semanticSquare.thrillio.managers.UserManager;
import com.semanticSquare.thrillio.util.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataStore {
    public static final int USER_BOOKMARK_LIMIT = 5;
    public static final int BOOKMARK_TYPE_COUNT = 3;
    public static final int BOOKMARK_COUNT_PER_TYPE = 5;
    public static final int TOTAL_USER_COUNT = 5;
    private static List<User> users = new ArrayList<>();
    private static List<List<Bookmark>> bookmarks = new ArrayList<>();
    public static List<List<Bookmark>> getBookmarks() {
        return bookmarks;
    }
    public static List<User> getUsers() {
        return users;
    }


    public static void loadData() {
        // JDBC URL, username, and password of MySQL server
        String jdbcUrl = "jdbc:mysql://localhost:3306/jid_thrillio";
        String username = "root";
        String password = "mySQL@2001"; /*or mySQL5957*/

        try {
            // 1. Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Establish a connection
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            if (connection != null) {
                System.out.println("Connected to the database!");

                // Perform database operations here

                try (Statement stm = connection.createStatement()) {
                    loadUsers(stm);
                    loadWeblinks(stm);
                    loadMovies(stm);
                    loadBooks(stm);
                }

                // 3. Close the connection when done
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void loadUsers(Statement stmt) throws SQLException {

        String query = "SELECT * from user";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            long id = rs.getLong("id");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            int genderId = rs.getInt("gender_id");
            int userTypeId = rs.getInt("user_type_id");

            Date createdDate = rs.getDate("created_date");
            System.out.println("createdDate: " + createdDate);

            System.out.println("id: " + id + ", email: " + email + ", password: "
                    + password + ", firstName: " + firstName + ", lastName: " + lastName
                    + "genderId: " + genderId + ", userTypeId: " + userTypeId);


            User user = UserManager.getInstance().createUser(id, email, password, firstName, lastName, Gender.getGender(genderId), UserType.getUserType(userTypeId));
            users.add(user);
        }
    }
    public static void loadWeblinks(Statement stmt) throws SQLException {
        String query = "SELECT * from weblink";
        ResultSet rs = stmt.executeQuery(query);

        List<Bookmark> bookmarkList = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            String url = rs.getString("url");
            String host = rs.getString("host");
            int kidFrientlyStatus = rs.getInt("kid_friendly_status");

            Date createdDate = rs.getDate("created_date");
            System.out.println("createdDate: " + createdDate);

            System.out.println("id: " + id + ", title: " + title + ", url: " + url + ", host: " + host + ", kidFrientlyStatus: " + kidFrientlyStatus);

            Bookmark bookmark = BookmarkManager.getInstance().createWebLink(id, title, url, host);
            bookmarkList.add(bookmark);
        }
        bookmarks.add(bookmarkList);
    }
    private static void loadMovies(Statement stmt) throws SQLException {
        String query = "Select m.id, title, release_year, GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast, GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors, movie_genre_id, imdb_rating"
                + " from Movie m, Actor a, Movie_Actor ma, Director d, Movie_Director md "
                + "where m.id = ma.movie_id and ma.actor_id = a.id and "
                + "m.id = md.movie_id and md.director_id = d.id group by m.id";
        ResultSet rs = stmt.executeQuery(query);

        List<Bookmark> bookmarkList = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            int releaseYear = rs.getInt("release_year");
            String[] cast = rs.getString("cast").split(",");
            String[] directors = rs.getString("directors").split(",");
            int genre_id = rs.getInt("movie_genre_id");
            MovieGenre genre = MovieGenre.values()[genre_id];
            double imdbRating = rs.getDouble("imdb_rating");

            Bookmark bookmark = BookmarkManager.getInstance().createMovie(id, title, "", releaseYear, cast, directors, genre, imdbRating/*, values[7]*/);
            bookmarkList.add(bookmark);
        }
        bookmarks.add(bookmarkList);
    }
    private static void loadBooks(Statement stmt) throws SQLException {
        String query = "Select b.id, title, publication_year, p.name, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, amazon_rating, created_date"
                + " from Book b, Publisher p, Author a, Book_Author ba "
                + "where b.publisher_id = p.id and b.id = ba.book_id and ba.author_id = a.id group by b.id";
        ResultSet rs = stmt.executeQuery(query);

        List<Bookmark> bookmarkList = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            int publicationYear = rs.getInt("publication_year");
            String publisher = rs.getString("name");
            String[] authors = rs.getString("authors").split(",");
            int genre_id = rs.getInt("book_genre_id");
            BookGenre genre = BookGenre.values()[genre_id];
            double amazonRating = rs.getDouble("amazon_rating");

            Date createdDate = rs.getDate("created_date");
            System.out.println("createdDate: " + createdDate);
            Timestamp timeStamp = rs.getTimestamp(8);
            System.out.println("timeStamp: " + timeStamp);
            System.out.println("localDateTime: " + timeStamp.toLocalDateTime());

            System.out.println("id: " + id + ", title: " + title + ", publication year: " + publicationYear + ", publisher: " + publisher + ", authors: " + String.join(", ", authors) + ", genre: " + genre + ", amazonRating: " + amazonRating);

            Bookmark bookmark = BookmarkManager.getInstance().createBook(id, title, publicationYear, publisher, authors, genre, amazonRating/*, values[7]*/);
            bookmarkList.add(bookmark);
        }
        bookmarks.add(bookmarkList);
    }
    private static List<UserBookmark> userBookmarks = new ArrayList<>();

    public static void add(UserBookmark userBookmark) {
        userBookmarks.add(userBookmark);
    }
}
