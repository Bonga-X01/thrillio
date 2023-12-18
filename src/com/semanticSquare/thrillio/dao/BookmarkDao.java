package com.semanticSquare.thrillio.dao;

import com.semanticSquare.thrillio.DataStore;
import com.semanticSquare.thrillio.constants.DatabaseInfo;
import com.semanticSquare.thrillio.entities.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookmarkDao {
    public List<List<Bookmark>> getBookmarks() {
        return DataStore.getBookmarks();
    }

    public void saveBookmark(UserBookmark userBookmark) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/jid_thrillio";
        String username = "root";
        String password = "mySQL@2001"; /*or mySQL5957*/

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement stm = connection.createStatement()) {
            if (userBookmark.getBookMark() instanceof Book) {
                saveUserBook(userBookmark, stm);
                
            } else if (userBookmark.getBookMark() instanceof Movie){
                saveUserMovie(userBookmark, stm);

            } else if (userBookmark.getBookMark() instanceof WebLink){
                saveUserWebLink(userBookmark, stm);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveUserWebLink(UserBookmark userBookmark, Statement stm) throws SQLException {
        String queryString = "INSERT INTO User_WebLink (user_id, weblink_id) values (" +
                userBookmark.getUser().getId() + ", " +
                userBookmark.getBookMark().getId() + "); ";
        stm.executeUpdate(queryString);
    }

    private void saveUserMovie(UserBookmark userBookmark, Statement stm) throws SQLException {
        String queryString = "INSERT INTO User_Movie (user_id, movie_id) values (" +
                userBookmark.getUser().getId() + ", " +
                userBookmark.getBookMark().getId() + "); ";
        stm.executeUpdate(queryString);
    }

    private void saveUserBook(UserBookmark userBookmark, Statement stm) throws SQLException {
        String queryString = "INSERT INTO User_Book (user_id, book_id) values (" +
                userBookmark.getUser().getId() + ", " +
                userBookmark.getBookMark().getId() + "); ";
        stm.executeUpdate(queryString);
    }

    public List<WebLink> getAllWebLinks() {
        List<WebLink> result = new ArrayList<>();
        List<List<Bookmark>> bookmarks = DataStore.getBookmarks();
        List<Bookmark> allWebLinks = bookmarks.get(0);
        for (Bookmark bookmark: allWebLinks) {
            result.add((WebLink) bookmark);
        }
        return result;
    }
    public List<WebLink> getWebLinks(WebLink.DownloadStatus downloadStatus) {
        List<WebLink> result = new ArrayList<>();
        List<WebLink> allWebLinks = getAllWebLinks();
        for (WebLink webLink : allWebLinks) {
            if(webLink.getDownloadStatus().equals(downloadStatus)) {
                result.add(webLink);
            }
        }
        return result;
    }

    public void updateKidFriendlyStatus(Bookmark bookmark) {
        int kidFriendlyStatus = bookmark.getKidFriendlyStatus().ordinal();
        long userId = bookmark.getKidFriendlyMarkedBy().getId();

        String tableToUpdate = "Book";
        if (bookmark instanceof Movie) {
            tableToUpdate = "Movie";
        } else if (bookmark instanceof WebLink) {
            tableToUpdate = "Weblink";
        }

        try (Connection connection = DriverManager.getConnection(DatabaseInfo.getJdbcUrl(), DatabaseInfo.getUsername(), DatabaseInfo.getPassword());
             Statement stm = connection.createStatement()) {
            System.out.println("Connected to the database!");
            String queryString =
                    "UPDATE " + tableToUpdate + " SET kid_friendly_status = " + kidFriendlyStatus
                    + ", kid_friendly_marked_by = " + userId + " WHERE id = " + bookmark.getId();
            System.out.println(queryString);
            stm.executeUpdate(queryString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sharedByInfo(Bookmark bookmark) {


        long userId = bookmark.getSharedBy().getId();
        String tableToUpdate = "Book";
        if (bookmark instanceof WebLink) {
            tableToUpdate = "Weblink";
        }
        try (Connection connection = DriverManager.getConnection(DatabaseInfo.getJdbcUrl(), DatabaseInfo.getUsername(), DatabaseInfo.getPassword());
             Statement stm = connection.createStatement()) {
            System.out.println("Connected to the database!");
            String queryString =
                    "UPDATE " + tableToUpdate + " SET shared_by = " + userId  + " WHERE id = " + bookmark.getId();
            System.out.println(queryString);
            stm.executeUpdate(queryString);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
