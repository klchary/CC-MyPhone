package com.example.ccmyphone.OtherClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.example.ccmyphone.Models.EduQualifications;
import com.example.ccmyphone.Models.PersonalProResume;
import com.example.ccmyphone.Models.UserDetails;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import static com.example.ccmyphone.ApplicationConstants.CC_MyPhone_RESUME;
import static com.example.ccmyphone.ApplicationConstants.SHARED_PERSISTENT_VALUES;
import static com.example.ccmyphone.ApplicationConstants.USER_DETAILS;

public class GenerateResume {

    private String TAG = "GenerateResume";
    private Gson gson = new Gson();
    private UserDetails userDetails = null;

    private Font nameFont = new Font(Font.FontFamily.HELVETICA, 18.0f, 1, CMYKColor.BLACK);
    private Font bioFont = new Font(Font.FontFamily.HELVETICA, 14.0f, 0, CMYKColor.BLACK);
    private Font mobileFont = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, 1, CMYKColor.BLACK);

    private Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, 1, CMYKColor.BLACK);
    private Font textFont = new Font(Font.FontFamily.TIMES_ROMAN, 14.0f, 0, CMYKColor.BLACK);

    public void DefaultResume(Context context, Uri imageUri, String personalDetails,
                              String permaAddress, String presentAddress, String eduQualifications) {
        Log.d(TAG, "Bundles " + personalDetails + "\n" + permaAddress + "\n" + presentAddress + "\n" + eduQualifications);
        Document document = setupResumeBuilder(context);
        try {
            PdfPTable mainTable = new PdfPTable(2);
            mainTable.setWidthPercentage(100);
            mainTable.setWidths(new int[]{80, 20});

            // First table cell
            PdfPCell firstTableCell = new PdfPCell();
//            firstTableCell.setBorder(PdfPCell.NO_BORDER);
            firstTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            PdfPTable firstTable = new PdfPTable(1);
            firstTable.setWidthPercentage(80);
            //......... add some cells here ...........
            PdfPCell cell1 = createImageCell(imageUri, context);
            firstTable.addCell(cell1);
            firstTableCell.addElement(firstTable);

            if (personalDetails != null) {
                PersonalProResume personalProResume = new PersonalProResume();
                personalProResume = gson.fromJson(personalDetails, PersonalProResume.class);
                ArrayList<PersonalProResume> proResumes = new ArrayList<>();
                proResumes.add(personalProResume);
                // Second table cell
                PdfPCell secondTableCell = new PdfPCell();
                secondTableCell.setBorder(PdfPCell.NO_BORDER);
                secondTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                PdfPTable secondTable = new PdfPTable(1);
                secondTable.setWidthPercentage(100);
                //......... add some cells here ...........
                PdfPCell txt1 = createTextCell(proResumes.get(0).getFullName(), 0);
                PdfPCell txt2 = createTextCell(proResumes.get(0).getFatherName() + "\n" + proResumes.get(0).getMotherName(), 1);
                PdfPCell txt3 = createTextCell(proResumes.get(0).getMobileNo(), 2);
                secondTable.addCell(txt1);
                secondTable.addCell(txt2);
                secondTable.addCell(txt3);
                secondTableCell.addElement(secondTable);
                mainTable.addCell(secondTableCell);
                Log.d(TAG, "Personal Details Second Table Cell Added");
            }

            mainTable.addCell(firstTableCell);
            Log.d(TAG, "Personal Details First Table Cell Added");
            AddEmpltyLineBeforeAfter(mainTable, 0f, 6f);
            document.add(mainTable);

            AddDividerVertical(document);

            setHeadingTable("Educational Qualifications", document, 10f, 4f);
            try {
                JSONArray jsonArray = new JSONArray(eduQualifications);
                ArrayList<EduQualifications> quaArrayList = new ArrayList<>();
                EduQualifications qualifications;
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        qualifications = new EduQualifications();
                        String quaObject = jsonArray.getJSONObject(i).toString();
                        qualifications = gson.fromJson(quaObject, EduQualifications.class);
                        quaArrayList.add(qualifications);
                    }
                    PdfPTable pdfPTable = new PdfPTable(9);
                    AddEmpltyLineBeforeAfter(pdfPTable, 0f, 10f);
                    pdfPTable.setWidthPercentage(100);
                    PdfPCell pdfPCell = new PdfPCell();
                    pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    for (int j = 0; j < quaArrayList.size(); j++) {
                        pdfPTable.addCell(String.valueOf(quaArrayList.get(j).getLayoutId()));
                        pdfPTable.addCell(quaArrayList.get(j).getCourseName());
                        pdfPTable.addCell(quaArrayList.get(j).getCoureFrom());
                        pdfPTable.addCell(quaArrayList.get(j).getCourseTo());
                        pdfPTable.addCell(quaArrayList.get(j).getInstitutionName());
                        pdfPTable.addCell(quaArrayList.get(j).getInstitutionDist());
                        pdfPTable.addCell(quaArrayList.get(j).getInstitutionState());
                        pdfPTable.addCell(quaArrayList.get(j).getQualificationTitle());
                        pdfPTable.addCell(String.valueOf(quaArrayList.get(j).getPercentage()));
                    }
                    document.add(pdfPTable);
                    Log.d(TAG, "Educational Qualification Details Table Cell Added");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            setHeadingTable("Communication Details", document, 10f, 4f);
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{50, 50});
            PdfPCell permanantcell = new PdfPCell();
            permanantcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(permaAddress);
            PdfPCell presentcell = new PdfPCell();
            presentcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(presentAddress);
            AddEmpltyLineBeforeAfter(table, 1, 20f);
            document.add(table);
            Log.d(TAG, "Address Details Table Cell Added");

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Document setupResumeBuilder(Context context) {
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

    private PdfPCell createImageCell(Uri imageUri, Context context) throws DocumentException, IOException {
        Image image = null;
        InputStream inputStream;
//        if (imageUri != null) {
////            byte[] decodedByte = Base64.decode(waterMarkImage, 0);
////            image = Image.getInstance(decodedByte);
//            inputStream = context.getContentResolver().openInputStream(imageUri);
//        } else {
        inputStream = context.getAssets().open("admin_icon.png");
//        }
        Bitmap bmp = BitmapFactory.decodeStream(inputStream);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bmp = Bitmap.createScaledBitmap(bmp, 200, 200, true);
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        image = Image.getInstance(stream.toByteArray());

        image.scaleAbsoluteHeight(100f);
        image.scaleAbsoluteWidth(100f);
        PdfPCell cell = new PdfPCell(image, true);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private PdfPCell createTextCell(String text, int id) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text);
        p.setAlignment(Element.ALIGN_LEFT);
        if (id == 0) {
            p.setFont(nameFont);
        } else if (id == 1) {
            p.setFont(bioFont);
        } else if (id == 2) {
            p.setFont(mobileFont);
        } else if (id == 3) {
            p.setFont(textFont);
        }
        cell.setBorder(Rectangle.NO_BORDER);
        cell.addElement(p);
        return cell;
    }

    private void AddEmpltyLineBeforeAfter(PdfPTable table, float before, float after) {
        table.setSpacingAfter(after);
        table.setSpacingBefore(before);
    }

    private void AddDividerVertical(Document document) throws DocumentException {
        LineSeparator ls = new LineSeparator();
        document.add(new Chunk(ls));
    }

    private void setHeadingTable(String textStr, Document document, float before, float after) throws DocumentException, IOException {
        PdfPTable pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100f);
        PdfPCell pdfPCell = createTextCell(textStr, 2);
        BaseColor baseColor = new BaseColor(R.color.Silver);
        pdfPCell.setBackgroundColor(baseColor);
        pdfPTable.addCell(pdfPCell);
        AddEmpltyLineBeforeAfter(pdfPTable, before, after);
        document.add(pdfPTable);
    }

}
