package com.aroutmodule;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.liux.android.qrcode.QRCodeScanningActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

/**
 * @author ckckck   2019/1/22
 * life is short ,bugs are too many!
 */
public class SC extends QRCodeScanningActivity {
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
        multiFormatReader.setHints(null);
        return multiFormatReader;
    }
}
