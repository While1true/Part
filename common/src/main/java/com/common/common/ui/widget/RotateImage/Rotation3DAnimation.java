package com.common.common.ui.widget.RotateImage;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

//public class Rotation3DAnimation extends Animation {
//    final float startDegree;
//    final float endDegree;
//    final float deep;
//    final float centreX;
//    final float centreY;
//    final boolean reverse;
//    final private Camera camera;
//
//    public Rotation3DAnimation(float startDegree, float endDegree, float deep, float centreX, float centreY, boolean reverse) {
//        this.startDegree = startDegree;
//        this.endDegree = endDegree;
//        this.deep = deep;
//        this.centreX = centreX;
//        this.centreY = centreY;
//        this.reverse = reverse;
//        this.camera = new Camera();
//
//    }
//
//    @Override
//    protected void applyTransformation(float interpolatedTime, Transformation t) {
//        if (centreY == 0) {
//            return;
//        }
//        float currentDegree = startDegree + (endDegree - startDegree) * interpolatedTime;
//        Matrix matrix = t.getMatrix();
//        camera.save();
//        camera.translate(0, 0, deep*(1-interpolatedTime));
//        camera.rotateY(currentDegree);
//        camera.getMatrix(matrix);
//        matrix.preScale(!reverse ? interpolatedTime : (1 - interpolatedTime), !reverse ? interpolatedTime : (1 - interpolatedTime));
//        matrix.postScale(1, 1);
//        camera.restore();
////        matrix.preTranslate(-centreX, -centreY);
////        matrix.postTranslate(centreX, centreY);
//    }
//}

public class Rotation3DAnimation extends Animation {
    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final float scale;
    private final boolean mReverse;
    // 修正失真，主要修改 MPERSP_0 和 MPERSP_1
    float[] mValues = new float[9];
    private Camera mCamera;
    /**
     * 创建一个绕y轴旋转的3D动画效果，旋转过程中具有深度调节，可以指定旋转中心。
     *
     * @param fromDegrees   起始时角度
     * @param toDegrees     结束时角度
     * @param centerX       旋转中心x坐标
     * @param centerY       旋转中心y坐标
     * @param depthZ        最远到达的z轴坐标
     * @param reverse       true 表示由从0到depthZ，false相反
     */
    public Rotation3DAnimation(Context context, float fromDegrees, float toDegrees,
                             float centerX, float centerY, float depthZ, boolean reverse) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        mReverse = reverse;

        // 获取手机像素密度 （即dp与px的比例）
        scale = context.getResources().getDisplayMetrics().density;
    }
    //只执行一次
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;
        final Matrix matrix = t.getMatrix();
        camera.save();

        // 调节深度
        if (mReverse) {
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
        } else {
            camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
        }

        // 绕y轴旋转
        camera.rotateY(degrees);

        camera.getMatrix(matrix);
        matrix.postScale(1, 1);
        matrix.preScale(!mReverse ? interpolatedTime : (1 - interpolatedTime), !mReverse ? interpolatedTime : (1 - interpolatedTime));
        camera.restore();
        matrix.getValues(mValues);              //获取数值
        mValues[6] = mValues[6]/scale;          //数值修正
        mValues[7] = mValues[7]/scale;          //数值修正
        matrix.setValues(mValues);              //重新赋值

        // 调节中心点
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}