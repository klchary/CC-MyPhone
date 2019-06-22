package com.example.ccmyphone.ResumeClassesAndFragments.ResumeEntity;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by CHINNA CHARY on Friday, 14 Jun 2019
 * Project Name CCMyPhone
 * Package Name com.example.ccmyphone.Models
 **/
public class PdfItemViewModel {

    private String pdfName;
    private Bitmap pdfBitmap;
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Bitmap getPdfBitmap() {
        return pdfBitmap;
    }

    public void setPdfBitmap(Bitmap pdfBitmap) {
        this.pdfBitmap = pdfBitmap;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }
}
