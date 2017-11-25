package com.company;

public class DateTool {
    public static final String errorInfo = "format error";
    public DateTool() {

    }

    public static Boolean isLeapYear(long year) {
        if (year < 0) {
            year++;
        }
//        if(0 < year && year <= 1582 && year % 4 == 0){
//            return true;
//        }else
        if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) {
            return true;
        }

        return false;
    }

    public static int getMonthDayLength(Date date) {
        int month = date.getMonth();
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
//                if(date.getYear() == 1582){
//                    return 21;
//                 }
            case 12:
                return 31;
            case 2:
                return isLeapYear(date.getYear()) ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                break;
        }

        return 0;
    }

    public static Boolean checkLegality(Date date) {
        long year = date.getYear();
        if (year == 0) {
            //cout << "checkLegality: Year illegal" << endl;
            return false;
        }
        int month = date.getMonth();
        if (month <= 0 || month > 12) {
            //cout << "checkLegality: Month illegal" << endl;
            return false;
        }
	    long day = date.getDay();
        if (day <= 0 || day > getMonthDayLength(date)) {
            //cout << "checkLegality: Day illegal" << endl;
            return false;
        }
//        if(year == 1582 && month == 10 && day >= 5 && day <= 14){
//            //cout << "checkLegality: Day illegal" << endl;
//            return false;
//        }
        return true;
    }

    //static void standardDate(Date *date);
    public static long getLengthBetweenDate(Date first, Date second) {
        Date bigger, smaller;
        if (first.isBigger(second)) {
            bigger = first;
            smaller = second;
        }
        else {
            bigger = second;
            smaller = first;
        }

        long numberYearCap = 0;
        long leapToSmaller4, leapToSmaller100, leapToSmaller400, leapToBigger4, leapToBigger100, leapToBigger400;
        if (bigger.getYear() != smaller.getYear()) {
		long leapYearCount;
		long notLeapYearCount;
            if (bigger.getYear() > 0 && smaller.getYear() > 0) {	//两者年份都在公元后模4-模100+模400
                leapToSmaller4 = (Math.abs(smaller.getYear())) / 4;	//计算包括本身的闰年数
                leapToSmaller100 = (Math.abs(smaller.getYear())) / 100;
                leapToSmaller400 = (Math.abs(smaller.getYear())) / 400;

                leapToBigger4 = (Math.abs(bigger.getYear()) - 1) / 4;	//计算不包括本身的闰年数
                leapToBigger100 = (Math.abs(bigger.getYear()) - 1) / 100;
                leapToBigger400 = (Math.abs(bigger.getYear()) - 1) / 400;

                leapYearCount = (leapToBigger4 - leapToBigger100 + leapToBigger400) - (leapToSmaller4 - leapToSmaller100 + leapToSmaller400);
                notLeapYearCount = bigger.getYear() - smaller.getYear() - leapYearCount;
            }
            else if(bigger.getYear() > 0 && smaller.getYear() < 0){	//大的一方年份都在公元后，小的一方在公元前
                leapToSmaller4 = (Math.abs(smaller.getYear() + 1) - 1) / 4;	//计算不包括本身的闰年数
                leapToSmaller100 = (Math.abs(smaller.getYear() + 1) - 1) / 100;
                leapToSmaller400 = (Math.abs(smaller.getYear() + 1) - 1) / 400;

                leapToBigger4 = (Math.abs(bigger.getYear()) - 1) / 4;	//计算不包括本身的闰年数
                leapToBigger100 = (Math.abs(bigger.getYear()) - 1) / 100;
                leapToBigger400 = (Math.abs(bigger.getYear()) - 1) / 400;

                leapYearCount = (leapToBigger4 - leapToBigger100 + leapToBigger400) + (leapToSmaller4 - leapToSmaller100 + leapToSmaller400);
                if (smaller.getYear() != -1) {	//补偿-1为闰年
                    leapYearCount++;
                }
                notLeapYearCount = bigger.getYear() - smaller.getYear() - leapYearCount - 1;	//减去0年
            }
            else {	//两者年份都在公元前
                leapToSmaller4 = (Math.abs(smaller.getYear() + 1) - 1) / 4;	//计算不包括本身的闰年数
                leapToSmaller100 = (Math.abs(smaller.getYear() + 1) - 1) / 100;
                leapToSmaller400 = (Math.abs(smaller.getYear() + 1) - 1) / 400;
                leapToSmaller4++;	//补偿-1闰年

                leapToBigger4 = (Math.abs(bigger.getYear() + 1)) / 4;	//计算包括本身的闰年数
                leapToBigger100 = (Math.abs(bigger.getYear() + 1)) / 100;
                leapToBigger400 = (Math.abs(bigger.getYear() + 1)) / 400;
                leapToBigger4++;	//补偿-1闰年

                leapYearCount = (leapToSmaller4 - leapToSmaller100 + leapToSmaller400) - (leapToBigger4 - leapToBigger100 + leapToBigger400);
                notLeapYearCount = bigger.getYear() - smaller.getYear() - leapYearCount;
            }
            numberYearCap = 365 * notLeapYearCount + 366 * leapYearCount;
            if (isLeapYear(smaller.getYear())) {	//补偿小年份包括2月29日
                if (smaller.getMonth() < 2 || (smaller.getMonth() == 2 && smaller.getDay() != 29)) {
                    numberYearCap++;
                }
            }
            if (isLeapYear(bigger.getYear())) {	//补偿大年份包括2月29日
                if (smaller.getMonth() > 2 || (smaller.getMonth() == 2 && smaller.getDay() == 29)) {
                    numberYearCap++;
                }
            }
        }

        int numberMonthCap = 0;
        if (bigger.getMonth() >= smaller.getMonth()) {
            for (int i = smaller.getMonth(); i < bigger.getMonth(); ++i) {
                numberMonthCap += getMonthDayLength(new Date(bigger.getYear(), i));
            }
        }else {
            for (int i = bigger.getMonth(); i < smaller.getMonth(); ++i) {
                numberMonthCap -= getMonthDayLength(new Date(bigger.getYear(), i));
            }
        }

	    long numberDayCap = bigger.getDay() - smaller.getDay();

        long duration = numberYearCap + numberMonthCap + numberDayCap;
//        if(smaller.isSmaller(new Date(1582, 10, 5)) && bigger.isBigger(new Date(1582, 10, 15))){
//            duration -= 10;
//        }

        return duration;

    }

    public static Date getMedianDate(Date begin, Date end) {
        long year = (end.getYear() + begin.getYear()) / 2;	//年份取半
        if (year == 0 || (begin.getYear() < 0 && end.getYear() < 0 && begin.getYear() != end.getYear() && begin.getYear() != end.getYear() - 2)) {	//排除0年，取半向下取整
            year--;
        }
        int month = begin.getMonth();
        long day = begin.getDay();

        //每次在年、月、日中只调整一项
        if (begin.getYear() < end.getYear()) {	//调整年份
            if (year == begin.getYear()) {	//说明begin的年份比end的年份少1
                int temp = (end.getMonth() + 12 - begin.getMonth()) / 2;
                if (temp == 0) {	//说明begin为12月，end为1月
                    day += (getMonthDayLength(begin) - begin.getDay() + end.getDay()) / 2;
                    if (day > 31) {
                        day -= 31;
                        temp = 1;
                    }
                }
                month += temp;
                if (month > 12) {
                    month -= 12;
                    ++year;
                    if (year == 0) {
                        ++year;
                    }
                }
            }
        }
        else if (begin.getMonth() < end.getMonth()) {	//年份相同，调整月份，可能造成月份与天数不匹配
            month = (end.getMonth() + begin.getMonth()) / 2;	//月份取半
            if (month == begin.getMonth()) {	//说明begin的月份比end月份少1
                int length = getMonthDayLength(new Date(begin.getYear(), begin.getMonth()));
                day += (end.getDay() + length - begin.getDay()) / 2;
                if (day > length) {
                    day -= length;
                    ++month;
                }
            }
            int length = getMonthDayLength(new Date(year, month));
            if (day > length) {	//调整天数与月份相匹配
                day = length;
            }
        }
        else {	//年份月份都相同，调整天数
            day = (end.getDay() + begin.getDay()) / 2;
        }

        return new Date(year, month, day);
    }

    public static Date LookUpDate(Date begin, Date end, long dayNum) {
        Date time = begin;
        while (dayNum != 0) {
            Date temp = getMedianDate(begin, end);
		long length = getLengthBetweenDate(temp, begin);
            if (length == dayNum) {
                time = temp;
                dayNum = 0;
            }
            else if (length < dayNum) {
                begin = temp;
                dayNum -= length;
            }
            else if (length > dayNum) {
                end = temp;
            }
        }

        return time;
    }

    public static String getNextNDay(long year, int month, long day, long numberDay) {
        Date now = new Date(year, month, day);
        if (!DateTool.checkLegality(now)) {
            return DateTool.errorInfo;
        }
        System.out.println("Today is :" + now.toString());

        long dayNum = numberDay;
        Date result = now.getNextNDay(dayNum);
        result.loadOutputFormat(new OutputFormat("yyyy-mm-dd"));
        System.out.println("Next " + dayNum + " day is :" + result);
        return result.toString();
    }
}
