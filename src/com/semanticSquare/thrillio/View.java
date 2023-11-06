package com.semanticSquare.thrillio;

import com.semanticSquare.thrillio.constants.BookmarkController;
import com.semanticSquare.thrillio.entities.Bookmark;
import com.semanticSquare.thrillio.entities.User;

public class View {
    public static void bookmark(User user, Bookmark[][] bookmarks) {
        System.out.println("\n" + user.getEmail() + " is bookmarking!");
        for (int i = 0; i < DataStore.USER_BOOKMARK_LIMIT; i++) {

            int typeOffset = (int) (Math.random() * DataStore.BOOKMARK_TYPE_COUNT);
            int bookmarkOffset = (int) (Math.random() * DataStore.BOOKMARK_COUNT_PER_TYPE);

            Bookmark bookmark = bookmarks[typeOffset][bookmarkOffset];
            System.out.println(bookmark);

            BookmarkController.getInstance().saveUserBookmark(user, bookmark);

        }
    }
}
