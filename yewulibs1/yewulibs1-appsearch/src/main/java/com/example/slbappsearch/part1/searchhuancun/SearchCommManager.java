package com.example.slbappsearch.part1.searchhuancun;

import com.example.slbappsearch.part1.SearchBean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车缓存的工具类
 */

public class SearchCommManager {

    private static SearchCommManager sShoppingCartHistoryManager;

    private SearchCommManager() {

    }

    public static SearchCommManager getInstance() {
        if (sShoppingCartHistoryManager == null) {
            synchronized (SearchCommManager.class) {
                if (sShoppingCartHistoryManager == null) {
                    sShoppingCartHistoryManager = new SearchCommManager();
                }
            }
        }
        return sShoppingCartHistoryManager;
    }

    // 写入缓存HashMap
    public void Add(String txt_id, ArrayList<SearchBean> mGoodsList) {
        //写入缓存
        LinkedHashMap<String, SearchBean> hashMap = new LinkedHashMap<>();
        for (SearchBean bean : mGoodsList) {
            String goodsId = bean.getText_id();
            hashMap.put(goodsId + "", bean);
        }
        SearchCommBaseManager.getInstance().addHashMap(txt_id, hashMap);
    }

    /**
     * 读取缓存HashMap
     *
     * @param txt_id
     */
    public ArrayList<SearchBean> get(String txt_id) {
        ArrayList<SearchBean> listBean = new ArrayList<>();
        LinkedHashMap<String, SearchBean> hashMap = (LinkedHashMap<String, SearchBean>) SearchCommBaseManager.getInstance().getHashMap(txt_id);
        if (hashMap != null) {
            for (Map.Entry<String, SearchBean> entry : hashMap.entrySet()) {
                SearchBean bean = entry.getValue();
                if (bean != null) {
                    listBean.add(bean);
                }
            }
        }
        return listBean;
    }

    //删除缓存中的TXT 文件bufen
    public void del(String txt_id) {
        SearchCommBaseManager.getInstance().delete(txt_id);
    }

}
