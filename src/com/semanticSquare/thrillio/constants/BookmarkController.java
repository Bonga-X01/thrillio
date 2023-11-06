package com.semanticSquare.thrillio.constants;

import com.semanticSquare.thrillio.entities.Bookmark;
import com.semanticSquare.thrillio.entities.User;
import com.semanticSquare.thrillio.managers.BookmarkManager;

public class BookmarkController {
    private static BookmarkController instance = new BookmarkController();
    private BookmarkController() {}
    public static BookmarkController getInstance() {
        return instance;
    }

    public void saveUserBookmark(User user, Bookmark bookmark) {
        BookmarkManager.getInstance().saveUserBookmark(user, bookmark);
    }
}
