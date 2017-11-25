package com.company;

public class NumberCheck {
    public static Boolean checkNumberLegality(String str) {
        for (int i = 0; i < str.length(); i++) {
            char temp = str.charAt(i);
            if (i == 0 && temp == '-') {
                if (str.length() == 1 || str.charAt(1) == '0') {
                    //cout << str << " Format error!\n";
                    return false;
                }
            }
            else if(i == 0 && temp == '0' && str.length() != 1){
                //cout << str << " Format error!\n";
                return false;
            }
            else if (temp < '0' || temp > '9') {
                //cout << str << " Format error!\n";
                return false;
            }
        }
        return true;
    }

}
