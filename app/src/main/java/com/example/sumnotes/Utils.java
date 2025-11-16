package com.example.sumnotes;
import java.util.regex.Pattern;

public final class Utils {
    private Utils() {}
    public static final Pattern sumPattern = Pattern.compile("(?<!\\()\\b\\d+\\b(?![^()]*\\))");
}
