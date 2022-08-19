package com.umldesigner.infrastructure.uml.error;

public class ErrorTags {
    // Noninstantiable class
    private ErrorTags(){
        // Suppress default constructor for noninsatiability
        throw new AssertionError();
    }
    /**
     * error on the application side of things
     */
    public static final String APP_ERROR = "App Error";
    
    /**
     * error for the api side of things
     */
    public static final String API_ERROR = "Api Error";
}
