package Gestion_Vente;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Gestion_Client.Client;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class PDFGenerator {

    public String getArray(List<LigneVente> lc) {
        String array = "";
        for (LigneVente l : lc) {
            double tva=l.getSubtotal()*0.2;
            double tt=l.getSubtotal();
            array += "					<tr>\r\n"
                    + "					  <td style=\"padding: 0.75rem;vertical-align: top; border: 1px solid #dee2e6;\">"
                    + l.getProduit().getDesignation() + "</td>\r\n"
                    + "					  <td style=\"padding: 0.75rem;vertical-align: top; border: 1px solid #dee2e6;\">"
                    + new DecimalFormat("##.##").format(l.getPrix()) + "</td>\r\n"
                    + "					  <td style=\"padding: 0.75rem;vertical-align: top; border: 1px solid #dee2e6;\">"
                    + l.getQte() + "</td>\r\n"
                    + "					  <td style=\"padding: 0.75rem;vertical-align: top; border: 1px solid #dee2e6;\">20%</td>\r\n"
                    + "					  <td style=\"padding: 0.75rem;vertical-align: top; border: 1px solid #dee2e6;\">"
                    + new DecimalFormat("##.##").format(tva) + "</td>\r\n"
                    + "					  <td style=\"padding: 0.75rem;vertical-align: top; border: 1px solid #dee2e6;\">"
                    + new DecimalFormat("##.##").format(tt)  + "</td>\r\n" + "					</tr>\r\n";
        }
        return array;
    }


    public void generateBellIntoPdf(List<LigneVente> lc, Vente vente) {
        String tr = this.getArray(lc);
        try {
            Document document = new Document(PageSize.LETTER);
            PdfWriter pdfWriter = PdfWriter.getInstance(document,
                    new FileOutputStream("C:\\Users\\xps\\Documents\\Intellij Projects\\Store--Management\\PDFs\\Facture" + vente.getId() +"@"+vente.getDate()+ ".pdf"));
            document.open();
            document.addAuthor("Yassir BOURAS");
            document.addCreationDate();

            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            Client c = vente.getClient();
            String ht = new DecimalFormat("##.##").format(vente.getTotal());
            String tva = new DecimalFormat("##.##").format(vente.getTotal()*0.2);
            String total = new DecimalFormat("##.##").format(vente.getTotal()+vente.getTotal()*0.2);
            String str = "<html>\r\n" + "	<head>\r\n" + "		<style>\r\n"
                    + "			#Global #gauche {\r\n" + "				float:left;\r\n"
                    + "				width:60%;\r\n" + "			}\r\n"
                    + "			#Global #droite {\r\n" + "				margin-left:60%;   \r\n"
                    + "			}\r\n" + "			#Global #middle {\r\n"
                    + "				float:left;\r\n" + "				margin-left:50%;\r\n"
                    + "			}\r\n" + "		</style>\r\n" + "	</head>\r\n"
                    + "	<body style=\"font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji';\"> \r\n"
                    + "	<div  id=\"contentToConvert\" > \r\n"
                    + "		<div class=\"contentToConvert\" id=\"contentToConvert\" >\r\n"
                    + "			<h1  style=\"text-align: center;margin-top: 0;margin-bottom: 0.5rem;color:red;\">Facture "
                    + vente.getId() + "</h1>\r\n" + "			<b>Socete X</b>\r\n"
                    + "			<p style=\"margin-top: 0;margin-bottom: 1rem;\">Hay Salam N 26</p>\r\n"
                    + "			<p style=\"margin-top: 0;margin-bottom: 1rem;\">Maroc 80000</p>\r\n"
                    + "			<p style=\"margin-top: 0;margin-bottom: 1rem;\">0637834832</p>\r\n"
                    + "			<p style=\"margin-top: 0;margin-bottom: 1rem;\">www.yourstore.com</p>\r\n"
                    + "			<table style=\"    width: 100%;\">\r\n"
                    + "				<tr>\r\n"
                    + "					<td style=\"width:80%;\"></td>\r\n"
                    + "					<td><b>" + c.getNom() + " " + c.getPrenom() + "</b></td>\r\n"
                    + "				</tr>\r\n" + "				<tr>\r\n"
                    + "					<td style=\"width:80%;\">\r\n"
                    + "						<p style=\"margin-top: 0;margin-bottom: 1rem;\">Reference : "
                    + vente.getId()+""+c.getId() + "</p>\r\n"
                    + "						<p style=\"margin-top: 0;margin-bottom: 1rem;\">Date :      "
                    + vente.getDate() + "</p>\r\n"
                    + "						<p style=\"margin-top: 0;margin-bottom: 1rem;\">N Client :  "
                    + c.getId() + "</p>\r\n" + "					</td>\r\n"
                    + "					<td><p style=\"margin-top: 0;margin-bottom: 1rem;\">"
                    + c.getVille() + "</p>\r\n"
                    + "					<p style=\"margin-top: 0;margin-bottom: 1rem;\">Maroc 80000</p></td>\r\n"
                    + "				</tr>\r\n" + "			</table>\r\n"
                    + "			\r\n" + "			<div style=\"padding-top:100px;\">\r\n"
                    + "				<p style=\"margin-top: 0;margin-bottom: 35px;\"> Listes des Produits :</p>\r\n"
                    + "				<table class=\"table\" style=\"width: 100%;margin-bottom: 1rem;\">\r\n"
                    + "					<tr style=\"border-color:red;\">\r\n"
                    + "					  <td style=\"padding: 0.75rem;vertical-align: top; border: 1px solid #dee2e6;\"><b>Description</b></td>\r\n"
                    + "					  <td style=\"padding: 0.75rem;vertical-align: top; border: 1px solid #dee2e6;\"><b>Prix Unitaire HT</b></td>\r\n"
                    + "					  <td style=\"padding: 0.75rem;vertical-align: top; border: 1px solid #dee2e6;\"><b>Quantity</b></td>\r\n"
                    + "					  <td style=\"padding: 0.75rem;vertical-align: top; border: 1px solid #dee2e6;\"><b>TVA %</b></td>\r\n"
                    + "					  <td style=\"padding: 0.75rem;vertical-align: top; border: 1px solid #dee2e6;\"><b>TVA</b></td>\r\n"
                    + "					  <td style=\"padding: 0.75rem;vertical-align: top; border: 1px solid #dee2e6;\"><b>Total HT</b></td>\r\n"
                    + "					</tr>\r\n" + tr + "				  </table>\r\n"
                    + "			</div>\r\n"
                    + "			<table style=\"    width: 100%;margin-top:40px;\">\r\n"
                    + "				<tr>\r\n"
                    + "					<td style=\"width:60%;\"></td>\r\n"
                    + "					<td style=\"width:20%;padding: 0.75rem;vertical-align: top;\">TOTAL(HT)</td>\r\n"
                    + "					<td style=\"padding: 0.75rem;vertical-align: top;\">" + ht
                    + " MAD</td>\r\n" + "				</tr>\r\n"
                    + "				<tr>\r\n"
                    + "					<td style=\"width:60%;\"></td>\r\n"
                    + "					<td style=\"width:20%;padding: 0.75rem;vertical-align: top;\">TVA (20%)</td>\r\n"
                    + "					<td style=\"padding: 0.75rem;vertical-align: top;\">" + tva
                    + " MAD</td>\r\n" + "				</tr>\r\n"
                    + "				<tr style=\"border:solid 2px;\">\r\n"
                    + "					<td style=\"width:60%;\"></td>\r\n"
                    + "					<td style=\"width:20%;padding: 0.75rem;vertical-align: top;\">TOTAL NET</td>\r\n"
                    + "					<td style=\"padding: 0.75rem;vertical-align: top;\">"
                    + total + " MAD</td>\r\n" + "				</tr>\r\n"
                    + "			</table>\r\n" + "			</div>\r\n" + "		</div>\r\n"
                    + "	<div style=\"text-align:center;\"><p style=\"margin-top: 180px;margin-bottom: 1rem;\">RC 12345 -Siége social :Hay Salam N 26 N° d'identifiant fiscal: 12346578 - Tel: 0637834832 -Email : yourstore@gmail.com site web www.yourstore.com</p></div>"
                    + "</body>\r\n" + "</html>";
            worker.parseXHtml(pdfWriter, document, new StringReader(str));
            document.close();
            this.openPdfFile(vente.getId(),vente.getDate());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openPdfFile(long code,String pool) throws IOException {
        File file = new File("C:\\Users\\xps\\Documents\\Intellij Projects\\Store--Management\\PDFs\\Facture" + code +"@"+pool + ".pdf");
        if (file.toString().endsWith(".pdf"))
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        else {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        }
    }
}


