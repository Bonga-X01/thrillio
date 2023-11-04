package com.semanticSquare.thrillio.dao;

import com.semanticSquare.thrillio.DataStore;
import com.semanticSquare.thrillio.entities.Bookmark;

public class BookmarkDao {
    public Bookmark[][] getBookmarks() {
        return DataStore.getBookmarks();
    }
}
