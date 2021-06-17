package com.example.pacemaker.study.ui.mystudy.chart;

import android.graphics.Canvas;
import android.util.Log;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class StudyXAxisRenderer extends XAxisRenderer {
    public StudyXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
        super(viewPortHandler, xAxis, trans);
    }

    @Override
    protected void drawLabel(Canvas c, String formattedLabel, float x, float y, MPPointF anchor, float angleDegrees) {
        String line[] = formattedLabel.split(" ");
        Utils.drawXAxisValue(c, line[0], x, y, mAxisLabelPaint, anchor, angleDegrees);
        if (line.length > 1)
            Utils.drawXAxisValue(c, line[1], x, y + mAxisLabelPaint.getTextSize(), mAxisLabelPaint, anchor, angleDegrees);
    }
}