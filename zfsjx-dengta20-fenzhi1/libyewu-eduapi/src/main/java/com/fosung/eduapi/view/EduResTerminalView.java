package com.fosung.eduapi.view;

import com.fosung.eduapi.bean.EduResExecutorReplyBean;
import com.geek.libmvp.IView;

public interface EduResTerminalView extends IView {

    void OnEduResTerminalSuccess(EduResExecutorReplyBean bean);
    void OnEduResTerminalFail(String msg);

}
