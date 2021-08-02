package com.fosung.xuanchuanlan.xuanchuanlan.main.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fosung.xuanchuanlan.R;

public class XCLNewsDynamicDetailActivity extends AppCompatActivity {

    private TextView contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xcl_activity_news_dynamic_detail);

        contentText = (TextView) findViewById(R.id.id_news_content);
        contentText.setText("榆垡镇结合大兴国际机场和护城河镇特定因素，采取多项措施，确保国庆70周年维稳工作取得实效。\n" +
                "\n" +
                "一是加强“倒退式”工作落实。为避免各项安保工作出现“头重脚轻”问题，我镇通过“一张检查单”来支撑整体工作。具体做法是：为各村、各部门制定《逐户违法问题排查整治检查单》，逐户记录建筑物类别、人员信息、问题类型、整改措施和时限等，形成基础台账上报专项办公室，汇总后，对违法问题分派职能部门进行压茬整改。\n" +
                "\n" +
                "二是严格“分片组”责任管理。统筹综治、安全、城管、规划等12个行业、领域职能部门，细化工作职责。同时，结合辖区实际，划分为5个片区，分别由综治、城管、工商、派出所、安全科牵头，各部门确保每片不少于1名业务骨干参加片区执法。并强化落实片区日排查、日碰头制度，及时对违法违规问题摸排治理，消除突出隐患。\n" +
                "\n" +
                "三是强化“快节奏”联动整改。依托吹哨执法机制，务求以最快速度、最高效率及时消除隐患，对突出问题责专人跟踪整改。同时，针对涉及黄赌毒、恐怖暴力、涉黑涉恶等违法犯罪案件，要求派出所做好快侦、快破等保障工作，并强化各类安全隐患、违法问题立行立改，最大化防止问题过夜，对疑难问题要确保在9月30日前整改完毕。\n" +
                "\n" +
                "四是完善“全方位”重点监管。针对邪教、肇事肇祸精神病等重点人的监管，我镇采取民警包村到户，村委责任到人，专职力量一");
    }
}
