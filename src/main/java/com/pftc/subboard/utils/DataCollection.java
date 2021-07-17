package com.pftc.subboard.utils;

import java.util.Collection;

public class DataCollection<T extends Collection<?>> extends Data<T>  {
    
    /**
     * Check if {@code myData} is null or empty.
     * @throws IllegalArgumentException when {@code myData} is null or empty.
     */
    public DataCollection(T value) throws IllegalArgumentException {
        super(value);

        if(checkEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Check if {@code myList} is empty.
     * @return boolean : true if {@code myList} is empty, false otherwise.
     */
    private boolean checkEmpty() {
        return myData.isEmpty();
    }
}
