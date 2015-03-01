package org.namofo.util;

import org.textmining.text.extraction.WordExtractor;

import java.io.InputStream;

/**
 * Doc文档Utils
 * @Author: zhengjiong
 * Date: 2014-07-13
 * Time: 16:35
 */
public class DocUtils {
    private DocUtils(){}

    private static WordExtractor wordExtractor;

    private static WordExtractor getExtractor(){
        if (wordExtractor == null){
            synchronized (WordExtractor.class){
                if (wordExtractor == null){
                    wordExtractor = new WordExtractor();
                }
            }
        }
        return wordExtractor;
    }

    /**
     * 獲取Doc文檔的內容
     * @param in
     * @return
     * @throws Exception
     */
    public static String getDocContent(InputStream in) throws Exception {
        return getExtractor().extractText(in);
    }
}
