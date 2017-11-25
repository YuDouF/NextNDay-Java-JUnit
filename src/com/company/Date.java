package com.company;

import com.company.OutputFormat;

import java.util.Vector;

public class Date {
    private long m_nYear;
    private int m_nMonth;
    private long m_nDay;

    private OutputFormat m_fOutputFormat;

    public Date() {
        m_nYear = 1;
        m_nMonth = 1;
        m_nDay = 1;
        m_fOutputFormat = new OutputFormat("yyyy-mm-dd");
    }

    public Date(long year, int month) {
        m_nYear = year;
        m_nMonth = month;
        m_nDay = 1;
        m_fOutputFormat = new OutputFormat("yyyy-mm-dd");
    }

    public Date(long year, int month, long  day) {
        m_nYear = year;
        m_nMonth = month;
        m_nDay = day;
        m_fOutputFormat = new OutputFormat("yyyy-mm-dd");
    }

    public Date getNextNDay(long dayNum) {
        Date maxDate, minDate;
        if (dayNum == 0) {
            return this;
        }
	    long year = Math.abs(dayNum) / 365;
        ++year;
        if (dayNum > 0) {
            if (m_nMonth == 2 && m_nDay == 29) {	//避免2月29日进入程序逻辑
                m_nDay = 1;
                m_nMonth = 3;
                dayNum--;
            }
            year += m_nYear;
            if (year >= 0) {	//补偿0年
                ++year;
            }
            maxDate = new Date(year, m_nMonth, m_nDay);
            minDate = this;
        }
        else{
            if (m_nMonth == 2 && m_nDay == 29) {	//避免2月29日进入程序逻辑
                m_nDay = 28;
                dayNum++;
            }
            year = m_nYear - year;
            if (year <= 0) {	//补偿0年
                --year;
            }
            maxDate = this;
            minDate = new Date(year, m_nMonth, m_nDay);

            dayNum = DateTool.getLengthBetweenDate(maxDate, minDate) + dayNum;	//将前N天转化为小日期的后M天
        }

        return DateTool.LookUpDate(minDate, maxDate, dayNum);
    }

    //    public bool operator > (Date date);
    public Boolean isBigger(Date date) {
        if (m_nYear > date.m_nYear) {
            return true;
        }
        else if (m_nYear == date.m_nYear) {
            if (m_nMonth > date.m_nMonth) {
                return true;
            }
            else if (m_nMonth == date.m_nMonth) {
                if (m_nDay >= date.m_nDay) {
                    return true;
                }
            }
        }

        return false;
    }

    public Boolean isSmaller(Date date) {
        return !isBigger(date);
    }
//    public bool operator < (const Date & date) const;

    public void loadOutputFormat(OutputFormat format) {
        m_fOutputFormat = format;
    }

    public String toString() {
        String str = "";
        Vector<String> splits = m_fOutputFormat.getSplits();
        str = str + m_nYear + splits.get(0) + m_nMonth + splits.get(1) + m_nDay;
        return str;
    }

    public long getYear() {
        return m_nYear;
    }

    public void setYear(long m_nYear) {
        this.m_nYear = m_nYear;
    }

    public int getMonth() {
        return m_nMonth;
    }

    public void setMonth(int m_nMonth) {
        this.m_nMonth = m_nMonth;
    }

    public long getDay() {
        return m_nDay;
    }

    public void setDay(long m_nDay) {
        this.m_nDay = m_nDay;
    }

    public OutputFormat getOutputFormat() {
        return m_fOutputFormat;
    }

    public void setOutputFormat(OutputFormat m_fOutputFormat) {
        this.m_fOutputFormat = m_fOutputFormat;
    }

}
