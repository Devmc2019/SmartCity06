package com.devmc.smartcity6.ui.analysis;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.devmc.smartcity6.R;
import com.devmc.smartcity6.entity.News;
import com.devmc.smartcity6.http.CbkImp;
import com.devmc.smartcity6.http.Http;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;

public class AnalysisActivity extends AppCompatActivity {
    private BarChart chart;
    private LineChart chart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        chart = findViewById(R.id.chart);
        chart2 = findViewById(R.id.chart2);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Http.service.getNews().enqueue(new CbkImp<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                super.onResponse(call, response);
                if (response.body().getCode() == 200) {
                    List<News.RowsBean> newsList = response.body().getRows();
                    Collections.sort(newsList, (o1, o2) -> o2.getLikeNum() - o1.getLikeNum());
                    setView(newsList.subList(0, 5));
                    setView2(newsList.subList(0, 5));
                } else {
                    Http.toastError();
                }
            }
        });
    }

    private void setView(List<News.RowsBean> newsList) {
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.getDescription().setText("asdasasd");
        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(true);

        chart.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return newsList.get((int) value).getTitle().substring(0, 6) + "...";
            }
        });
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < newsList.size(); i++) {
            values.add(new BarEntry(i, newsList.get(i).getLikeNum()));
        }
        BarDataSet set1;
        set1 = new BarDataSet(values, "点赞排行");
        {
            set1.setDrawIcons(false);
            int startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            int startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            int startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light);
            int startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light);
            int endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
            int endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple);
            int endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark);
            int endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark);
            int endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark);
            List<GradientColor> gradientColors = new ArrayList<>();
            gradientColors.add(new GradientColor(startColor1, endColor1));
            gradientColors.add(new GradientColor(startColor2, endColor2));
            gradientColors.add(new GradientColor(startColor3, endColor3));
            gradientColors.add(new GradientColor(startColor4, endColor4));
            gradientColors.add(new GradientColor(startColor5, endColor5));
            set1.setGradientColors(gradientColors);
        }
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);
        chart.setData(data);
        chart.invalidate();
    }

    private void setView2(List<News.RowsBean> newsList) {
        // // Chart Style // //
        chart2.getDescription().setEnabled(false);
        chart2.setTouchEnabled(true);
        chart2.setDrawGridBackground(false);
        chart2.setDragEnabled(true);
        chart2.setScaleEnabled(true);
        chart2.setPinchZoom(true);
        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart2.getXAxis();
            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
            xAxis.setGranularity(1f);
        }
        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart2.getAxisLeft();
            // disable dual axis (only use LEFT axis)
            chart2.getAxisRight().setEnabled(false);
            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);
        }
        {   // // Create Limit Lines // //
            LimitLine llXAxis = new LimitLine(9f, "Index 10");
            llXAxis.setLineWidth(4f);
            llXAxis.enableDashedLine(10f, 10f, 0f);
            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            llXAxis.setTextSize(10f);

        }
        /*ArrayList<Entry> values = new ArrayList<>();
        Random r = new Random();
        values.add(new Entry(0, r.nextInt(10) * r.nextInt(20)));
        values.add(new Entry(1, r.nextInt(10) * r.nextInt(20)));
        values.add(new Entry(2, r.nextInt(10) * r.nextInt(20)));
        values.add(new Entry(3, r.nextInt(10) * r.nextInt(20)));
        values.add(new Entry(4, r.nextInt(10) * r.nextInt(20)));
        values.add(new Entry(5, r.nextInt(10) * r.nextInt(20)));
        values.add(new Entry(6, r.nextInt(10) * r.nextInt(20)));*/

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return newsList.get((int) value).getTitle().substring(0, 6) + "...";
            }
        });
        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < newsList.size(); i++) {
            values.add(new Entry(i, newsList.get(i).getLikeNum()));
        }


        LineDataSet set1;
        set1 = new LineDataSet(values, "DataSet 1");
        set1.setDrawIcons(false);
        // draw dashed line
        set1.enableDashedLine(10f, 5f, 0f);
        // black lines and points
        // line thickness and point size
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        // draw points as solid circles
        set1.setDrawCircleHole(false);
        // customize legend entry
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);
        // text size of values
        set1.setValueTextSize(9f);
        // draw selection line as dashed
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        // set the filled area
        set1.setDrawFilled(true);
        set1.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return chart2.getAxisLeft().getAxisMinimum();
            }
        });
        // set color of filled area

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets
        // create a data object with the data sets
        LineData data = new LineData(dataSets);
        // set data
        chart2.setData(data);
        chart2.invalidate();
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}