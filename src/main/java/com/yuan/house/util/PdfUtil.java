package com.yuan.house.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.yuan.house.constants.Constants;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class PdfUtil {
    public static String  changeTxtToPdf(String msg) {
        //页面大小
        Rectangle rect = new Rectangle(PageSize.A4.rotate());
        //页面背景色
        rect.setBackgroundColor(BaseColor.WHITE);

        try {
            // 使用Windows系统字体(TrueType)
            BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font font = new Font(baseFont);
            // 设置字体大小
            font.setSize(12);
            Document doc = new Document(rect);
            String filename = UUID.randomUUID() + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(Constants.UPLOAD_URL + filename));
            //添加属性信息
            doc.addAuthor("租否");
            doc.addTitle("租房合同");
            doc.addSubject("租房合同");
            doc.addKeywords("私密");
            //页边空白
            doc.setMargins(10, 10, 20, 10);
            doc.open();
            doc.add(new Paragraph(msg,font));
            doc.close();
            return filename;
        } catch (DocumentException e) {
            LoggerUtil.error("pdf文件创建失败",e);
            return null;
        } catch (FileNotFoundException e) {
            LoggerUtil.error("目的文件不存在",e);
            return null;
        } catch (IOException e) {
            LoggerUtil.error("windows字体不存在",e);
            return null;
        }
    }
}
