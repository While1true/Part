package com.part.common.ui.widget.Scrolling.MixScroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.part.common.ui.widget.Scrolling.MixScroll.Base.RefreshState;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.Refreshable;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.SizeUtil;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.ViewControlUtil;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.XmlAttr;
import com.part.common.ui.widget.Scrolling.ScrollDirection;
import com.part.common.ui.widget.Scrolling.Scrolling;


/**
 * @author ckckck  ${data}
 * life is short ,bugs are too many!
 */
public class SimpleHeaderFooter extends FrameLayout implements Refreshable {
    private Context context;
    private ProgressBar progressBar;
    private TextView textView;
    private RefreshState state = RefreshState.IDEL;
    private boolean isHeader = true;
    private boolean isnomore = false;
    private ScrollDirection direction = ScrollDirection.Y;

    public SimpleHeaderFooter(Context context) {
        this(context, null);
    }

    public SimpleHeaderFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleHeaderFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        if (attrs != null) {
            XmlAttr xmlAttr = ViewControlUtil.getXmlAttr(attrs);
            direction = xmlAttr.getScrollDirection();
            isHeader = xmlAttr.isHeader();
        }
    }


    public SimpleHeaderFooter(Context context, boolean isHeader) {
        this(context, null);
        this.isHeader = isHeader;
    }

    public SimpleHeaderFooter(Context context, boolean isHeader, ScrollDirection direction) {
        this(context, null);
        this.isHeader = isHeader;
        this.direction = direction;
    }

    @Override
    public void onLayout(ScrollDirection direction, Scrolling parent, boolean changed, int left, int top, int right, int bottom) {
        ViewControlUtil.onLayout(direction, this, parent, bottom);
    }

    @Override
    public boolean isHeader() {
        return isHeader;
    }

    @Override
    public View getContentView() {
        if (progressBar == null) {
            progressBar = new ProgressBar(context);
            progressBar.setVisibility(View.GONE);
            textView = new TextView(context);
            if (direction == ScrollDirection.X) {
                textView.setEms(1);
            }
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(direction == ScrollDirection.Y ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);
            int size = SizeUtil.dp2px(context, 25);
//            linearLayout.setPadding(0, 0, direction==ScrollDirection.Y?size:0, direction==ScrollDirection.Y?0:size);
            linearLayout.addView(progressBar, new ViewGroup.LayoutParams(size, size));
            linearLayout.addView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            addView(linearLayout, direction == ScrollDirection.Y ? new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, canRefreshSpace(), Gravity.CENTER) : new LayoutParams(canRefreshSpace(), ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        }
        return this;
    }

    @Override
    public void onPull(float pull, boolean fling) {
        if (isnomore || state.ordinal() > 2) {
            return;
        }
        if (pull > canSecondFloorSpace() && canSecondFloorSpace() > canRefreshSpace()) {
            if (!textView.getText().toString().equals("进入二楼")) {
                textView.setText("进入二楼");
            }
        } else if (pull > canRefreshSpace()) {
            String release = isHeader ? "放开刷新" : "放开加载";
            if (!textView.getText().toString().equals(release)) {
                textView.setText(release);
            }
        } else {
            String pullheader = direction == ScrollDirection.X ? "右拉刷新" : "下拉刷新";
            String pullfooter = direction == ScrollDirection.X ? "左拉加载" : "上拉加载";
            String release = isHeader ? pullheader : pullfooter;
            if (!textView.getText().toString().equals(release)) {
                textView.setText(release);
            }
        }
    }

    @Override
    public void onStateChange(RefreshState state) {
        if (isnomore) {
            return;
        }
        this.state = state;
        if (state == RefreshState.REFRESHING || state == RefreshState.LOADING) {
            String release = isHeader ? "正在刷新" : "正在加载";
            textView.setText(release);
            progressBar.setVisibility(View.VISIBLE);
        } else if (state == RefreshState.SECONDFLOOR) {
            setVisibility(INVISIBLE);
        } else {
            setVisibility(VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public int canPullSpace() {
        return SizeUtil.dp2px(context, 200);
    }

    @Override
    public int secondFloorSpace() {
        return 0;
    }

    @Override
    public int canSecondFloorSpace() {
        return 0;
    }

    @Override
    public int canRefreshSpace() {
        return SizeUtil.dp2px(context, 50);
    }

    public void setHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }

    public void setIsnomore(boolean isnomore, String info) {
        this.isnomore = isnomore;
        if (isnomore) {
            textView.setText(info);
            progressBar.setVisibility(View.GONE);
        }
    }
}
