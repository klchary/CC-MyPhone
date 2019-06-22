package com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.util.Log;

import com.example.ccmyphone.Models.UserDetails;
import com.example.ccmyphone.OtherClasses.Utils;
import com.example.ccmyphone.R;
import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static com.example.ccmyphone.ApplicationConstants.CC_MyPhone_RESUME;
import static com.example.ccmyphone.ApplicationConstants.SHARED_PERSISTENT_VALUES;
import static com.example.ccmyphone.ApplicationConstants.USER_DETAILS;

/**
 * Created by CHINNA CHARY on Friday, 21 Jun 2019
 * Project Name CCMyPhone
 * Package Name com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils
 **/
public class ResumeUtils {

    private String TAG = "ResumeUtils";
    private UserDetails userDetails;

    private Font nameFont = new Font(Font.FontFamily.HELVETICA, 18.0f, 1, CMYKColor.BLACK);
    private Font bioFont = new Font(Font.FontFamily.HELVETICA, 14.0f, 0, CMYKColor.BLACK);
    private Font mobileFont = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, 1, CMYKColor.BLACK);
    private Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, 1, CMYKColor.BLACK);
    private Font textFont = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, 0, CMYKColor.BLACK);

    public Document setupResumeBuilder(Context context) {
        Gson gson = new Gson();
        SharedPreferences sharedpref = context.getSharedPreferences(SHARED_PERSISTENT_VALUES, Context.MODE_PRIVATE);
        String userSharedDetails = sharedpref.getString(USER_DETAILS, null);
        if (userSharedDetails != null) {
            userDetails = gson.fromJson(userSharedDetails, UserDetails.class);
        }
        Log.d(TAG, "userDetails " + userDetails);

        Document document = new Document(PageSize.A4, 30f, 30f, 30f, 30f);
        Utils utils = new Utils();
        File saveFilePath = utils.CheckDirectory(context, CC_MyPhone_RESUME);
        Date date = new Date();
        String fileName;
        if (userDetails != null) {
            fileName = userDetails.getUserName() + " " + date + ".pdf";
        } else {
            fileName = "CC Resume Builder " + date + ".pdf";
        }
        File file = new File(saveFilePath.getAbsolutePath(), fileName);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            document.addAuthor("CC MyPhone");
            document.addCreator(fileName);
            document.addLanguage("English");
            document.addCreationDate();
            document.addTitle("Resume");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return document;
    }

    public PdfPCell createImageCell(Uri imageUri, Context context) throws DocumentException, IOException {
        Image image = null;
        InputStream inputStream;
//        if (imageUri != null) {
////            byte[] decodedByte = Base64.decode(waterMarkImage, 0);
////            image = Image.getInstance(decodedByte);
//            inputStream = context.getContentResolver().openInputStream(imageUri);
//        } else {
        inputStream = context.getAssets().open("userimage.jpg");
//        }
        Bitmap bmp = BitmapFactory.decodeStream(inputStream);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bmp = Bitmap.createScaledBitmap(bmp, 200, 200, true);
        Bitmap bitmap = getRoundImage(bmp);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        image = Image.getInstance(stream.toByteArray());

        image.scaleAbsoluteHeight(100f);
        image.scaleAbsoluteWidth(100f);
        PdfPCell cell = new PdfPCell(image, true);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private Bitmap getRoundImage(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public PdfPCell createTextCell(String text, int fontId, int alignmentId) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text);
        p.setAlignment(alignmentId);
        if (fontId == 0) {
            p.setFont(nameFont);
        } else if (fontId == 1) {
            p.setFont(bioFont);
        } else if (fontId == 2) {
            p.setFont(mobileFont);
        } else if (fontId == 3) {
            p.setFont(textFont);
        }
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(p);
        return cell;
    }

    public void AddEmpltyLineBeforeAfter(PdfPTable table, float before, float after) {
        table.setSpacingAfter(after);
        table.setSpacingBefore(before);
    }

    public void AddDividerVertical(Document document) throws DocumentException {
        LineSeparator ls = new LineSeparator();
        document.add(new Chunk(ls));
    }

    public void setHeadingTable(String textStr, Document document, float before, float after) throws DocumentException, IOException {
        PdfPTable pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100f);
        PdfPCell pdfPCell = createTextCell(textStr, 2, Element.ALIGN_LEFT);
        BaseColor baseColor = new BaseColor(R.color.Silver);
        pdfPCell.setBackgroundColor(baseColor);
        pdfPTable.addCell(pdfPCell);
        AddEmpltyLineBeforeAfter(pdfPTable, before, after);
        document.add(pdfPTable);
    }

}
