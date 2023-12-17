package com.semanticSquare.thrillio.dao;

import com.semanticSquare.thrillio.DataStore;
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
}
