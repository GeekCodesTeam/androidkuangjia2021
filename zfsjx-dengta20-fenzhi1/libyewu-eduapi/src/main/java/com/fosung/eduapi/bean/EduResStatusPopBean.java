package com.fosung.eduapi.bean;

import java.io.Serializable;

/**
 * @author fosung
 */
public class EduResStatusPopBean implements Serializable {

    public String name;
    public String code;

    public EduResStatusPopBean(String name, String code) {
        this.name = name;
        this.code = code;
    }
}

