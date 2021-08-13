/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-10-9 下午1:29
 * ********************************************************
 */

package com.fosung.xuanchuanlan.common.http.entity;

/**
 * 85返回实体有时只有一个 “true”或者“false”，不是json，这个时候我们需要手动解析，然后使用代理返回此报文
 */
public class CommonReplyState85 extends BaseReplyBean85 {
    public boolean success;
}
