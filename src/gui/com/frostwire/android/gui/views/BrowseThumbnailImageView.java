/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2013, FrostWire(R). All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.frostwire.android.gui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author gubatron
 * @author aldenml
 *
 */
public class BrowseThumbnailImageView extends ImageView {

    private static final Paint paintCircle = new Paint();
    private static final Paint paintTriangle = new Paint();

    static {
        paintCircle.setColor(Color.BLACK);
        paintCircle.setStrokeWidth(2);
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setAntiAlias(true);

        paintTriangle.setColor(Color.BLACK);
        paintTriangle.setStyle(Paint.Style.FILL);
        paintTriangle.setAntiAlias(true);
    }

    private boolean playVisible;

    public BrowseThumbnailImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isPlayVisible() {
        return playVisible;
    }

    public void setPlayVisible(boolean visible) {
        this.playVisible = visible;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (playVisible) {
            drawCricle(canvas);
            drawTriangle(canvas);
        }
    }

    private void drawCricle(Canvas canvas) {
        float x = getWidth() / 2.0f;
        float y = getHeight() / 2.0f;
        float r = getWidth() / 6.0f + 2;
        canvas.drawCircle(x, y, r, paintCircle);
    }

    private void drawTriangle(Canvas canvas) {
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        int w = getWidth() / 7;
        Path path = getTriangle(new Point(x - w / 2 + 3, y - w / 2), w);
        canvas.drawPath(path, paintTriangle);
    }

    private Path getTriangle(Point p1, int width) {
        Point p2 = null, p3 = null;

        p2 = new Point(p1.x, p1.y + width);
        p3 = new Point(p1.x + width, p1.y + (width / 2));

        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);

        return path;
    }
}