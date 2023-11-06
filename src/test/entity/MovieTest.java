package test.entity;

import com.semanticSquare.thrillio.constants.MovieGenre;
import com.semanticSquare.thrillio.entities.Movie;
import com.semanticSquare.thrillio.managers.BookmarkManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    void isKidFriendlyEligible() {
        //Test 1
        Movie movie = BookmarkManager.getInstance().createMovie(3000,
                "Citizen Kane,",
                "",
                1941,
                new String[]{"Orson Welles", "Joseph Cotten"},
                new String[]{"Orson Welles"},
                MovieGenre.HORROR,
                8.5);
        boolean isKidFriendlyEligible = movie.isKidFriendlyEligible();
        assertFalse(isKidFriendlyEligible, "For horror genre - isKidFriendlyEligible should return false");
        //Test 2
        movie = BookmarkManager.getInstance().createMovie(3000,
                "Citizen Kane,",
                "",
                1941,
                new String[]{"Orson Welles", "Joseph Cotten"},
                new String[]{"Orson Welles"},
                MovieGenre.THRILLERS,
                8.5);
        isKidFriendlyEligible = movie.isKidFriendlyEligible();
        assertFalse(isKidFriendlyEligible, "For Thriller genre - isKidFriendlyEligible should return false");
    }
}