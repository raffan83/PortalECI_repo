package it.portalECI.bo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.jsoup.Jsoup;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.itextpdf.tool.xml.pipeline.html.LinkProvider;

import it.portalECI.DAO.GestioneTemplateQuestionarioDAO;
import it.portalECI.DTO.TemplateQuestionarioDTO;
import it.portalECI.Util.Costanti;
import it.portalECI.Util.HeaderFooter;

public class GestioneTemplateQuestionarioBO {

	public static TemplateQuestionarioDTO getQuestionarioById(Integer idTemplate, Session session) {
		return GestioneTemplateQuestionarioDAO.getTemplateById(idTemplate, session);
	}
	
	public static File QuestionarioTest(TemplateQuestionarioDTO template) throws IOException, DocumentException {
		
		String path = "QuestionarioTest"+File.separator;
		new File(Costanti.PATH_CERTIFICATI+path).mkdirs();
		File file = new File(Costanti.PATH_CERTIFICATI+path, "ProvaDownloadQuestionario.pdf");
	
		String html = new String(template.getTemplate());
		html = html.replaceAll("\\$\\{(.*?)\\}", "");
	
		final org.jsoup.nodes.Document documentJsoup = Jsoup.parse(html);
		documentJsoup.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
		String validXHTML = documentJsoup.html();
	
		Document document = new Document(PageSize.A4);
		FileOutputStream fileOutput = new FileOutputStream(file);
		PdfWriter writer = PdfWriter.getInstance(document, fileOutput);
        
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		HeaderFooter pageEventHandler;
		try {
			pageEventHandler = new HeaderFooter(
				template.getHeader(),
				template.getFooter(),
				"",
				"Revisione del "+dateFormat.format(new Date())
			);
		}catch (Exception e) {
			return null;
		}
		
		writer.setPageEvent(pageEventHandler);
		pageEventHandler.formatDocument(document);
		document.open();
    
    
		// CSS
		CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
		InputStream iscss = new FileInputStream(Costanti.PATH_FONT_STYLE+"style.css");
		CssFile cssFile = XMLWorkerHelper.getCSS(iscss);
		cssResolver.addCss(cssFile);
		cssResolver.addCss(XMLWorkerHelper.getInstance().getDefaultCSS());
    
		XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(Costanti.PATH_FONT_STYLE);
		fontProvider.register(Costanti.PATH_FONT_STYLE+"Arial.ttf");        
		fontProvider.register(Costanti.PATH_FONT_STYLE+"Times.ttf");        
		fontProvider.register(Costanti.PATH_FONT_STYLE+"Courier.ttf");
		fontProvider.setUseUnicode(true);
		CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
		// 	HTML
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
		htmlContext.setImageProvider(new AbstractImageProvider() {
			public String getImageRootPath() {
				return Costanti.PATH_ROOT;
			}
		});
		htmlContext.setLinkProvider(new LinkProvider() {
			public String getLinkRoot() {
				return Costanti.PATH_ROOT;
			}
		});
		// 	Pipelines
		PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
		HtmlPipeline htmlPipeline = new HtmlPipeline(htmlContext, pdf);
		CssResolverPipeline css = new CssResolverPipeline(cssResolver, htmlPipeline);

		XMLWorker worker = new XMLWorker(css, true);
		XMLParser p = new XMLParser(worker);
		p.parse(new ByteArrayInputStream(validXHTML.getBytes()),
    		Charset.forName("UTF-8"));

		document.close();
	
		return file;
	}
	

}
