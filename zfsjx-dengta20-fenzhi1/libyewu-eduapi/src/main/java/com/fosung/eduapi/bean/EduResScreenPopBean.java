package com.fosung.eduapi.bean;

import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;

import java.io.Serializable;
import java.util.List;

public class EduResScreenPopBean implements Serializable {
    public String createOrgId = "";//            报送单位
    public String startTime;//              初始提报日期开始
    public String endTime; //                初始提报日期结束
    public String startAuditTime; //         提报本级日期开始
    public String endAuditTime; //           提报本级日期结束
    public String status  = ""; //                 资源状态
    public String terminalId  = ""; //             报送终端id
}

