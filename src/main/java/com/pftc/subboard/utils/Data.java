package com.pftc.subboard.utils;

public class Data<T> {
    protected T myData;

    /**
     * Check if {@code myList} is null or empty.
     * @throws IllegalArgumentException when {@code myList} is null or empty.
     */
    public Data(T value) throws IllegalArgumentException {
        myData = value;

        if(checkNull() || checkStringEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Check if {@code myList} is null.
     * @return boolean : true if {@code myList} is null, false otherwise.
     */
    private boolean checkNull() {
        return myData == null;
    }

    /**
     * Check if {@code myList} is a String, if so check if it's empty.
     * @return boolean : true if {@code myList} is a string and empty, false otherwise.
     */
    private boolean checkStringEmpty() {
        
        if(myData instanceof String) {
            return ((String) myData).isEmpty();
        }
        return false;
    }
}
