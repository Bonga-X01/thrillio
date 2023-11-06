package test.entity;

import com.semanticSquare.thrillio.entities.WebLink;
import com.semanticSquare.thrillio.managers.BookmarkManager;
import org.junit.jupiter.api.Assertions;

class WebLinkTest {

    @org.junit.jupiter.api.Test
    void isKidFriendlyEligible() {


        //Test 1: porn in url --false
        WebLink weblink = BookmarkManager.getInstance().createWebLink(2000,
                "Taming Tiger, Part 2",
                "http://www.javaworld.com/article/2072759/core-java/taming-porn--part-2.html",
                "http://www.javaworld.com");
        boolean isKidFriendlyEligible;

        isKidFriendlyEligible = weblink.isKidFriendlyEligible();

        Assertions.assertFalse(isKidFriendlyEligible,"For porn in url - isKidFriendlyEligible() must return false");

        //Test 2:porn in title --false
        weblink = BookmarkManager.getInstance().createWebLink(2000,
                "Taming Tiger, porn 2",
                "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html",
                "http://www.javaworld.com");

        isKidFriendlyEligible = weblink.isKidFriendlyEligible();

        Assertions.assertFalse(isKidFriendlyEligible,"For porn in title - isKidFriendlyEligible() must return false");

        //Test 3:adult in host --false
        weblink = BookmarkManager.getInstance().createWebLink(2000,
                "Taming Tiger, Part 2",
                "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html",
                "http://www.adult.com");

        isKidFriendlyEligible = weblink.isKidFriendlyEligible();

        Assertions.assertFalse(isKidFriendlyEligible,"For adult in host - isKidFriendlyEligible() must return false");

        //Test 4: adult in url, but not in host part --true
        weblink = BookmarkManager.getInstance().createWebLink(2000,
                "Taming Tiger, Part 2",
                "http://www.javaworld.com/article/2072759/core-java/taming-adult--part-2.html",
                "http://www.javaworld.com");

        isKidFriendlyEligible = weblink.isKidFriendlyEligible();

        Assertions.assertTrue(isKidFriendlyEligible,"For adult in url, but not host part - isKidFriendlyEligible() must return true");

        //Test 5: adult in title only:
        weblink = BookmarkManager.getInstance().createWebLink(2000,
                "Adult Tiger, Part 2",
                "http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html",
                "http://www.javaworld.com");

        isKidFriendlyEligible = weblink.isKidFriendlyEligible();

        Assertions.assertTrue(isKidFriendlyEligible,"For adult in title - isKidFriendlyEligible() must return true");

    }
}