package it.portalECI.Util;

import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooter extends PdfPageEventHelper {
    protected Image imgHeader;
    protected Image imgFooter;
    public HeaderFooter(Image imgHeader, Image imgFooter) throws IOException {
    	this.imgHeader = imgHeader;
    	this.imgFooter = imgFooter;
    }
     
    @Override
	public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        if(imgHeader !=null) {
        	try {
	            float docHeight = document.getPageSize().getHeight();
	            float imgHeight = imgHeader.getScaledHeight();
	            imgHeader.setAbsolutePosition(0,docHeight-imgHeight);
	            cb.addImage(imgHeader);
	        } catch (DocumentException de) {
	        	de.printStackTrace();
	            throw new ExceptionConverter(de);
	        }
        }

        if(imgFooter !=null) {
        try {
            	imgFooter.setAbsolutePosition(0, 0);
	            cb.addImage(imgFooter);
	        } catch (DocumentException de) {
	        	de.printStackTrace();
	            throw new ExceptionConverter(de);
	        }
        }

    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
    	
    }

}
