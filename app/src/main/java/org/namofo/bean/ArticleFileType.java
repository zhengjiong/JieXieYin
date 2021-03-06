package org.namofo.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import java.io.Serializable;

/**
 * Entity mapped to table article_file_type.
 */
public class ArticleFileType implements Serializable{

    private Long id;
    private String file_name;

    public ArticleFileType() {
    }

    public ArticleFileType(Long id) {
        this.id = id;
    }

    public ArticleFileType(Long id, String file_name) {
        this.id = id;
        this.file_name = file_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

}
