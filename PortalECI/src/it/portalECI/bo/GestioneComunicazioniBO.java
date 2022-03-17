package it.portalECI.bo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;


import org.hibernate.Session;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

import it.portalECI.DAO.GestioneCommesseDAO;
import it.portalECI.DAO.GestioneComunicazioniDAO;
import it.portalECI.DAO.GestioneDocumentoDAO;
import it.portalECI.DTO.AttrezzaturaDTO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.DocumentoDTO;
import it.portalECI.DTO.InterventoDTO;
import it.portalECI.DTO.TipoComunicazioneUtenteDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;
import it.portalECI.Util.Costanti;


public class GestioneComunicazioniBO {

	public static ArrayList<TipoComunicazioneUtenteDTO> getListaTipoComunicazione(Session session) {
		
		return GestioneComunicazioniDAO.getListaTipoComunicazione(session);
	}

	public static TipoComunicazioneUtenteDTO getComunicazioneFromId(int id_comunicazione,Session session) {
		
		return GestioneComunicazioniDAO.getComunicazioneFromId(id_comunicazione, session);
	}

public static void sendEmailVerbale(VerbaleDTO verbale, String id_commessa, String mailTo, String from, int stato, String tipo_verbale, VerbaleDTO verbale_origine) throws Exception {
				
		

		  HtmlEmail email = new HtmlEmail();
		  email.setHostName("smtps.aruba.it");
  		 //email.setDebug(true);


		  email.setAuthentication("info@ecisrl.it", Costanti.PASS_EMAIL);

	        email.getMailSession().getProperties().put("mail.smtp.auth", "true");
	        email.getMailSession().getProperties().put("mail.debug", "true");
	        email.getMailSession().getProperties().put("mail.smtp.port", "465");
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.port", "465");
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.fallback", "false");
	        email.getMailSession().getProperties().put("mail.smtp.ssl.enable", "true");

	
		  String[] to = mailTo.split(";");
			
		  for (String string : to) {
			  email.addTo(string);
		  }
		  
		 
		  CommessaDTO commessa = GestioneCommesseBO.getCommessaById(id_commessa);
		  
		  if(tipo_verbale.equals(VerbaleDTO.SK_TEC)){
			  
			  if(stato==4) {
					
				  email.setSubject("Richiesta di approvazione scheda tecnica verbale ID n. "+verbale_origine.getId()+" - Cliente: "+commessa.getID_ANAGEN_NOME());
				  
				  email.setHtmlMsg("<html>Si richiede l'approvazione della scheda tecnica del verbale di verifica in oggetto. <br />  <br /><br /> "
				  		+ "<em><b style='color:#9d201d' >ECI Ente di Certificazione & Ispezione Srl</em></b><br><br><span style='color:#204d74'>Via Tofaro 42, B - 03039 Sora (FR)<br>  " + 
				  		"			  		<em>Tel + 39 0776.18151 - Fax+ 39 0776.814169 <br> " + 
				  		"			  		Mail: </em>info@ecisrl.it<br> \r\n" + 
				  		"			  		<em>Web: </em>http://www.ecisrl.it<br></span>"
				  		+ "<br><br>"
				  		+ "<font size='1' style='color:#204d74'><br><br>In ottemperanza al D.L. n. 196 del 30/6/2003 e Reg. UE n.2016/679 (GDPR) in materia di protezione dei dati personali, le informazioni contenute in questo messaggio sono strettamente confidenziali e riservate ed esclusivamente indirizzate al destinatario indicato (oppure alla persona responsabile di rimetterlo al destinatario). " + 
				  		"Vogliate tener presente che qualsiasi uso, riproduzione o divulgazione di questo messaggio è vietato. Nel caso in cui aveste ricevuto questo messaggio per errore, vogliate cortesemente avvertire il mittente e distruggere il presente messaggio.<br>" +
				  		"			  		<br>According to Italian law D.L. 196/2003 and Reg. UE n.2016/679 (GDPR)  concerning privacy, if you are not the addressee (or responsible for delivery of the message to such person) you are hereby notified that any disclosure, reproduction, distribution or other dissemination or use of this communication is strictly prohibited. If you have received this message in error, please destroy it and notify us by email." + 
				  		"			  		</font></html>");
			  }
			  else if(stato == 5) {
				  
				  email.setSubject("Approvazione scheda tecnica verbale ID n. "+verbale_origine.getId()+" - Cliente: "+commessa.getID_ANAGEN_NOME());
				  
				  email.setHtmlMsg("<html>Si comunica che, a seguito dell'avvenuto riesame della documentazione prodotta durante l'ispezione effettuata, si approva la scheda tecnica del verbale di verifica in oggetto. <br />  <br /><br /> "
				  		+ "<em><b style='color:#9d201d' >ECI Ente di Certificazione & Ispezione Srl</em></b><br><br><span style='color:#204d74'>Via Tofaro 42, B - 03039 Sora (FR)<br>  " + 
				  		"			  		<em>Tel + 39 0776.18151 - Fax+ 39 0776.814169 <br> " + 
				  		"			  		Mail: </em>info@ecisrl.it<br> \r\n" + 
				  		"			  		<em>Web: </em>http://www.ecisrl.it<br></span>"
				  		+ "<br><br>"
				  		+ "<font size='1' style='color:#204d74'><br><br>In ottemperanza al D.L. n. 196 del 30/6/2003 e Reg. UE n.2016/679 (GDPR) in materia di protezione dei dati personali, le informazioni contenute in questo messaggio sono strettamente confidenziali e riservate ed esclusivamente indirizzate al destinatario indicato (oppure alla persona responsabile di rimetterlo al destinatario). " + 
				  		"Vogliate tener presente che qualsiasi uso, riproduzione o divulgazione di questo messaggio è vietato. Nel caso in cui aveste ricevuto questo messaggio per errore, vogliate cortesemente avvertire il mittente e distruggere il presente messaggio.<br>" +
				  		"			  		<br>According to Italian law D.L. 196/2003 and Reg. UE n.2016/679 (GDPR)  concerning privacy, if you are not the addressee (or responsible for delivery of the message to such person) you are hereby notified that any disclosure, reproduction, distribution or other dissemination or use of this communication is strictly prohibited. If you have received this message in error, please destroy it and notify us by email." + 
				  		"			  		</font></html>");
			  }else if(stato == 6) {
				  email.setSubject("Rifiuto scheda tecnica verbale ID n. "+verbale_origine.getId()+" - Cliente: "+commessa.getID_ANAGEN_NOME());
				  
				  email.setHtmlMsg("<html>Si comunica l'esito negativo del riesame della documentazione prodotta durante l'ispezione effettuata; la scheda tecnica del verbale di verifica in oggetto viene rifiutata. <br />  <br /><br /> "
				  		+ "<em><b style='color:#9d201d' >ECI Ente di Certificazione & Ispezione Srl</em></b><br><br><span style='color:#204d74'>Via Tofaro 42, B - 03039 Sora (FR)<br>  " + 
				  		"			  		<em>Tel + 39 0776.18151 - Fax+ 39 0776.814169 <br> " + 
				  		"			  		Mail: </em>info@ecisrl.it<br> \r\n" + 
				  		"			  		<em>Web: </em>http://www.ecisrl.it<br></span>"
				  		+ "<br><br>"
				  		+ "<font size='1' style='color:#204d74'><br><br>In ottemperanza al D.L. n. 196 del 30/6/2003 e Reg. UE n.2016/679 (GDPR) in materia di protezione dei dati personali, le informazioni contenute in questo messaggio sono strettamente confidenziali e riservate ed esclusivamente indirizzate al destinatario indicato (oppure alla persona responsabile di rimetterlo al destinatario). " + 
				  		"Vogliate tener presente che qualsiasi uso, riproduzione o divulgazione di questo messaggio è vietato. Nel caso in cui aveste ricevuto questo messaggio per errore, vogliate cortesemente avvertire il mittente e distruggere il presente messaggio.<br>" +
				  		"			  		<br>According to Italian law D.L. 196/2003 and Reg. UE n.2016/679 (GDPR)  concerning privacy, if you are not the addressee (or responsible for delivery of the message to such person) you are hereby notified that any disclosure, reproduction, distribution or other dissemination or use of this communication is strictly prohibited. If you have received this message in error, please destroy it and notify us by email." + 
				  		"			  		</font></html>");
			  }
			  
			  
		  }else {
			  if(stato==4) {
					
				  email.setSubject("Richiesta di approvazione verbale ID n. "+verbale.getId()+" - Cliente: "+commessa.getID_ANAGEN_NOME());
				  
				  email.setHtmlMsg("<html>Si richiede l'approvazione del verbale di verifica in oggetto. <br />  <br /><br /> "
				  		+ "<em><b style='color:#9d201d' >ECI Ente di Certificazione & Ispezione Srl</em></b><br><br><span style='color:#204d74'>Via Tofaro 42, B - 03039 Sora (FR)<br>  " + 
				  		"			  		<em>Tel + 39 0776.18151 - Fax+ 39 0776.814169 <br> " + 
				  		"			  		Mail: </em>info@ecisrl.it<br> \r\n" + 
				  		"			  		<em>Web: </em>http://www.ecisrl.it<br></span>"
				  		+ "<br><br>"
				  		+ "<font size='1' style='color:#204d74'><br><br>In ottemperanza al D.L. n. 196 del 30/6/2003 e Reg. UE n.2016/679 (GDPR) in materia di protezione dei dati personali, le informazioni contenute in questo messaggio sono strettamente confidenziali e riservate ed esclusivamente indirizzate al destinatario indicato (oppure alla persona responsabile di rimetterlo al destinatario). " + 
				  		"Vogliate tener presente che qualsiasi uso, riproduzione o divulgazione di questo messaggio è vietato. Nel caso in cui aveste ricevuto questo messaggio per errore, vogliate cortesemente avvertire il mittente e distruggere il presente messaggio.<br>" +
				  		"			  		<br>According to Italian law D.L. 196/2003 and Reg. UE n.2016/679 (GDPR)  concerning privacy, if you are not the addressee (or responsible for delivery of the message to such person) you are hereby notified that any disclosure, reproduction, distribution or other dissemination or use of this communication is strictly prohibited. If you have received this message in error, please destroy it and notify us by email." + 
				  		"			  		</font></html>");
			  }
			  else if(stato == 5) {
				  
				  email.setSubject("Approvazione verbale n. "+verbale.getNumeroVerbale()+" - Cliente: "+commessa.getID_ANAGEN_NOME());
				  
				  email.setHtmlMsg("<html>Si comunica che, a seguito dell'avvenuto riesame della documentazione prodotta durante l'ispezione effettuata, si approva il verbale di verifica in oggetto. <br />  <br /><br /> "
				  		+ "<em><b style='color:#9d201d' >ECI Ente di Certificazione & Ispezione Srl</em></b><br><br><span style='color:#204d74'>Via Tofaro 42, B - 03039 Sora (FR)<br>  " + 
				  		"			  		<em>Tel + 39 0776.18151 - Fax+ 39 0776.814169 <br> " + 
				  		"			  		Mail: </em>info@ecisrl.it<br> \r\n" + 
				  		"			  		<em>Web: </em>http://www.ecisrl.it<br></span>"
				  		+ "<br><br>"
				  		+ "<font size='1' style='color:#204d74'><br><br>In ottemperanza al D.L. n. 196 del 30/6/2003 e Reg. UE n.2016/679 (GDPR) in materia di protezione dei dati personali, le informazioni contenute in questo messaggio sono strettamente confidenziali e riservate ed esclusivamente indirizzate al destinatario indicato (oppure alla persona responsabile di rimetterlo al destinatario). " + 
				  		"Vogliate tener presente che qualsiasi uso, riproduzione o divulgazione di questo messaggio è vietato. Nel caso in cui aveste ricevuto questo messaggio per errore, vogliate cortesemente avvertire il mittente e distruggere il presente messaggio.<br>" +
				  		"			  		<br>According to Italian law D.L. 196/2003 and Reg. UE n.2016/679 (GDPR)  concerning privacy, if you are not the addressee (or responsible for delivery of the message to such person) you are hereby notified that any disclosure, reproduction, distribution or other dissemination or use of this communication is strictly prohibited. If you have received this message in error, please destroy it and notify us by email." + 
				  		"			  		</font></html>");
			  }else if(stato == 6) {
				  email.setSubject("Rifiuto verbale ID n. "+verbale.getId()+" - Cliente: "+commessa.getID_ANAGEN_NOME());
				  
				  email.setHtmlMsg("<html>Si comunica l'esito negativo del riesame della documentazione prodotta durante l'ispezione effettuata; il verbale di verifica in oggetto viene rifiutato. <br />  <br /><br /> "
				  		+ "<em><b style='color:#9d201d' >ECI Ente di Certificazione & Ispezione Srl</em></b><br><br><span style='color:#204d74'>Via Tofaro 42, B - 03039 Sora (FR)<br>  " + 
				  		"			  		<em>Tel + 39 0776.18151 - Fax+ 39 0776.814169 <br> " + 
				  		"			  		Mail: </em>info@ecisrl.it<br> \r\n" + 
				  		"			  		<em>Web: </em>http://www.ecisrl.it<br></span>"
				  		+ "<br><br>"
				  		+ "<font size='1' style='color:#204d74'><br><br>In ottemperanza al D.L. n. 196 del 30/6/2003 e Reg. UE n.2016/679 (GDPR) in materia di protezione dei dati personali, le informazioni contenute in questo messaggio sono strettamente confidenziali e riservate ed esclusivamente indirizzate al destinatario indicato (oppure alla persona responsabile di rimetterlo al destinatario). " + 
				  		"Vogliate tener presente che qualsiasi uso, riproduzione o divulgazione di questo messaggio è vietato. Nel caso in cui aveste ricevuto questo messaggio per errore, vogliate cortesemente avvertire il mittente e distruggere il presente messaggio.<br>" +
				  		"			  		<br>According to Italian law D.L. 196/2003 and Reg. UE n.2016/679 (GDPR)  concerning privacy, if you are not the addressee (or responsible for delivery of the message to such person) you are hereby notified that any disclosure, reproduction, distribution or other dissemination or use of this communication is strictly prohibited. If you have received this message in error, please destroy it and notify us by email." + 
				  		"			  		</font></html>");
			  }
		  }
		  
		  		 
		  email.setFrom("info@ecisrl.it", "info@ecisrl.it");
		  
		  // embed the image and get the content id

//		  File image = new File(ctx.getRealPath("images/logo_calver_v2.png"));
//		  String cid = email.embed(image, "Calver logo");

		  
		  // send the email
		  email.send();
 	}

public static ArrayList<UtenteDTO> getListaUtentiComunicazione(String codiceCategoria, Session session) {
	
	return GestioneComunicazioniDAO.getListaUtentiComunicazione(codiceCategoria, session);
}


public static void sendPecVerbale(ArrayList<VerbaleDTO> lista_verbali, String mailTo) throws MessagingException {
	
	   String from = "verifiche@pec.ecisrl.it";
	   String SMTP_HOST_NAME = "smtps.pec.aruba.it";
	   int SMTP_HOST_PORT = 465;
	   String SMTP_AUTH_USER = "verifiche@pec.ecisrl.it";
	 
	   String SMTP_AUTH_PWD  = Costanti.PASS_PEC;
	   
	   Properties props = new Properties();

    props.put("mail.transport.protocol", "smtps");
    props.put("mail.smtps.host", SMTP_HOST_NAME);
    props.put("mail.smtps.auth", true);    
    props.put("mail.smtps.port", 465);      
    props.put("mail.smtps.auth",true);
    props.put("mail.smtps.user","verifiche@pec.ecisrl.it");
    props.put("mail.debug", "true");
    props.put("mail.smtps.port", 465);
    props.put("mail.smtps.socketFactory.port", 465);
    props.put("mail.smtps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtps.socketFactory.fallback", "false");
    props.put("mail.smtps.ssl.enable", true);
    props.put("mail.smtps.ssl.protocols", "TLSv1.2");
    
    
    javax.mail.Session mailSession =  javax.mail.Session.getDefaultInstance(props);       
    
 
    MimeMessage message = new MimeMessage(mailSession); 
		
		// header field of the header. 
		message.setFrom(new InternetAddress(from)); 	
		
		InternetAddress[] address = InternetAddress.parse(mailTo.trim().replace(";", ","));
		
		message.addRecipients(Message.RecipientType.TO, address); 
		
		message.setSubject("Trasmissione verbali "); 
			
		StringBuffer msg = new StringBuffer();
		
		  msg.append("<html><body>");
		
		if(lista_verbali.get(0).getCodiceCategoria().equals("VIE")) {
			  msg.append("<html>Gentile Cliente, <br /> " + 
				  		"Inviamo in allegato i Verbali attestanti l'avvenuta verifica dei Vs. impianti  <br /> " + 
				  		"elettrici ai sensi del D.P.R. 462/01.<br /> " + 		
				  		"<br />Con l'occasione Vi ricordiamo che tale documentazione deve essere conservata <br>" + 
				  		"per tutto il periodo di validit&agrave; della verifica ed esibita a richiesta degli Organi di vigilanza. <br><br>" + 
				  		"Si specifica che non saranno inviate copie cartacee. <br><br>"+
				  		"Restiamo a disposizione per qualsiasi chiarimento in merito. <br>"+
				  		"Distinti saluti. "+
				  		"  <br /> <br />"
				  		+"<em><b>Segreteria Tecnica-Commerciale</b></em> <br>"
				  		+ "<em><b>E.C.I. Ente di Certificazione & Ispezione Srl <br>" + 
				  		"Organismo di Ispezione di Tipo A n. ISP 322E" + 
				  		"</b><br>Via Tofaro 42, B - 03039 Sora (FR)</em><br><br>" + 
				  		"<em>Tel + 39 0776.18151 - Fax+ 39 0776.814169 <br> "
				  		+ "Mail: </em>segreteria@ecisrl.it<br>" + 
				  		 "<em>Pec: </em>verifiche@pec.ecisrl.it<br>" + 
				  		"<em>Web: </em>http://www.ecisrl.it<br>" + 
				  		"<br/></html>");
		}else {
			  msg.append("<html>Gentile Cliente, <br /> " + 
				  		"Inviamo in allegato i Verbali e le Schede Tecniche attestanti l'avvenuta verifica delle Vs. attrezzature  <br /> " + 
				  		"di lavoro ai sensi del D.M. 11/04/2011.<br /> " + 		
				  		"<br />Con l'occasione Vi ricordiamo che tale documentazione deve essere conservata <br>" + 
				  		"per tutto il periodo di validit&agrave; della verifica ed esibita a richiesta degli Organi di vigilanza. <br><br>" +
				  		"Si specifica che non saranno inviate copie cartacee. <br><br>"+
				  		"In attesa di ricevere copia controfirmata, restiamo a disposizione per qualsiasi chiarimento in merito. <br>"+
				  		"Distinti saluti. "+
				  		"  <br /> <br />"
				  		+"<em><b>Segreteria Tecnica-Commerciale</b></em> <br>"
				  		+ "<em><b>E.C.I. Ente di Certificazione & Ispezione Srl <br>" + 
				  		"Soggetto Abilitato ai sensi dei D.M. 11/04/2011" + 
				  		"</b><br>Via Tofaro 42, B - 03039 Sora (FR)</em><br><br>" + 
				  		"<em>Tel + 39 0776.18151 - Fax+ 39 0776.814169 <br> "
				  		+ "Mail: </em>segreteria@ecisrl.it<br>" + 
				  		 "<em>Pec: </em>verifiche@pec.ecisrl.it<br>" + 
				  		"<em>Web: </em>http://www.ecisrl.it<br>" + 
				  		"<br/></html>");
		}

		  
			//  msg.append("<img width='350' src=cid:").append(message.embed(img)).append(">");
			  msg.append("<a href='www.ecisrl.it'><img width='350' src=\"cid:image1\"></a>");
		
			  msg.append("</body><font size='1'><br><br>In ottemperanza al D.L. n. 196 del 30/6/2003 e Reg. UE n.2016/679 (GDPR) in materia di protezione dei dati personali, le informazioni contenute in questo messaggio sono strettamente confidenziali e riservate ed esclusivamente indirizzate al destinatario indicato (oppure alla persona responsabile di rimetterlo al destinatario). \r\n" + 
			  		"Vogliate tener presente che qualsiasi uso, riproduzione o divulgazione di questo messaggio è vietato. Nel caso in cui aveste ricevuto questo messaggio per errore, vogliate cortesemente avvertire il mittente e distruggere il presente messaggio.\r\n" + 
			  		"<br><br>" + 
			  		"According to Italian law D.L. 196/2003 and Reg. UE n.2016/679 (GDPR)  concerning privacy, if you are not the addressee (or responsible for delivery of the message to such person) you are hereby notified that any disclosure, reproduction, distribution or other dissemination or use of this communication is strictly prohibited. If you have received this message in error, please destroy it and notify us by email." + 
			  		"</font></html>");
		

		  BodyPart messageBodyPart = new MimeBodyPart();
		  messageBodyPart.setContent(msg.toString(),"text/html");
		  
		
		 		  
		  BodyPart image = new MimeBodyPart();
		  BodyPart image_ann = new MimeBodyPart();
		 // DataSource fds = new FileDataSource(Costanti.PATH_FOLDER_LOGHI +"logo_sti.png");
		  DataSource fds = new FileDataSource(Costanti.PATH_HEADER_IMAGE +"Logo ECI Srl.jpg");

		  image.setDataHandler(new DataHandler(fds));
		  image.setHeader("Content-ID", "<image1>");
		  
			  
		  Multipart multipart = new MimeMultipart();
		  multipart.addBodyPart(messageBodyPart);
			
			//String filenameP7m = verbale.getNumeroVerbale()+".pdf.p7m.p7m";			
			// Create the attachment
			
			for (VerbaleDTO verbale  : lista_verbali) {
				
				  BodyPart attachPdf = new MimeBodyPart();
				  
				  
				String filenamePdf = "_CF.pdf";
				String filenamePdfScheda = null;
				String pathScheda = null;
				
				
				if(verbale.getControfirmato()==0) {
					filenamePdf = "_F.pdf";
				}
				
				
				for (DocumentoDTO doc : verbale.getDocumentiVerbale()) {
					if(!doc.getInvalid() && doc.getType().equals("CERTIFICATO")) {
						DataSource source = new FileDataSource(Costanti.PATH_CERTIFICATI+doc.getFilePath().replace(".pdf", filenamePdf));
				        attachPdf.setDataHandler(new DataHandler(source));
				        attachPdf.setFileName(verbale.getNumeroVerbale()+".pdf");					        
				        
				        multipart.addBodyPart(attachPdf);
					}
					else if(doc.getType().equals("ALLEGATO") && doc.getAllegato_inviabile()==1) {
						BodyPart attachAllegato = new MimeBodyPart();
						DataSource source = new FileDataSource(Costanti.PATH_CERTIFICATI+doc.getFilePath());
						attachAllegato.setDataHandler(new DataHandler(source));
						attachAllegato.setFileName(doc.getFileName());
						
						multipart.addBodyPart(attachAllegato);
					}
				}
				
				if(verbale.getSchedaTecnica()!=null) {
					BodyPart attachSchedaTecnica = new MimeBodyPart();
					Set<DocumentoDTO> lista_doc= verbale.getSchedaTecnica().getDocumentiVerbale();
						
					Iterator<DocumentoDTO> doc = lista_doc.iterator();
					
					while(doc.hasNext()) {
						DocumentoDTO d = doc.next();
						if(!d.getInvalid()) {
							//filenamePdfScheda = d.getFileName().substring(0,d.getFileName().length()-4)+"_F.pdf";
							filenamePdfScheda = d.getFileName();
							pathScheda = Costanti.PATH_CERTIFICATI+d.getFilePath().replace(".pdf","_F.pdf");
						}
					}
					
					DataSource source = new FileDataSource(pathScheda);
				    attachSchedaTecnica.setDataHandler(new DataHandler(source));
				    attachSchedaTecnica.setFileName(filenamePdfScheda);
				    multipart.addBodyPart(attachSchedaTecnica);	
				}
			}	                  
	        
	        
			
	  
	         multipart.addBodyPart(image);
	         // Send the complete message parts
	         message.setContent(multipart);
   
	
	         Transport tr = mailSession.getTransport("smtps");
	         
	         tr.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
	         message.saveChanges();      // don't forget this
	         tr.sendMessage(message, message.getAllRecipients());
	         tr.close();
	

		
	}





//public static void sendPecVerbale(DocumentoDTO documento, VerbaleDTO verbale, String mailTo) throws MessagingException {
//	
//	   String from = "verifiche@pec.ecisrl.it";
//	   String SMTP_HOST_NAME = "smtps.pec.aruba.it";
//	   int SMTP_HOST_PORT = 465;
//	   String SMTP_AUTH_USER = "verifiche@pec.ecisrl.it";
//	   //String SMTP_AUTH_PWD  = "8pfSu3sYx+";
//	   String SMTP_AUTH_PWD  = "&w&K3vQ!HQY%@vV&";
//	   
//	   Properties props = new Properties();
//
//       props.put("mail.transport.protocol", "smtps");
//       props.put("mail.smtps.host", SMTP_HOST_NAME);
//       props.put("mail.smtps.auth", true);    
//       props.put("mail.smtps.port", 465);      
//       props.put("mail.smtps.auth",true);
//       props.put("mail.smtps.user","verifiche@pec.ecisrl.it");
//       props.put("mail.debug", "true");
//       props.put("mail.smtps.port", 465);
//       props.put("mail.smtps.socketFactory.port", 465);
//       props.put("mail.smtps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//       props.put("mail.smtps.socketFactory.fallback", "false");
//       props.put("mail.smtps.ssl.enable", true);
//       props.put("mail.smtps.ssl.protocols", "TLSv1.2");
//       
//       
//       javax.mail.Session mailSession =  javax.mail.Session.getDefaultInstance(props);       
//       
//    
//       MimeMessage message = new MimeMessage(mailSession); 
//		
//		// header field of the header. 
//		message.setFrom(new InternetAddress(from)); 	
//		
//		InternetAddress[] address = InternetAddress.parse(mailTo.trim().replace(";", ","));
//		
//		message.addRecipients(Message.RecipientType.TO, address); 
//		
//		message.setSubject("Trasmissione verbale "+verbale.getNumeroVerbale()); 
//			
//		StringBuffer msg = new StringBuffer();
//		
//		  msg.append("<html><body>");
//		
//		  if(verbale.getSchedaTecnica()!=null) {
//			  
//			  msg.append("<html>Gentile Cliente, <br /> " + 
//				  		"Inviamo in allegato il Verbale e la Scheda Tecnica attestanti l'avvenuta verifica del Vs. impianto  <br /> " + 
//				  		"elettrico ai sensi del D.P.R. 462/01.<br /> " + 		
//				  		"<br />Con l'occasione Vi ricordiamo che tale documentazione deve essere conservata <br>" + 
//				  		"per tutto il periodo di validit&agrave; della verifica ed esibita a richiesta degli Organi di vigilanza. <br>" + 
//				  		"Restiamo a disposizione per qualsiasi chiarimento in merito. <br>"+
//				  		"Distinti saluti. "+
//				  		"  <br /> <br />"
//				  		+"<em><b>Segreteria Tecnica-Commerciale</b></em> <br>"
//				  		+ "<em><b>E.C.I. Ente di Certificazione & Ispezione Srl <br>" + 
//				  		"Organismo di Ispezione di Tipo A n. ISP 322E" + 
//				  		"</b><br>Via Tofaro 42, B - 03039 Sora (FR)</em><br><br>" + 
//				  		"<em>Tel + 39 0776.18151 - Fax+ 39 0776.814169 <br> "
//				  		+ "Mail: </em>segreteria@ecisrl.it<br>" + 
//				  		 "<em>Pec: </em>verifiche@pec.ecisrl.it<br>" + 
//				  		"<em>Web: </em>http://www.ecisrl.it<br>" + 
//				  		"<br/></html>");
//			  
//		  }else {
//			  
//			  msg.append("<html>Gentile Cliente, <br /> " + 
//				  		"Inviamo in allegato il Verbale attestante l'avvenuta verifica del Vs. impianto  <br /> " + 
//				  		"elettrico ai sensi del D.P.R. 462/01.<br /> " + 		
//				  		"<br />Con l'occasione Vi ricordiamo che tale documentazione deve essere conservata <br>" + 
//				  		"per tutto il periodo di validit&agrave; della verifica ed esibita a richiesta degli Organi di vigilanza. <br>" + 
//				  		"Restiamo a disposizione per qualsiasi chiarimento in merito. <br>"+
//				  		"Distinti saluti. "+
//				  		"  <br /> <br />"
//				  		+"<em><b>Segreteria Tecnica-Commerciale</b></em> <br>"
//				  		+ "<em><b>E.C.I. Ente di Certificazione & Ispezione Srl <br>" + 
//				  		"Organismo di Ispezione di Tipo A n. ISP 322E" + 
//				  		"</b><br>Via Tofaro 42, B - 03039 Sora (FR)</em><br><br>" + 
//				  		"<em>Tel + 39 0776.18151 - Fax+ 39 0776.814169 <br> "
//				  		+ "Mail: </em>segreteria@ecisrl.it<br>" + 
//				  		 "<em>Pec: </em>verifiche@pec.ecisrl.it<br>" + 
//				  		"<em>Web: </em>http://www.ecisrl.it<br>" + 
//				  		"<br/></html>");
//		  }
//			 
//			//  msg.append("<img width='350' src=cid:").append(message.embed(img)).append(">");
//			  msg.append("<a href='www.ecisrl.it'><img width='350' src=\"cid:image1\"></a>");
//		
//			  msg.append("</body><font size='1'><br><br>In ottemperanza al D.L. n. 196 del 30/6/2003 e Reg. UE n.2016/679 (GDPR) in materia di protezione dei dati personali, le informazioni contenute in questo messaggio sono strettamente confidenziali e riservate ed esclusivamente indirizzate al destinatario indicato (oppure alla persona responsabile di rimetterlo al destinatario). \r\n" + 
//			  		"Vogliate tener presente che qualsiasi uso, riproduzione o divulgazione di questo messaggio è vietato. Nel caso in cui aveste ricevuto questo messaggio per errore, vogliate cortesemente avvertire il mittente e distruggere il presente messaggio.\r\n" + 
//			  		"<br><br>" + 
//			  		"According to Italian law D.L. 196/2003 and Reg. UE n.2016/679 (GDPR)  concerning privacy, if you are not the addressee (or responsible for delivery of the message to such person) you are hereby notified that any disclosure, reproduction, distribution or other dissemination or use of this communication is strictly prohibited. If you have received this message in error, please destroy it and notify us by email." + 
//			  		"</font></html>");
//		
//
//		  BodyPart messageBodyPart = new MimeBodyPart();
//		  messageBodyPart.setContent(msg.toString(),"text/html");
//		  
//		  BodyPart attachPdf = new MimeBodyPart();
//		  BodyPart attachSchedaTecnica = new MimeBodyPart();
//		 		  
//		  BodyPart image = new MimeBodyPart();
//		  BodyPart image_ann = new MimeBodyPart();
//		 // DataSource fds = new FileDataSource(Costanti.PATH_FOLDER_LOGHI +"logo_sti.png");
//		  DataSource fds = new FileDataSource(Costanti.PATH_HEADER_IMAGE +"Logo ECI Srl.jpg");
//
//		  image.setDataHandler(new DataHandler(fds));
//		  image.setHeader("Content-ID", "<image1>");
//		  
//			  
//		  Multipart multipart = new MimeMultipart();
//		  
//			String filenamePdf = verbale.getNumeroVerbale()+"_CF.pdf";
//			String filenamePdfScheda = null;
//			String pathScheda = null;
//			
//			
//			if(verbale.getControfirmato()==0) {
//				filenamePdf = verbale.getNumeroVerbale()+"_F.pdf";
//			}
//			
//			
//			//String filenameP7m = verbale.getNumeroVerbale()+".pdf.p7m.p7m";			
//			// Create the attachment
//
//	         DataSource source = new FileDataSource(Costanti.PATH_CERTIFICATI+documento.getFilePath().replace(documento.getFileName(), filenamePdf));
//	         attachPdf.setDataHandler(new DataHandler(source));
//	         attachPdf.setFileName(verbale.getNumeroVerbale()+".pdf");	   	         
//	         multipart.addBodyPart(messageBodyPart);
//	         multipart.addBodyPart(attachPdf);
//	        
//			if(verbale.getSchedaTecnica()!=null) {
//				Set<DocumentoDTO> lista_doc= verbale.getSchedaTecnica().getDocumentiVerbale();
//					
//				Iterator<DocumentoDTO> doc = lista_doc.iterator();
//				while(doc.hasNext()) {
//					DocumentoDTO d = doc.next();
//					if(!d.getInvalid()) {
//						filenamePdfScheda = d.getFileName().substring(0,d.getFileName().length()-4)+"_F.pdf";
//						pathScheda = Costanti.PATH_CERTIFICATI+d.getFilePath().replace(d.getFileName(), filenamePdfScheda);
//					}
//				}
//					
//				source = new FileDataSource(pathScheda);
//			    attachSchedaTecnica.setDataHandler(new DataHandler(source));
//			    attachSchedaTecnica.setFileName(filenamePdfScheda.replace("_F.pdf", ".pdf"));
//			    multipart.addBodyPart(attachSchedaTecnica);	
//			}
//	  
//	         multipart.addBodyPart(image);
//	         // Send the complete message parts
//	         message.setContent(multipart);
//      
//	
//	         Transport tr = mailSession.getTransport("smtps");
//	         
//	         tr.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
//	         message.saveChanges();      // don't forget this
//	         tr.sendMessage(message, message.getAllRecipients());
//	         tr.close();
//	
//
//		
//	}

