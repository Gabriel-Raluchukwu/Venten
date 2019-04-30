package com.android.udacity.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMessageFormatter {
    private final static String dateRegexPattern = "(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)[0-9]{2}";
    private final static String timeRegexPattern = "\\b((1[0-2]|0?[1-9]):([0-5][0-9]) ([AaPp][Mm]))";
    private final static String widthRegexPattern = "[0-9]+w";
    private final static String lengthRegexPattern = "[0-9]+l";
    private final static String colorRegexPattern = "[0-9a-fA-F]{6}-[0-9a-fA-F]{6}";
    private final static String codedMessageRegexPattern = "(?<=AM|PM\\s).*?(?=\\sSZ)";

    public static String retrieveDate(String message){
        Pattern datePattern = Pattern.compile(dateRegexPattern);
        Matcher dateMatcher = datePattern.matcher(message);
        if(dateMatcher.find()){
            return dateMatcher.group(0);
        }
        return "";
    }

    public static String retrieveTime(String message){

        Pattern timePattern = Pattern.compile(timeRegexPattern);
        Matcher timeMatcher = timePattern.matcher(message);
        if(timeMatcher.find()){
            return timeMatcher.group(0);
        }
        return "";
    }

    public static String retrieveWidth(String message){
        Pattern widthPattern = Pattern.compile(widthRegexPattern);
        Matcher widthMatcher = widthPattern.matcher(message);
        if(widthMatcher.find()){
            return widthMatcher.group(0).replace("w","");
        }
        return null;
    }

    public static String retrieveLength(String message){
        Pattern lengthPattern = Pattern.compile(lengthRegexPattern);
        Matcher lengthMatcher = lengthPattern.matcher(message);
        if(lengthMatcher.find()){
            return lengthMatcher.group(0).replace("l","");
        }
        return "";
    }

    public static String[] retrieveColors(String message){
        Pattern colorPattern = Pattern.compile(colorRegexPattern);
        Matcher colorMatcher = colorPattern.matcher(message);
        if(colorMatcher.find()){
           String color = colorMatcher.group(0);
           String[] colors = color.split("-");
           return colors;
        }
        return new String[]{};
    }

    public static String retrievecodedMessage(String message){
        Pattern messagePattern = Pattern.compile(codedMessageRegexPattern);
        Matcher messageMatcher = messagePattern.matcher(message);
        if(messageMatcher.find()){
            return messageMatcher.group(0);
        }
        return "";
    }
}
