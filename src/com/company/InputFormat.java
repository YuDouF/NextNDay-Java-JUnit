package com.company;

import com.company.Format;
import java.util.Vector;

public class InputFormat extends Format {
    public InputFormat() {
        super("yyyy,mm,dd,n");
    }

    public InputFormat(String type) {
        super(type);
        int temp1 = (int)type.indexOf("yyyy", 0);
        if (temp1 == -1) {
//            cout << type << " error format\n";
            m_sFormatType = "";
            return;
        }
        int temp2 = (int)type.indexOf("mm", temp1 + 4);
        if (temp2 == -1) {
//            cout << type << " error format\n";
            m_sFormatType = "";
            return;
        }
        int temp3 = (int)type.indexOf("dd", temp2 + 2);
        if (temp3 == -1) {
//            cout << type << " error format\n";
            m_sFormatType = "";
            return;
        }
        int temp4 = (int)type.indexOf("n", temp3 + 2);
        if (temp4 == -1) {
//            cout << type << " error format\n";
            m_sFormatType = "";
            return;
        }
        int temp5 = (int)type.indexOf("exp", temp4 + 1);
        if (temp5 == -1) {
//            cout << type << " error format\n";
            m_sFormatType = "";
            return;
        }

        String splite = m_sFormatType.substring(temp1 + 4, temp2);
        m_vSplits.add(splite);
        splite = m_sFormatType.substring(temp2 + 2, temp3);
        m_vSplits.add(splite);
        splite = m_sFormatType.substring(temp3 + 2, temp4);
        m_vSplits.add(splite);
        splite = m_sFormatType.substring(temp4 + 1, temp5);
        m_vSplits.add(splite);
    }

    public InputFormat(InputFormat inputFormat) {
        super(inputFormat);
    }

    @Override
    public Vector match(String str) {
        Vector<String> words = new Vector<String>();
        for (int i = 0; i < m_vSplits.size(); ++i){
            String splite = m_vSplits.get(i);
            int index = (int)str.indexOf(splite);
            if (index == -1) {
                words.clear();
                return words;
            }
            String temp = str.substring(0, index);
            words.add(temp);
            str = str.substring(index + splite.length());
        }

        String temp = str.substring(0);
        words.add(temp);

        return words;
    }
}