	public static void sendEmailInfoVerbale(VerbaleDTO verbale, String nome, String cognome, String motivazione, String indirizzo_email) throws Exception {
	

		  HtmlEmail email = new HtmlEmail();
		  email.setHostName("smtps.aruba.it");
		 //email.setDebug(true);

	
		  email.setAuthentication("info@ecisrl.it", Costanti.PASS_EMAIL);

	        email.getMailSession().getProperties().put("mail.smtp.auth", "true");
	        email.getMailSession().getProperties().put("mail.debug", "true");
	        email.getMailSession().getProperties().put("mail.smtp.port", "465");
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.port", "465");
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.fallback", "false");
	        email.getMailSession().getProperties().put("mail.smtp.ssl.enable", "true");

	
			email.addTo("info@ecisrl.it");
	       
		 
		    CommessaDTO commessa = GestioneCommesseDAO.getCommessaById(verbale.getIntervento().getIdCommessa()+"");
		 
		    email.setSubject("Richiesta copia Verbale ID n. "+verbale.getId()+ " - Cliente: "+commessa.getID_ANAGEN_NOME());
				  
		    email.setHtmlMsg("<html>Si richiede la copia del verbale di verifica in oggetto. <br /> "
		    		+ "Dati richiedente:"
		    		+ " <br />"
		    		+ "Nome: "+nome+"<br>"
		    		+ "Cognome: "+cognome+"<br>"
		    		+ "Motivazione: "+motivazione+"<br>"
		    		+ "Indirizzo email: "+indirizzo_email+"<br>"
		    		
		    		+ "<br /> <br><br>"
				  		+ "<em><b style='color:#9d201d' >ECI Ente di Certificazione & Ispezione Srl</em></b><br><br><span style='color:#204d74'>Via Tofaro 42, B - 03039 Sora (FR)<br>  " + 
				  		"			  		<em>Tel + 39 0776.18151 - Fax+ 39 0776.814169 <br> " + 
				  		"			  		Mail: </em>info@ecisrl.it<br> \r\n" + 
				  		"			  		<em>Web: </em>http://www.ecisrl.it<br></span>"
				  		+ "<br><br>"
				  		+ "<font size='1' style='color:#204d74'><br><br>In ottemperanza al D.L. n. 196 del 30/6/2003 e Reg. UE n.2016/679 (GDPR) in materia di protezione dei dati personali, le informazioni contenute in questo messaggio sono strettamente confidenziali e riservate ed esclusivamente indirizzate al destinatario indicato (oppure alla persona responsabile di rimetterlo al destinatario). " + 
				  		"Vogliate tener presente che qualsiasi uso, riproduzione o divulgazione di questo messaggio è vietato. Nel caso in cui aveste ricevuto questo messaggio per errore, vogliate cortesemente avvertire il mittente e distruggere il presente messaggio.<br>" +
				  		"			  		<br>According to Italian law D.L. 196/2003 and Reg. UE n.2016/679 (GDPR)  concerning privacy, if you are not the addressee (or responsible for delivery of the message to such person) you are hereby notified that any disclosure, reproduction, distribution or other dissemination or use of this communication is strictly prohibited. If you have received this message in error, please destroy it and notify us by email." + 
				  		"			  		</font></html>");
			 
		  
		  		 
		  email.setFrom("info@ecisrl.it", "info@ecisrl.it");

		  email.send();
	}

