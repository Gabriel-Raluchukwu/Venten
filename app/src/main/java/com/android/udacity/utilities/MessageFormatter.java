package com.android.udacity.utilities;
import java.lang.String;

public class MessageFormatter {
    private static final String dt = "DT:";
    private static final String sz = "SZ";
    private String stringToFormat;
    String color1;
    String color2;
    String date;
    String time;
    String width;
    String length;
    String message;

    public String getDate(){ return date; }

    public String getTime(){ return time; }

    public String getWidth(){ return width; }

    public String getLength(){ return length; }

    public String getMessage(){ return message; }

    public String getColor1(){ return color1; }

    public String getColor2(){ return color2; }

    public void formatString(String string){
        stringToFormat = string;

        //Extract Date
        stringToFormat = stringToFormat.trim();
        String[] split = stringToFormat.split(":");
        stringToFormat = stringToFormat.replace(dt, "");
        String tempDate = split[1];
        stringToFormat = stringToFormat.replace(split[1], "");

        //Extract
        String colorAndDimensions = stringToFormat.substring(stringToFormat.lastIndexOf(sz));
        stringToFormat = stringToFormat.replace(colorAndDimensions, "").toUpperCase();
        colorAndDimensions = colorAndDimensions.replace(sz,"");
        String[] data = colorAndDimensions.split("/");

        //Extract Length and Width Dimensions
        String dimen = data[0].trim().toLowerCase();
        String Length = dimen.substring(dimen.indexOf('w') + 1);
        String Width = dimen.replace(Length,"");

        String tempWidth = Width.replace("w", "");
        String tempLength = Length.replace("l","");


        //Extract Colors
        String color = data[1].trim();
        String[] colors = color.split("-");
        String tempColor1 = colors[0];
        String tempColor2 = colors[1];

        //Extract Coded message
        String tempMessage;
        if(stringToFormat.contains("AM")){
            int index = stringToFormat.indexOf("AM") + 2;
            tempMessage = stringToFormat.substring(index);
        }
        else{
            int index = stringToFormat.indexOf("PM") + 2;
            tempMessage = stringToFormat.substring(index);
        }
        message = tempMessage;

        stringToFormat = stringToFormat.replace(tempMessage,"");
        String tempTime = stringToFormat.substring(1);

        //Validation using Regular Expressions
        //Every data extracted is matched against their appropiate regex before saving
        //This is to ensure that extracted information is in the right format that the rest of the application will be able to use.
        if(tempDate.matches("(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)[0-9]{2}")){
            date = tempDate;
        }
        if(tempTime.matches("\\b((1[0-2]|0?[1-9]):([0-5][0-9]) ([AaPp][Mm]))")){
            time = tempTime;
        }
        if(tempColor1.matches("([0-9a-fA-F]{3}|[0-9a-fA-F]{6}|[0-9a-fA-F]{8})")){
            color1 = tempColor1;
        }
        if(tempColor2.matches("([0-9a-fA-F]{3}|[0-9a-fA-F]{6}|[0-9a-fA-F]{8})")){
            color2 = tempColor2;
        }
        if(tempWidth.matches("^[0-9]+$")){
            width = tempWidth;
        }
        if(tempLength.matches("^[0-9]+$")){
            length = tempLength;
        }
    }
}
