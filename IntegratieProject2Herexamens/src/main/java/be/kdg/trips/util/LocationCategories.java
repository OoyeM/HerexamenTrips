package be.kdg.trips.util;

/**
 * Created by Matthias on 12/08/2015.
 */
public enum LocationCategories {
    START {
        @Override
        public String getCat() {
            return "START";
        }
    },

    STOP {
        @Override
        public String getCat() {
            return "STOP";
        }
    },
    BETWEEN {
        @Override
        public String getCat() {
            return "BETWEEN";
        }
    };

    public abstract String getCat();

}

