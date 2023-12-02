package com.semanticSquare.thrillio;

import com.semanticSquare.thrillio.constants.BookGenre;
import com.semanticSquare.thrillio.constants.Gender;
import com.semanticSquare.thrillio.constants.MovieGenre;
import com.semanticSquare.thrillio.entities.*;
import com.semanticSquare.thrillio.managers.BookmarkManager;
import com.semanticSquare.thrillio.managers.UserManager;
import com.semanticSquare.thrillio.util.*;

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
        loadUsers();
        loadWeblinks();
        loadMovies();
        loadBooks();
    }
    private static void loadUsers() {

//        String[] data = new String[TOTAL_USER_COUNT];
        List<String> data = new ArrayList<>();
        IOUtil.read(data, "src\\User+"); //read data from file 'User+' into data array
        for(String row: data) {
            String[] values = row.split("\t");

            Gender gender = Gender.MALE;
            if (values[5].equals("f")) {
                gender = Gender.FEMALE;
            }else if (values[5].equals("t")) {
                gender = Gender.TRANSGENDER;
            }

            User user = UserManager.getInstance().createUser(Long.parseLong(values[0]), values[1], values[2], values[3], values[4], gender, values[6]);
            users.add(user);
        }
    }
    public static void loadWeblinks() {

//        String[] data = new String[TOTAL_USER_COUNT];
        List<String> data = new ArrayList<>();
        IOUtil.read(data,"src\\Web+Link+");
        List<Bookmark> weblinks = new ArrayList<>();
        for(String row: data) {
            String[] values = row.split("\t");
            Bookmark bookmark = BookmarkManager.getInstance().createWebLink(Long.parseLong(values[0]), values[1], values[2], values[3]);
            weblinks.add(bookmark);
        }
        bookmarks.add(weblinks);
    }
    public static void loadMovies() {

//        String[] data = new String[TOTAL_USER_COUNT];
        List<String> data = new ArrayList<>();
        IOUtil.read(data, "src\\Movie+");
        List<Bookmark> movies = new ArrayList<>();
        int colNum = 0;
        for (String row : data) {
            String[] values = row.split("\t");
            String[] cast = values[3].split(",");
            String[] directors = values[4].split(",");
            Movie movie = BookmarkManager.getInstance().createMovie(Long.parseLong(values[0]), values[1], "", Integer.parseInt(values[2]), cast, directors, MovieGenre.valueOf(values[5]), Double.parseDouble(values[6]));
            movies.add(movie);
        }
        bookmarks.add(movies);
    }
    public static void loadBooks() {

//        String[] data = new String[TOTAL_USER_COUNT];
        List<String> data = new ArrayList<>();
        IOUtil.read(data, "src\\Book+");
        List<Bookmark> books = new ArrayList<>();
        int colNum = 0;
        for (String row : data) {
            String[] values = row.split("\t");
            String[] authors = values[4].split(",");
            Book book = BookmarkManager.getInstance().createBook(Long.parseLong(values[0]), values[1], Integer.parseInt(values[2]), values[3], authors, BookGenre.valueOf(values[5]), Double.parseDouble(values[6])/*, values[7]*/);
            books.add(book);
        }
        bookmarks.add(books);
    }
    private static List<UserBookmark> userBookmarks = new ArrayList<>();

    public static void add(UserBookmark userBookmark) {
        userBookmarks.add(userBookmark);
    }
}
