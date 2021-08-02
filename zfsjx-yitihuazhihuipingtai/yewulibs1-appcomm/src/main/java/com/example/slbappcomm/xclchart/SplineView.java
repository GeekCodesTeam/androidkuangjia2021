package com.example.slbappcomm.xclchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import com.example.slbappcomm.videoplay.quxiantu.bean.SpView4Bean;
import com.example.slbappcomm.videoplay.quxiantu.bean.SpView4Bean21;
import com.example.slbappcomm.xclchart.chart.CustomLineData;
import com.example.slbappcomm.xclchart.chart.PointD;
import com.example.slbappcomm.xclchart.chart.SplineChart;
import com.example.slbappcomm.xclchart.chart.SplineData;
import com.example.slbappcomm.xclchart.common.DensityUtil;
import com.example.slbappcomm.xclchart.common.IFormatterTextCallBack;
import com.example.slbappcomm.xclchart.renderer.XEnum;
import com.example.slbappcomm.xclchart.renderer.plot.PlotGrid;
import com.example.slbappcomm.xclchart.view.ChartView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Copyright 2014  XCL-Charts
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author XiongChuanLiang<br                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               />(xcl_168@aliyun.com)
 * @Project XCL-Charts
 * @Description Android图表基类库
 * @ClassName SplineChart01View
 * @Description 曲线图 的例子
 */
public class SplineView extends ChartView {

    private String TAG = "SplineChart04View";
    private SplineChart chart = new SplineChart();
    //分类轴标签集合
    private LinkedList<String> labels = new LinkedList<String>();
    private LinkedList<SplineData> chartData = new LinkedList<SplineData>();
    Paint pToolTip = new Paint(Paint.ANTI_ALIAS_FLAG);

    private List<CustomLineData> mCustomLineDataset = new LinkedList<CustomLineData>();

