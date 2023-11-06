import com.semanticSquare.thrillio.DataStore;
import com.semanticSquare.thrillio.View;
import com.semanticSquare.thrillio.entities.Bookmark;
import com.semanticSquare.thrillio.entities.User;
import com.semanticSquare.thrillio.managers.BookmarkManager;
import com.semanticSquare.thrillio.managers.UserManager;

public class Main {
    private static User[] users;
    private static Bookmark[][] bookmarks;
    public static void loadData() {
        System.out.println(" 1. Loading data...");
        DataStore.loadData();

        users = UserManager.getInstance().getUsers();
        bookmarks = BookmarkManager.getInstance().getBookmarks();

        System.out.println("Printing data...");
        printUserData();
        printBookmarkData();
    }

    private static void printBookmarkData() {
        for(Bookmark[] bookmarkList: bookmarks) {
            for (Bookmark bookmark: bookmarkList) {
                System.out.println(bookmark);
            }
        }
    }

    private static void printUserData() {
        for(User user: users) {
            System.out.println(user);
        }
    }
    private static void startBookmarking() {
        System.out.println("\n 2. Bookmarking...");
        for(User user: users) {
            View.bookmark(user, bookmarks);
        }

    }
    public static void main(String[] args) {
        System.out.println("Hello world!");
        loadData();
        startBookmarking();
    }

}