package it.portalECI.action;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.Map;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import TemplateReport.PivotTemplate;
import it.portalECI.DAO.GestioneCampioneDAO;
import it.portalECI.DAO.SessionFacotryDAO;
import it.portalECI.DTO.CampioneDTO;
import it.portalECI.Exception.ECIException;
import it.portalECI.Util.Costanti;
import it.portalECI.Util.Templates;
import it.portalECI.Util.Utility;
import it.portalECI.bo.GestioneCampioneBO;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.jasperreports.engine.JREmptyDataSource;

/**
 * Servlet implementation class ScaricaCertificato
 */
@WebServlet(name= "/scaricaEtichetta", urlPatterns = { "/scaricaEtichetta.do" })
public class ScaricaEtichetta extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger(ScaricaEtichetta.class);   
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ScaricaEtichetta() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("static-access")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(Utility.validateSession(request,response,getServletContext()))return;

		Session session=SessionFacotryDAO.get().openSession();
		session.beginTransaction();	
		JsonObject myObj = new JsonObject();

		boolean ajax = false;

		String action = request.getParameter("action");
		
		try
		{	
			
		if(action == null || action.equals("stampaEtichetta")) {
			
			String id_campione=request.getParameter("idcampione");

			CampioneDTO campione = GestioneCampioneDAO.getCampioneFromId(id_campione, session);	
			ajax = false;

			InputStream is = PivotTemplate.class.getResourceAsStream("EtichettaZebra.jrxml");
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			JasperReportBuilder report = DynamicReports.report();

			report.setTemplateDesign(is);
			report.setTemplate(Templates.reportTemplate);

			File imageHeader = new File(Costanti.PATH_HEADER_IMAGE+"/Logo ECI Srl.jpg");
								
			BufferedImage in = ImageIO.read(imageHeader);		
			report.addParameter("logo",rotateClockwise90(in));
			report.addParameter("codiceInterno",campione.getCodice());
			report.addParameter("matricola",campione.getMatricola());
			if(campione.getDataVerifica()!=null) {
			
			report.addParameter("dataVerifica",sdf.format(campione.getDataVerifica()));
			}else {
				report.addParameter("dataVerifica","");	
			}

			if(campione.getDataScadenza()!=null) {
			report.addParameter("dataProVerifica",sdf.format(campione.getDataScadenza()));
			}
			else 
			{
			report.addParameter("dataProVerifica","");	
			}

			report.setDataSource(new JREmptyDataSource());

			// java.io.File file = new java.io.File(Costanti.PATH_FOLDER+"//"+intervento.getNome_pack()+"//SchedaDiConsegna.pdf");
			java.io.File file = new java.io.File(Costanti.PATH_ROOT+"//Campioni/ET_"+campione.getId()+".pdf");
			FileOutputStream fos = new FileOutputStream(file);
			report.toPdf(fos);

			fos.flush();
			fos.close();


			File d = new File(Costanti.PATH_ROOT+"//Campioni/ET_"+campione.getId()+".pdf");

			FileInputStream fileIn = new FileInputStream(d);

			response.setContentType("application/pdf");

			//	 response.setHeader("Content-Disposition","attachment;filename="+filename);

			ServletOutputStream outp = response.getOutputStream();

			byte[] outputByte = new byte[1];

			while(fileIn.read(outputByte, 0, 1) != -1)
			{
				outp.write(outputByte, 0, 1);
			}

			session.close();
			fileIn.close();

			outp.flush();
			outp.close();
			}
		}
		catch(Exception ex)
		{

			PrintWriter  out = response.getWriter();
			if(ajax) {
				ex.printStackTrace();
				session.getTransaction().rollback();
				session.close();
				request.getSession().setAttribute("exception", ex);
				myObj = ECIException.callExceptionJsonObject(ex);
				out.print(myObj);
			}else {


				ex.printStackTrace();
				session.getTransaction().rollback();
				session.close();

				request.setAttribute("error",ECIException.callException(ex));
				request.getSession().setAttribute("exception", ex);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/site/error.jsp");
				dispatcher.forward(request,response);
			}
		}  
	}
	
	public static BufferedImage rotateClockwise90(BufferedImage src) {
	    int width = src.getWidth();
	    int height = src.getHeight();

	    BufferedImage dest = new BufferedImage(height, width, src.getType());

	    Graphics2D graphics2D = dest.createGraphics();
	    graphics2D.translate((height - width) / 2, (height - width) / 2);
	    graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
	    graphics2D.drawRenderedImage(src, null);

	    return dest;
	}
}
	
