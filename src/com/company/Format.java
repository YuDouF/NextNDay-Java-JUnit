package com.company;

import java.util.Vector;

public abstract class Format {
    protected String m_sFormatType;
    protected Vector<String> m_vSplits;

    public Format() {
        m_sFormatType = "";
        m_vSplits = new Vector<String>();
    }

    public Format(String type) {
        m_sFormatType = type;
        m_vSplits = new Vector<String>();
    }

    public Format(Format format) {
        m_sFormatType = format.getFormatType();
        m_vSplits = format.getSplits();
    }

    public abstract Vector<String> match(String str);


    public void display() {
        if (m_sFormatType == "") {
            return;
        }

//        cout << "Format: " << m_sFormatType
//                << "\nSplites:\n";
        for (int i = 0; i < m_vSplits.size(); ++i) {
            System.out.println(m_vSplits.get(i));
        }
    }

    public String getFormatType() {
        return m_sFormatType;
    }

    public void setFormatType(String m_sFormatType) {
        this.m_sFormatType = m_sFormatType;
    }

    public Vector getSplits() {
        return m_vSplits;
    }

    public void setSplits(Vector<String> m_vSplits) {
        this.m_vSplits = m_vSplits;
    }
}
