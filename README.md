# Venten Assessment

## Venten Android Developer Assessment

### Description
This application listens for SMS messages sent by '**ven10**' and uses the Message to construct the User Interface accordingly.
The message must follow a format.
Format of message is: DT:11/18/2016:7:37 AM TXG C55/DRAINQ, WHITE/GIN~ SZ 200w200l / CD00A0-FFFFFF
* Messages will always have 3 lines
* First line will always start with DT: followed by a date and time in am/pm format
* Second line is a coded message and as such can have any combination of text
* Third line always starts with SZ followed by a width and length dimensions then a 2 sets of 6 digit hex color codes

The following information is extracted from the SMS message
1. Date
2. Time
3. Coded message
4. Dimension w
5. Dimension l
6. Color code 1
7. Color code 2

The information above is displayed in the User Interface
User cab press the toggle botton in the menu to toggle between color 1 and color 2
