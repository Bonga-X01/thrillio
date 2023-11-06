package com.semanticSquare.thrillio;

import com.semanticSquare.thrillio.constants.BookmarkController;
import com.semanticSquare.thrillio.constants.KidFriendlyStatus;
import com.semanticSquare.thrillio.constants.UserType;
import com.semanticSquare.thrillio.entities.Book;
import com.semanticSquare.thrillio.entities.Bookmark;
import com.semanticSquare.thrillio.entities.User;

public class View {

    public static void browse(User user, Bookmark[][] bookmarks) {
        System.out.println("\n" + user.getEmail() + " is browsing items!");

        int bookmarkCount = 0;
        for(Bookmark[] bookmarkList : bookmarks) {
            for(Bookmark bookmark : bookmarkList) {
                //bookmarking!!
                if(bookmarkCount < DataStore.USER_BOOKMARK_LIMIT) {
                    boolean isBookmarked = getBookmarkDecision(bookmark);
                    if (isBookmarked) {
                        bookmarkCount++;
                    BookmarkController.getInstance().saveUserBookmark(user, bookmark);
                    System.out.println("bookmark = " + bookmark);
                    }
                }
        if (user.getUserType().equals(UserType.EDITOR)
                || user.getUserType().equals(UserType.CHIEF_EDITOR)) {
            if (bookmark.isKidFriendlyEligible()
                    && bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)) {
                String kidFriendlyStatus = getKidFriendlyStatusDecision(bookmark);
                if (!kidFriendlyStatus.equals(KidFriendlyStatus.UNKNOWN)) {
                    bookmark.setKidFriendlyStatus(kidFriendlyStatus);
                    System.out.println("Kid-friendly status: " + kidFriendlyStatus + ", " + bookmark);
                }
            }
        }
            }
        }
    }

    private static String getKidFriendlyStatusDecision(Bookmark bookmark) {
        double randomVal = Math.random();
        return randomVal < 0.4 ? KidFriendlyStatus.APPROVED :
                randomVal < 0.8 ? KidFriendlyStatus.REJECTED :
                        KidFriendlyStatus.UNKNOWN;
    }

    private static boolean getBookmarkDecision(Bookmark bookmark) {
        return Math.random() < 0.5;
    }
}
