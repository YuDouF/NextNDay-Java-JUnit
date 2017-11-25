package com.company;
import com.company.Format;

import java.util.Vector;

public class OutputFormat extends Format{
    public OutputFormat() {
        super("yyyy-mm-dd");
    }

    public OutputFormat(String type) {
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
        String splite = m_sFormatType.substring(temp1 + 4, temp1 + 4 + temp2 - temp1 - 4);
        m_vSplits.add(splite);
        splite = m_sFormatType.substring(temp2 + 2, temp2 + 2 + temp3 - temp2 - 2);
        m_vSplits.add(splite);
    }

    public OutputFormat(OutputFormat inputFormat) {
        super(inputFormat);
    }

    @Override
    public Vector<String> match(String str) {
        Vector<String> words = new Vector<String>();
        for (int i = 0; i < m_vSplits.size(); ++i) {
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
