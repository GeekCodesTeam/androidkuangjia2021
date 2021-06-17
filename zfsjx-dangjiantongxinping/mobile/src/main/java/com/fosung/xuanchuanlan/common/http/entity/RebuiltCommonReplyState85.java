/*
 * *********************************************************
 *   author   yangqilin
 *   company  fosung
 *   email    yql19911010@gmail.com
 *   date     17-12-26 下午2:31
 * ********************************************************
 */

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
 * 整建制的返回报文：85返回不是json，这个时候我们需要手动解析，然后使用代理返回此报文
 */
public class RebuiltCommonReplyState85 extends BaseReplyBean85 {
    public boolean success;
    public boolean tranFlag;
    public String message;
    public String playFlag;
}