	public static void sendEmail(UtenteDTO ricevente, InterventoDTO intervento, VerbaleDTO verbale,VerbaleDTO verbale_principale, int motivo) throws Exception {
		
		String to = ricevente.getEMail();
		
		
		  HtmlEmail email = new HtmlEmail();
		  email.setHostName("smtps.aruba.it");
		 //email.setDebug(true);

	
		  email.setAuthentication("info@ecisrl.it", Costanti.PASS_EMAIL);

	        email.getMailSession().getProperties().put("mail.smtp.auth", "true");
	        email.getMailSession().getProperties().put("mail.debug", "true");
	        email.getMailSession().getProperties().put("mail.smtp.port", "465");
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.port", "465");
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.fallback", "false");
	        email.getMailSession().getProperties().put("mail.smtp.ssl.enable", "true");
	        

	        CommessaDTO commessa = GestioneCommesseBO.getCommessaById(intervento.getIdCommessa());
			  if(motivo==0) {
				  
					
				  email.setSubject("Creazione intervento ID n. "+intervento.getId()+" - Cliente: "+commessa.getID_ANAGEN_NOME());
				  
				  email.setHtmlMsg("<html>Si comunica che &egrave; stato creato l'intervento in oggetto. <br />  <br /><br /> "
				  		+ "<em><b style='color:#9d201d' >ECI Ente di Certificazione & Ispezione Srl</em></b><br><br><span style='color:#204d74'>Via Tofaro 42, B - 03039 Sora (FR)<br>  " + 
				  		"			  		<em>Tel + 39 0776.18151 - Fax+ 39 0776.814169 <br> " + 
				  		"			  		Mail: </em>info@ecisrl.it<br> \r\n" + 
				  		"			  		<em>Web: </em>http://www.ecisrl.it<br></span>"
				  		+ "<br><br>"
				  		+ "<font size='1' style='color:#204d74'><br><br>In ottemperanza al D.L. n. 196 del 30/6/2003 e Reg. UE n.2016/679 (GDPR) in materia di protezione dei dati personali, le informazioni contenute in questo messaggio sono strettamente confidenziali e riservate ed esclusivamente indirizzate al destinatario indicato (oppure alla persona responsabile di rimetterlo al destinatario). " + 
				  		"Vogliate tener presente che qualsiasi uso, riproduzione o divulgazione di questo messaggio è vietato. Nel caso in cui aveste ricevuto questo messaggio per errore, vogliate cortesemente avvertire il mittente e distruggere il presente messaggio.<br>" +
				  		"			  		<br>According to Italian law D.L. 196/2003 and Reg. UE n.2016/679 (GDPR)  concerning privacy, if you are not the addressee (or responsible for delivery of the message to such person) you are hereby notified that any disclosure, reproduction, distribution or other dissemination or use of this communication is strictly prohibited. If you have received this message in error, please destroy it and notify us by email." + 
				  		"			  		</font></html>");
			  }
			  else if(motivo == 1) {				  
				
				  String str = "";		
			        if(verbale.getType() == VerbaleDTO.SK_TEC) {
			        	 email.setSubject("Richiesta controfirma scheda tecnica verbale ID n. "+verbale_principale.getId()+" - Cliente: "+commessa.getID_ANAGEN_NOME());
			        	 str = "Si richiede la controfirma della scheda tecnica del verbale di verifica in oggetto.";
			        }else {
			        	 email.setSubject("Richiesta controfirma verbale ID n. "+verbale.getId()+" - Cliente: "+commessa.getID_ANAGEN_NOME());
			        	 str = "Si richiede la controfirma del verbale di verifica in oggetto.";
			        }
						 
				  
				  email.setHtmlMsg("<html>"+str+" <br />  <br /><br /> "
				  		+ "<em><b style='color:#9d201d' >ECI Ente di Certificazione & Ispezione Srl</em></b><br><br><span style='color:#204d74'>Via Tofaro 42, B - 03039 Sora (FR)<br>  " + 
				  		"			  		<em>Tel + 39 0776.18151 - Fax+ 39 0776.814169 <br> " + 
				  		"			  		Mail: </em>info@ecisrl.it<br> \r\n" + 
				  		"			  		<em>Web: </em>http://www.ecisrl.it<br></span>"
				  		+ "<br><br>"
				  		+ "<font size='1' style='color:#204d74'><br><br>In ottemperanza al D.L. n. 196 del 30/6/2003 e Reg. UE n.2016/679 (GDPR) in materia di protezione dei dati personali, le informazioni contenute in questo messaggio sono strettamente confidenziali e riservate ed esclusivamente indirizzate al destinatario indicato (oppure alla persona responsabile di rimetterlo al destinatario). " + 
				  		"Vogliate tener presente che qualsiasi uso, riproduzione o divulgazione di questo messaggio è vietato. Nel caso in cui aveste ricevuto questo messaggio per errore, vogliate cortesemente avvertire il mittente e distruggere il presente messaggio.<br>" +
				  		"			  		<br>According to Italian law D.L. 196/2003 and Reg. UE n.2016/679 (GDPR)  concerning privacy, if you are not the addressee (or responsible for delivery of the message to such person) you are hereby notified that any disclosure, reproduction, distribution or other dissemination or use of this communication is strictly prohibited. If you have received this message in error, please destroy it and notify us by email." + 
				  		"			  		</font></html>");
			  }else if(motivo == 2) {
				  
				  String str = "";		
			        if(verbale.getType() == VerbaleDTO.SK_TEC) {
			        	 email.setSubject("Comunicazione avvenuta controfirma scheda tecnica verbale ID n. "+verbale_principale.getId()+" - Cliente: "+commessa.getID_ANAGEN_NOME());
			        	 str = "Si comunica l'avvenuta controfirma da parte del RT/SRT della scheda tecnica del verbale di verifica in oggetto.";
			        }else {
			        	 email.setSubject("Comunicazione avvenuta controfirma verbale ID n. "+verbale.getId()+" - Cliente: "+commessa.getID_ANAGEN_NOME());
			        	 str = "Si comunica l'avvenuta controfirma da parte del RT/SRT del verbale di verifica in oggetto.";
			        }
					
				  email.setHtmlMsg("<html>"+str+" <br />  <br /><br /> "
				  		+ "<em><b style='color:#9d201d' >ECI Ente di Certificazione & Ispezione Srl</em></b><br><br><span style='color:#204d74'>Via Tofaro 42, B - 03039 Sora (FR)<br>  " + 
				  		"			  		<em>Tel + 39 0776.18151 - Fax+ 39 0776.814169 <br> " + 
				  		"			  		Mail: </em>info@ecisrl.it<br> \r\n" + 
				  		"			  		<em>Web: </em>http://www.ecisrl.it<br></span>"
				  		+ "<br><br>"
				  		+ "<font size='1' style='color:#204d74'><br><br>In ottemperanza al D.L. n. 196 del 30/6/2003 e Reg. UE n.2016/679 (GDPR) in materia di protezione dei dati personali, le informazioni contenute in questo messaggio sono strettamente confidenziali e riservate ed esclusivamente indirizzate al destinatario indicato (oppure alla persona responsabile di rimetterlo al destinatario). " + 
				  		"Vogliate tener presente che qualsiasi uso, riproduzione o divulgazione di questo messaggio è vietato. Nel caso in cui aveste ricevuto questo messaggio per errore, vogliate cortesemente avvertire il mittente e distruggere il presente messaggio.<br>" +
				  		"			  		<br>According to Italian law D.L. 196/2003 and Reg. UE n.2016/679 (GDPR)  concerning privacy, if you are not the addressee (or responsible for delivery of the message to such person) you are hereby notified that any disclosure, reproduction, distribution or other dissemination or use of this communication is strictly prohibited. If you have received this message in error, please destroy it and notify us by email." + 
				  		"			  		</font></html>");
			  }
			  
			  
		  email.addTo(to);
		  
		  		 
		  email.setFrom("info@ecisrl.it", "info@ecisrl.it");
		  
		  // embed the image and get the content id

//		  File image = new File(ctx.getRealPath("images/logo_calver_v2.png"));
//		  String cid = email.embed(image, "Calver logo");

		  
		  // send the email
		  email.send();
		
	}