    public SplineView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView();
    }

    public SplineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SplineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }
    //Demo中bar chart所使用的默认偏移值。
    //偏移出来的空间用于显示tick,axistitle....
    public int[] getBarLnDefaultSpadding()
    {
        int [] ltrb = new int[4];
        ltrb[0] = DensityUtil.dip2px(getContext(), 40); //left
        ltrb[1] = DensityUtil.dip2px(getContext(), 60); //top
        ltrb[2] = DensityUtil.dip2px(getContext(), 20); //right
        ltrb[3] = DensityUtil.dip2px(getContext(), 40); //bottom
        return ltrb;
    }

    public int[] getPieDefaultSpadding()
    {
        int [] ltrb = new int[4];
        ltrb[0] = DensityUtil.dip2px(getContext(), 20); //left
        ltrb[1] = DensityUtil.dip2px(getContext(), 65); //top
        ltrb[2] = DensityUtil.dip2px(getContext(), 20); //right
        ltrb[3] = DensityUtil.dip2px(getContext(), 20); //bottom
        return ltrb;
    }

    private void initView() {
        chartLabels();
        chartDataSet();
        chartDesireLines();
        chartRender();

        //綁定手势滑动事件
        this.bindTouch(this, chart);
    }

    private SpView4Bean spView4Bean;

    public SpView4Bean getSpView4Bean() {
        return spView4Bean;
    }

    public void setSpView4Bean(SpView4Bean spView4Bean) {
        this.spView4Bean = spView4Bean;
        initView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        chart.setChartRange(w, h);
    }

    private void chartRender() {
        try {

            //设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
            int[] ltrb = getBarLnDefaultSpadding();
            chart.setPadding(ltrb[0] + DensityUtil.dip2px(this.getContext(), 10), ltrb[1],
                    ltrb[2] + DensityUtil.dip2px(this.getContext(), 20), ltrb[3]);

            //标题
            if (spView4Bean != null) {
                chart.setTitle(spView4Bean.getTitle1());
                chart.addSubtitle(spView4Bean.getTitle2());
                chart.getAxisTitle().setLeftTitle(spView4Bean.getTitle3());
            } else {
                chart.setTitle("");
                chart.addSubtitle("");
                chart.getAxisTitle().setLeftTitle("");
            }
            chart.getAxisTitle().getLeftTitlePaint().setColor(Color.BLACK);

            //显示边框
            chart.showRoundBorder();

            //数据源
            chart.setCategories(labels);
            chart.setDataSource(chartData);
            chart.setCustomLines(mCustomLineDataset);

            //坐标系
            if (spView4Bean != null) {
                //数据轴最大值
                chart.getDataAxis().setAxisMax(spView4Bean.getTitle31());
                //chart.getDataAxis().setAxisMin(0);
                //数据轴刻度间隔
                chart.getDataAxis().setAxisSteps(5 * (spView4Bean.getTitle31() / 20));

                //标签轴最大值
                chart.setCategoryAxisMax(spView4Bean.getTitle21());
                //标签轴最小值
                chart.setCategoryAxisMin(0);
            } else {
                //数据轴最大值
                chart.getDataAxis().setAxisMax(20);
                //chart.getDataAxis().setAxisMin(0);
                //数据轴刻度间隔
                chart.getDataAxis().setAxisSteps(5);

                //标签轴最大值
                chart.setCategoryAxisMax(6);
                //标签轴最小值
                chart.setCategoryAxisMin(0);
            }


            //背景网格
            PlotGrid plot = chart.getPlotGrid();
            plot.hideHorizontalLines();
            plot.hideVerticalLines();
//            int color_yun = Color.rgb(127, 204, 204);
            int color_yun = Color.rgb(0, 0, 0);
            chart.getDataAxis().getAxisPaint().setColor(color_yun);
            chart.getCategoryAxis().getAxisPaint().setColor(color_yun);

            chart.getDataAxis().getTickMarksPaint().setColor(color_yun);
            chart.getCategoryAxis().getTickMarksPaint().setColor(color_yun);

            //定义数据轴标签显示格式
            chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack() {

                @Override
                public String textFormatter(String value) {
                    // TODO Auto-generated method stub
                    Double tmp = Double.parseDouble(value);
                    DecimalFormat df = new DecimalFormat("#0");
                    String label = df.format(tmp);
                    return (label);
                }
            });

            //不使用精确计算，忽略Java计算误差,提高性能
            chart.disableHighPrecision();
            chart.disableScale();// 设置缩放

            chart.disablePanMode();
            chart.hideBorder();
            chart.getPlotLegend().hide();

            //chart.getCategoryAxis().setLabelLineFeed(XEnum.LabelLineFeed.ODD_EVEN);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
    }

    private void chartDataSet() {

        //线2的数据集
        List<PointD> linePoint3 = new ArrayList<PointD>();
        if (spView4Bean != null) {
            LinkedList<SpView4Bean21> labels21 = spView4Bean.getLabels2().getLabels21();
            for (int i = 0; i < labels21.size(); i++) {
                PointD pointD = new PointD(Double.valueOf(labels21.get(i).getX()), Double.valueOf(labels21.get(i).getY()));
                linePoint3.add(pointD);
            }
        }
        String a = "";
        if (spView4Bean != null) {
            a = spView4Bean.getLabels2().getTitle();
        }
        SplineData dataSeries3 = new SplineData(a, linePoint3,
                Color.rgb(221, 0, 27));
        if (spView4Bean != null) {
            LinkedList<SpView4Bean21> labels21 = spView4Bean.getLabels2().getLabels21();
            int startColor = Color.rgb(237, 0, 17);
            int startColor2 = Color.rgb(17, 0, 237);
            int stopColor = Color.rgb(0, 255, 15);
            int stopColor2 = Color.rgb(75, 180, 255);
            int stopColor3 = Color.rgb(43, 211, 255);
            int stopColor4 = Color.rgb(244, 10, 255);
            int stopColor5 = Color.rgb(255, 211, 43);
            int[] colors = new int[]{startColor, startColor2, stopColor, stopColor2, stopColor3/*, stopColor4, stopColor5*/};
            LinearGradient linearGradient = new LinearGradient(Float.valueOf(labels21.get(0).getX()), Float.valueOf(labels21.get(0).getY()),
                    Float.valueOf(labels21.get(labels21.size() - 1).getX()), Float.valueOf(labels21.get(labels21.size() - 1).getY()), colors,
                    null, Shader.TileMode.MIRROR);
            dataSeries3.getLinePaint().setShader(linearGradient);
        }
        dataSeries3.setLabelVisible(false);
        dataSeries3.setDotStyle(XEnum.DotStyle.HIDE);

        chartData.add(dataSeries3);
    }

    private void renderBezierCurvePath(Canvas canvas, Paint paint, Path bezierPath, PointF start, PointF stop, PointF[] bezierControls,
                                       int startColor, int stopColor) {
        if (null == bezierPath) {
            bezierPath = new Path();
        }
        bezierPath.reset();
        bezierPath.moveTo(start.x, start.y);


        drawGradient(paint, start, stop, startColor, stopColor);
        canvas.drawPath(bezierPath, paint);
        bezierPath.reset();
    }

    private void drawGradient(Paint paint, PointF start, PointF stop, int startColor, int stopColor) {
        if (startColor == 0 && stopColor == 0) {
            return;
        }
        LinearGradient linearGradient = new LinearGradient(start.x, start.y, stop.x, stop.y, new int[]{startColor, stopColor}, null, Shader.TileMode.MIRROR);
        paint.setShader(linearGradient);
    }

    private void chartLabels() {
        if (spView4Bean != null) {
            labels = spView4Bean.getLabels1();
        }
    }

    private void chartDesireLines() {
        String a = "";
        int a1 = 1;
        if (spView4Bean != null) {
            a = spView4Bean.getLabels2().getTitle();
            a1 = spView4Bean.getTitle31() / 20;
        }
        CustomLineData s3 = new CustomLineData(a, 16d * a1, Color.rgb(221, 0, 27), 3);
        s3.getLineLabelPaint().setColor(Color.rgb(221, 0, 27));
        s3.getLineLabelPaint().setTextSize(25);
        s3.hideLine();
        s3.setLabelOffset(10);
        mCustomLineDataset.add(s3);

    }


    @Override
    public void render(Canvas canvas) {
        try {
//            canvas.drawLine(100,50,100,400,paint);
//            LinearGradient linearGradient=new LinearGradient(0,72,6,145,new int[]{
//                    Color.rgb(255,189,22),
//                    Color.rgb(221,43,6),
//                    Color.rgb(0,25,233),
//                    Color.rgb(0,232,210)},
//                    new float[]{0,.3F,.6F,.9F},Shader.TileMode.CLAMP);
//            //new float[]{},中的数据表示相对位置，将150,50,150,300，划分10个单位，.3，.6，.9表示它的绝对位置。300到400，将直接画出rgb（0,232,210）
//            paint.setShader(linearGradient);
//            canvas.drawLine(150,50,150,400,paint);

            chart.render(canvas);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

}
