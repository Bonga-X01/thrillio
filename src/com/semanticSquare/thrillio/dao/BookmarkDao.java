package com.semanticSquare.thrillio.dao;

import com.semanticSquare.thrillio.DataStore;
import com.semanticSquare.thrillio.entities.Bookmark;
import com.semanticSquare.thrillio.entities.UserBookmark;
import com.semanticSquare.thrillio.entities.WebLink;

import java.util.ArrayList;
import java.util.List;

public class BookmarkDao {
    public List<List<Bookmark>> getBookmarks() {
        return DataStore.getBookmarks();
    }

    public void saveBookmark(UserBookmark userBookmark) {
        DataStore.add(userBookmark);
    }

    //in real application, we would have SQL or hibernate queries
    public List<WebLink> getAllWebLinks() {
        List<WebLink> result = new ArrayList<>();


        return result;
    }
    public List<WebLink> getWebLinks(WebLink.DownloadStatus downloadStatus) {
        List<WebLink> result = new ArrayList<>();


        return result;
    }
}
