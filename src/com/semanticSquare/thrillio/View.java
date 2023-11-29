package com.semanticSquare.thrillio;

import com.semanticSquare.thrillio.constants.BookmarkController;
import com.semanticSquare.thrillio.constants.KidFriendlyStatus;
import com.semanticSquare.thrillio.constants.UserType;
import com.semanticSquare.thrillio.entities.Book;
import com.semanticSquare.thrillio.entities.Bookmark;
import com.semanticSquare.thrillio.entities.User;
import com.semanticSquare.thrillio.partner.Sharable;

import java.util.List;

public class View {

    public static void browse(User user, List<List<Bookmark>> bookmarks) {
        System.out.println("\n" + user.getEmail() + " is browsing items!");

        int bookmarkCount = 0;
        for(List<Bookmark> bookmarkList : bookmarks) {
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
                if (user.getUserType().equals(UserType.EDITOR) || user.getUserType().equals(UserType.CHIEF_EDITOR)) {

                    //Mark as kid-friendly
                    if (bookmark.isKidFriendlyEligible() && bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)) {
                        String kidFriendlyStatus = getKidFriendlyStatusDecision(bookmark);
                        if (!kidFriendlyStatus.equals(KidFriendlyStatus.UNKNOWN)) {
                            BookmarkController.getInstance().setKidFriendlyStatus(user, kidFriendlyStatus, bookmark);
                        }
                    }
                    //sharing!! --only kid-friendly bookmarks
                    if (bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.APPROVED)
                            && bookmark instanceof Sharable) {
                        boolean isShared =  getShareDecision();
                        if (isShared) {
                            BookmarkController.getInstance().share(user, bookmark);
                        }
                    }
                }
            }
        }
    }

    //todo: below methods simulate user input. After IO, we take input via console
    private static boolean getShareDecision() {
        return Math.random() < 0.5;
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
