package org.ng.undp.vdms.constants;

/**
 * Created by macbook on 7/1/17.
 *
 *
 * 1= Excellent, 2=very good. 3=Good, 4=fair, 5=Unsatisfactory
 */
public enum RatingAttributes {


   __SELECT__(0), EXCELLENT(1) , VERYGOOD(2), GOOD(3),FAIR(4),UNSATISFACTORY(5);  // Named constants

    public final int rating;      // Private variable

    RatingAttributes(int rating) {     // Constructor
        this.rating = rating;
    }

    int getRating() {              // Getter
        return rating;
    }











}
