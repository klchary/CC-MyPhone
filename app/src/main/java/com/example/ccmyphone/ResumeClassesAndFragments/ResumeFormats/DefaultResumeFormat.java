package com.example.ccmyphone.ResumeClassesAndFragments.ResumeFormats;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.example.ccmyphone.ResumeClassesAndFragments.ResumeEntity.EduQualifications;
import com.example.ccmyphone.ResumeClassesAndFragments.ResumeEntity.PersonalProResume;
import com.example.ccmyphone.Models.UserDetails;
import com.example.ccmyphone.OtherClasses.Utils;
import com.example.ccmyphone.R;
import com.example.ccmyphone.ResumeClassesAndFragments.ResumeUtils.ResumeUtils;
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

public class DefaultResumeFormat {

    private Gson gson = new Gson();
    private ResumeUtils resumeUtils = new ResumeUtils();

    public void DefaultResumeF1(Context context, Uri imageUri, String personalDetails,
                              String permaAddress, String presentAddress, String eduQualifications,
                              String professinalStr, String hobbiesStr, String languagesKnownStr) {
        String TAG = "DefaultResumeFormat";
        Log.d(TAG, "Bundles " + personalDetails + "\n" + permaAddress + "\n" + presentAddress + "\n" + eduQualifications
                + "\n" + professinalStr + "\n" + hobbiesStr + "\n" + languagesKnownStr);
        Document document = resumeUtils.setupResumeBuilder(context);
        try {
            PdfPTable userDetailsTable = new PdfPTable(3);
            userDetailsTable.setWidthPercentage(100);
            userDetailsTable.setWidths(new int[]{20, 50, 30});

            // user Image cell
            PdfPCell firstTableCell = new PdfPCell();
            firstTableCell.setBorder(PdfPCell.NO_BORDER);
            firstTableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            PdfPTable firstTable = new PdfPTable(1);
            firstTable.setWidthPercentage(80);
            //......... add some cells here ...........
            PdfPCell imageCell = resumeUtils.createImageCell(imageUri, context);
            firstTable.addCell(imageCell);
            firstTableCell.addElement(firstTable);
            userDetailsTable.addCell(firstTableCell);
            Log.d(TAG, "Image Cell added to table");

            if (personalDetails != null) {
                PersonalProResume personalProResume = new PersonalProResume();
                personalProResume = gson.fromJson(personalDetails, PersonalProResume.class);
                ArrayList<PersonalProResume> proResumes = new ArrayList<>();
                proResumes.add(personalProResume);
                // User Details cell
                PdfPCell secondTableCell = new PdfPCell();
                secondTableCell.setBorder(PdfPCell.NO_BORDER);
                secondTableCell.setVerticalAlignment(Element.ALIGN_LEFT);
                PdfPTable secondTable = new PdfPTable(1);
                secondTable.setWidthPercentage(100);
                //......... add some cells here ...........
                PdfPCell txt1 = resumeUtils.createTextCell(proResumes.get(0).getFullName(), 0, Element.ALIGN_LEFT);
                PdfPCell txt2 = resumeUtils.createTextCell(proResumes.get(0).getFatherName() + "\n" + proResumes.get(0).getMotherName(), 1, Element.ALIGN_LEFT);
                PdfPCell txt3 = resumeUtils.createTextCell(proResumes.get(0).getMobileNo(), 2, Element.ALIGN_LEFT);
                secondTable.addCell(txt1);
                secondTable.addCell(txt2);
                secondTable.addCell(txt3);
                secondTableCell.addElement(secondTable);
                userDetailsTable.addCell(secondTableCell);
                Log.d(TAG, "User Details added to table");
            }

            // social details cell
            PdfPCell thirdCell = new PdfPCell();
            thirdCell.setBorder(PdfPCell.NO_BORDER);
            thirdCell.setVerticalAlignment(Element.ALIGN_RIGHT);
            PdfPTable thirdTable = new PdfPTable(1);
            thirdTable.setWidthPercentage(100);
            //......... add some cells here ...........
            ArrayList<String> socialArray = new ArrayList<>();
            socialArray.add("Facebook");
            socialArray.add("GMail");
            socialArray.add("Mobile");
            socialArray.add("LinkedIn");
            socialArray.add("Twitter");
            for (int i = 0; i < socialArray.size(); i++) {
                PdfPCell socialCell = resumeUtils.createTextCell(socialArray.get(i), 3, Element.ALIGN_RIGHT);
                thirdTable.addCell(socialCell);
            }
            thirdCell.addElement(thirdTable);
            userDetailsTable.addCell(thirdCell);
            Log.d(TAG, "Social Cell added to table");

            document.add(userDetailsTable);
            resumeUtils.AddDividerVertical(document);

            resumeUtils.setHeadingTable("Educational Qualifications", document, 10f, 4f);
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
                    resumeUtils.AddEmpltyLineBeforeAfter(pdfPTable, 0f, 10f);
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

            resumeUtils.setHeadingTable("Communication Details", document, 10f, 4f);
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{50, 50});
            PdfPCell permanantcell = new PdfPCell();
            permanantcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(permaAddress);
            PdfPCell presentcell = new PdfPCell();
            presentcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(presentAddress);
            resumeUtils.AddEmpltyLineBeforeAfter(table, 1, 20f);
            document.add(table);
            Log.d(TAG, "Address Details Table Cell Added");

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
