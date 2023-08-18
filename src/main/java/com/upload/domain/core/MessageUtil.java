package com.upload.domain.core;

public class MessageUtil {

    public static final String FILE_IS_EMTY = "The file is empty";
    public static final String INCORRECT_FILE_FORMAT = "Incorrect file format";
    public static final String ERROR_CONVERTING_FILE = "Error converting file";

    private MessageUtil() {
        throw new IllegalStateException("Messages class");
    }
}
