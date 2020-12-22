package it.portalECI.Util;

import java.io.IOException;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class HeaderFooter extends PdfPageEventHelper {
	
	private static float LEFT_MARGIN = 40;
	private static float RIGHT_MARGIN = 40;
	private static float MIN_TOP_MARGIN = 72;
	private static float MIN_BOTTOM_MARGIN = 72;
	private static float TABLE_FOOTER_HEIGHT = 24;
	private static float FIXED_FOOTER_FONT_SIZE = 7;
	
    protected Image imgHeader;
    protected Image imgFooter;
    protected PdfPTable tblSubheader;
    
    protected String subheader; 
    protected PdfTemplate totalPage;
    protected String footerLeft;
    protected String footerRight;
    protected Font font;
    protected float docWidth;
    protected float subheaderHeight;
    
    
    public HeaderFooter(String imgHeaderPath, String subheader, String imgFooterPath, String footerLeft, String footerRight) throws IOException, BadElementException {
    	this.footerLeft = footerLeft;
    	this.footerRight = footerRight;
    	this.subheader = subheader;

    	if(imgHeaderPath != null && !imgHeaderPath.isEmpty()) {
    		imgHeader = Image.getInstance(Costanti.PATH_HEADER_IMAGE+imgHeaderPath);
        }
            
    	if(imgFooterPath != null && !imgFooterPath.isEmpty()) {
			imgFooter = Image.getInstance(Costanti.PATH_FOOTER_IMAGE+imgFooterPath);
        }
    }

    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
		try {
			this.font = new Font(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED), FIXED_FOOTER_FONT_SIZE);
		} catch (DocumentException | IOException e) {
			this.font = null;
			e.printStackTrace();
		}
		
    	totalPage = writer.getDirectContent().createTemplate(50,(TABLE_FOOTER_HEIGHT/2)-3);
    }

    @Override
	public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
		
        //float docWidth = document.getPageSize().getWidth();
        float tableWith= docWidth-LEFT_MARGIN-RIGHT_MARGIN;
        float standardCellWidth = tableWith/3;
        
        PdfPTable table = new PdfPTable(4);
        try {
        	table.setWidths(new float[]{standardCellWidth, standardCellWidth/2, standardCellWidth/2, standardCellWidth});
            table.setTotalWidth(tableWith);
            
            table.setLockedWidth(true);
            table.getDefaultCell().setFixedHeight(TABLE_FOOTER_HEIGHT);
            
            PdfPCell leftCell = new PdfPCell(new Phrase(footerLeft, font));
            leftCell.setBorder(Rectangle.NO_BORDER);
            leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            leftCell.setVerticalAlignment(Element.ALIGN_BASELINE);
            leftCell.setFixedHeight(TABLE_FOOTER_HEIGHT);
            table.addCell(leftCell);
            
            PdfPCell numberPageCell = new PdfPCell(new Phrase(String.format("%d /", writer.getPageNumber()), font));
            numberPageCell.setBorder(Rectangle.NO_BORDER);
            numberPageCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            numberPageCell.setVerticalAlignment(Element.ALIGN_BASELINE);
            numberPageCell.setFixedHeight(TABLE_FOOTER_HEIGHT);
            table.addCell(numberPageCell);
            
            PdfPCell totalNumberPageCell = new PdfPCell(Image.getInstance(totalPage));           
            totalNumberPageCell.setBorder(Rectangle.NO_BORDER);
            totalNumberPageCell.setVerticalAlignment(Element.ALIGN_BASELINE);
            totalNumberPageCell.setFixedHeight(TABLE_FOOTER_HEIGHT);
            table.addCell(totalNumberPageCell);
            
            PdfPCell rightCell = new PdfPCell(new Phrase(footerRight, font));
            rightCell.setBorder(Rectangle.NO_BORDER);
            rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            rightCell.setVerticalAlignment(Element.ALIGN_BASELINE);
            rightCell.setFixedHeight(TABLE_FOOTER_HEIGHT);
            table.addCell(rightCell);
            
            
            table.writeSelectedRows(0, -1,LEFT_MARGIN, TABLE_FOOTER_HEIGHT, cb);

		} catch (DocumentException e) {
			e.printStackTrace();
		}

        float docHeight = document.getPageSize().getHeight();
        float imgHeight = 0;
        
        if (imgHeader !=null) {
	    	try {
	            imgHeight = imgHeader.getScaledHeight();
	            imgHeader.setAbsolutePosition(0,docHeight-imgHeight);
	            cb.addImage(imgHeader);        
	        } catch (DocumentException de) {
	        	de.printStackTrace();
	            throw new ExceptionConverter(de);
	        }
        }
        
    	if (tblSubheader != null) {
    		tblSubheader.writeSelectedRows(0, -1, LEFT_MARGIN, docHeight-imgHeight, cb);
    	}

        if (imgFooter !=null) {
	        try {
            	imgFooter.setAbsolutePosition(0, TABLE_FOOTER_HEIGHT);
	            cb.addImage(imgFooter);
	        } catch (DocumentException de) {
	        	de.printStackTrace();
	            throw new ExceptionConverter(de);
	        }
	    }

    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(totalPage, Element.ALIGN_BASELINE,new Phrase(String.valueOf(writer.getPageNumber()), font),0, 0, 0);
    }
    
    public void formatDocument(Document document) {
		float marginTop = MIN_TOP_MARGIN;
		float marginBottom = MIN_BOTTOM_MARGIN;
		docWidth = document.getPageSize().getWidth();
		
		if(subheader != null && !subheader.isEmpty()) {
    		StringBuilder sb = new StringBuilder(subheader);
    		tblSubheader = new PdfPTable(1);
			try {			
				tblSubheader.setTotalWidth(docWidth-LEFT_MARGIN-RIGHT_MARGIN);
				PdfPCell cell = new PdfPCell();
				ElementList list = new ElementList();
				list = XMLWorkerHelper.parseToElementList(sb.toString(), null);
				for (Element element : list) {
				    cell.addElement(element);
				}
				cell.setBorder(Rectangle.NO_BORDER);
				cell.setVerticalAlignment(Element.ALIGN_BASELINE);
				tblSubheader.addCell(cell);
				subheaderHeight = tblSubheader.getTotalHeight();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
		
		if(imgHeader != null) {
			imgHeader.scaleToFit(document.getPageSize());
			marginTop = Math.max(MIN_TOP_MARGIN, imgHeader.getScaledHeight()+subheaderHeight);
		}
		
		if(imgFooter != null) {
			imgFooter.scaleToFit(document.getPageSize());
			marginBottom = Math.max(MIN_BOTTOM_MARGIN, imgFooter.getScaledHeight()+TABLE_FOOTER_HEIGHT);
		}
		document.setMargins(LEFT_MARGIN,RIGHT_MARGIN,marginTop,marginBottom);

    }

}
