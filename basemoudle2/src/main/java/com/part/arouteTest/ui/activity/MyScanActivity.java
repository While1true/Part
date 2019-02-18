package com.part.arouteTest.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.liux.android.qrcode.QRCodeScanningActivity;
import com.liux.android.qrcode.QRCodeScanningFragment;
import com.common.common.util.ToastUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

/**
 * by ckckck 2019/1/22
 * <p>
 * life is short , bugs are too many!
 */
public class MyScanActivity extends QRCodeScanningActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public MultiFormatReader onCreateReader() {
        Collection<BarcodeFormat> decodeFormats = new ArrayList<>();
        decodeFormats.add(BarcodeFormat.QR_CODE);
        decodeFormats.add(BarcodeFormat.CODE_128);

        Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

        MultiFormatReader multiFormatReader = new MultiFormatReader();
        multiFormatReader.setHints(hints);
        return multiFormatReader;
    }

    @Override
    public void onResult(Result result) {
        ToastUtil.showSuccessToast(result.getText());
        resetToContinue();
    }

    private void resetToContinue() {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("QRCodeScanningActivity");
        if (fragment instanceof QRCodeScanningFragment) {
            ((QRCodeScanningFragment) fragment).reset();

        }
    }
}
