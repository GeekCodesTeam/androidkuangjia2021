package com.example.slbappcomm.fragmentsgeek.demo5.domain;

import java.io.Serializable;
import java.util.List;

public class MkShopCategories implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<MkShopCategoryItem> category2_list;

    public List<MkShopCategoryItem> getCategory2_list() {
        return category2_list;
    }

    public void setCategory2_list(List<MkShopCategoryItem> category2_list) {
        this.category2_list = category2_list;
    }
}