	public static void sendEmailScadenzaVentennale(ArrayList<AttrezzaturaDTO> lista_attrezzature_scadenza, Session session) throws EmailException {
		
		
		  HtmlEmail email = new HtmlEmail();
		  email.setHostName("smtps.aruba.it");
		 //email.setDebug(true);

	
		  email.setAuthentication("info@ecisrl.it", Costanti.PASS_EMAIL);

	        email.getMailSession().getProperties().put("mail.smtp.auth", "true");
	        email.getMailSession().getProperties().put("mail.debug", "true");
	        email.getMailSession().getProperties().put("mail.smtp.port", "465");
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.port", "465");
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        email.getMailSession().getProperties().put("mail.smtp.socketFactory.fallback", "false");
	        email.getMailSession().getProperties().put("mail.smtp.ssl.enable", "true");
	        
					
		    email.setSubject("Avviso scadenza ventennale attrezzature");
				  
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");	  
			String str = "";
			
			for (AttrezzaturaDTO attrezzatura : lista_attrezzature_scadenza) {
				
				if(attrezzatura.getId_sede()==0) {
					str += "ID: "+attrezzatura.getId()+" - Cliente: "+attrezzatura.getNome_cliente()+" - Sede: Non Associate - Data scadenza: "+df.format(attrezzatura.getData_scadenza_ventennale())+"<br>";
				}else {
					str += "ID: "+attrezzatura.getId()+" - Cliente: "+attrezzatura.getNome_cliente()+" - Sede: "+attrezzatura.getNome_sede()+" - Data scadenza: "+df.format(attrezzatura.getData_scadenza_ventennale())+"<br>";
				}
				
				attrezzatura.setScadenza_ventennale_segnalata(1);
			}
				  
				  email.setHtmlMsg("<html>Si comunica che la scadenza ventennale delle seguenti attrezzature scadr&agrave; entro i prossimi 60 giorni<br><br>"+str+"</html>"); 
		
			  
		  email.addTo("antonio.dicivita@ncsnetwork.it");
		  email.addTo("antoniodicivita@live.it");	
		  		 
		  email.setFrom("info@ecisrl.it", "info@ecisrl.it");
		  

		  email.send();
		
		  
	}

}
