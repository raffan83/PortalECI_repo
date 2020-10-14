package it.portalECI.bo;

import java.util.ArrayList;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;

import org.hibernate.Session;


import org.apache.commons.mail.HtmlEmail;

import it.portalECI.DAO.GestioneComunicazioniDAO;
import it.portalECI.DTO.CommessaDTO;
import it.portalECI.DTO.TipoComunicazioneUtenteDTO;
import it.portalECI.DTO.UtenteDTO;
import it.portalECI.DTO.VerbaleDTO;

public class GestioneComunicazioniBO {

	public static ArrayList<TipoComunicazioneUtenteDTO> getListaTipoComunicazione(Session session) {
		
		return GestioneComunicazioniDAO.getListaTipoComunicazione(session);
	}

	public static TipoComunicazioneUtenteDTO getComunicazioneFromId(int id_comunicazione,Session session) {
		
		return GestioneComunicazioniDAO.getComunicazioneFromId(id_comunicazione, session);
	}

public static void sendEmailVerbale(VerbaleDTO verbale, String mailTo, String from, int stato) throws Exception {
				
		

		  HtmlEmail email = new HtmlEmail();
		  email.setHostName("smtps.aruba.it");
  		 //email.setDebug(true);

		  email.setAuthentication("info@ecisrl.it", "a$77?Qz9bx");

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
		  
		  CommessaDTO commessa = GestioneCommesseBO.getCommessaById(verbale.getIntervento().getIdCommessa());
		  if(stato==4) {
			
			  email.setSubject("Richiesta di approvazione verbale ID n. "+verbale.getId()+" - Cliente: "+commessa.getID_ANAGEN_NOME());
			  
			  email.setHtmlMsg("<html>Si richiede l'apporvazione di verifica in oggetto effettuata ai sensi del DPR 462/01. <br />  <br /><br /> "
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
			  
			  email.setHtmlMsg("<html>Si comunica che, a seguito dell'avvenuto riesame della documentazione prodotta durante l'ispezione effettuata ai sensi del DPR 462/01, si approva il verbale di verifica in oggetto. <br />  <br /><br /> "
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
			  
			  email.setHtmlMsg("<html>Si comunica l'esito negativo del riesame della documentazione prodotta durante l'ispezione effettuata ai sensi del DPR 462/01; il verbale di verifica in oggetto viene rifiutato. <br />  <br /><br /> "
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
	
}
