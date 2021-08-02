package com.example.gsydemo.updateview;


import com.example.gsydemo.bt.Member;

/**
 * Created by mqh on 2018/8/4.
 */

public class MemberHrView {
    private HrCardView hrCardView;
    private Member member;

    public HrCardView getHrCardView() {
        return hrCardView;
    }

    public void setHrCardView(HrCardView hrCardView) {
        this.hrCardView = hrCardView;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public MemberHrView(HrCardView hrCardView, Member member) {
        this.member = member;
        this.hrCardView = hrCardView;
    }
}
