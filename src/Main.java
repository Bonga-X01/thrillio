import com.semanticSquare.thrillio.DataStore;
import com.semanticSquare.thrillio.View;
import com.semanticSquare.thrillio.bgjobs.WebpageDownloaderTask;
import com.semanticSquare.thrillio.entities.Bookmark;
import com.semanticSquare.thrillio.entities.User;
import com.semanticSquare.thrillio.managers.BookmarkManager;
import com.semanticSquare.thrillio.managers.UserManager;

import java.util.List;

public class Main {
    private static List<User> users;
    private static List<List<Bookmark>> bookmarks;
    public static void loadData() {
        System.out.println(" 1. Loading data...");
        DataStore.loadData();

        users = UserManager.getInstance().getUsers();
        bookmarks = BookmarkManager.getInstance().getBookmarks();
    }

    private static void printBookmarkData() {
        for(List<Bookmark> bookmarkList: bookmarks) {
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
    private static void start() {
        for(User user: users) {
            View.browse(user, bookmarks);
        }

    }
    public static void main(String[] args) {

        loadData();
        start();

        //background jobs
        runDownloaderJob();
    }

    private static void runDownloaderJob() {
        WebpageDownloaderTask task = new WebpageDownloaderTask(true);
        (new Thread(task)).start();
    }

}