package com.example.basecommon.model;

import android.graphics.Bitmap;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

public class SaveFile {
    public static void PdfSave(File Dir,
                        Bitmap captureView,
                        String FileName,
                        String output){
        try {
            FileOutputStream fos;
            Document document = new Document();
            fos = new FileOutputStream(new File(Dir,FileName));
            captureView.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.close();
            fos = new FileOutputStream(output);
            PdfWriter writer = PdfWriter.getInstance(document, fos);
            writer.open();
            document.open();
            Image image = Image.getInstance(Dir.getPath() + '/' + FileName);
            image.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
            float x = (PageSize.A4.getWidth() - image.getScaledWidth()) / 2;
            float y = (PageSize.A4.getHeight() - image.getScaledHeight()) / 2;
            image.setAbsolutePosition(x, y);
            document.add(image);
            document.close();
            writer.close();
            fos.close();
        } catch (Exception e) {

        }

    }
}
