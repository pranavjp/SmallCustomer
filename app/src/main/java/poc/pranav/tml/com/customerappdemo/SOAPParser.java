package poc.pranav.tml.com.customerappdemo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

//THIS CLASS MAKES A CONNECTION TO THE QA URL AND GETS THE RESPONSES



public class SOAPParser {
Integer STATUS = -1;
    Context context;
    String qaURL = "http://tmcrmappsqa.inservices.tatamotors.com/cordys/com.eibus.web.soap.Gateway.wcp?organization=o=MobileApps,cn=cordys,cn=cbop,o=tatamotors.com";

 SOAPParser(Context c,String samlToken)
 {
     context= c;
     if(!samlToken.isEmpty())
     {
         qaURL= qaURL + "&SAMLart=" + samlToken;
     }

 }


    public String Login(String userName, String password) {
        String Response = null;

        String soapText =
                "<SOAP:Envelope xmlns:SOAP=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                +"<SOAP:Header>"
                +"<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
                +"<wsse:UsernameToken xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
                +"<wsse:Username>" +userName+ "</wsse:Username>"
                +"<wsse:Password>"+ password+ "</wsse:Password>"
                +"</wsse:UsernameToken>"
                +"</wsse:Security>"
                +"<header xmlns=\"http://schemas.cordys.com/General/1.0/\">"
                +"<Logger xmlns=\"http://schemas.cordys.com/General/1.0/\"/>"
                +"</header>"
                +"</SOAP:Header>"
                +"<SOAP:Body>"
                +"<samlp:Request xmlns:samlp=\"urn:oasis:names:tc:SAML:1.0:protocol\" MajorVersion=\"1\" MinorVersion=\"1\" IssueInstant=\"2010-05-03T06:53:51Z\" RequestID=\"aa23159489-0126-1853-28ca-d7024556c36\">"
                +"<samlp:AuthenticationQuery>"
                +"<saml:Subject xmlns:saml=\"urn:oasis:names:tc:SAML:1.0:assertion\">"
                +"<saml:NameIdentifier Format=\"urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified\">"+userName+"</saml:NameIdentifier>"
                +"</saml:Subject>"
                +"</samlp:AuthenticationQuery>"
                +"</samlp:Request>"
                +"</SOAP:Body>"
                +"</SOAP:Envelope>";

        try {

            HttpURLConnection connection = null;
            InputStream inStr = null;
            URL url = new URL(qaURL);
            connection = (HttpURLConnection) url.openConnection();

         //   Toast.makeText(this.context, "TEST", Toast.LENGTH_SHORT).show();

            if (connection != null) {
                //SET PARAMETERS FOR CONNECTION
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.setConnectTimeout(500000);
                connection.setRequestProperty("Content-Type", "text/xml");
                connection.setRequestProperty("charset", "utf-8");
                connection.setRequestProperty("Content-Length", "" + soapText.getBytes().length);
                OutputStream opStream;
                //GET REQUEST AND WRITE REQUEST TO CONNECTION
                      opStream = connection.getOutputStream();
                      opStream.write(soapText.getBytes());

          //      Toast.makeText(this.context, "TEST2", Toast.LENGTH_SHORT).show();

                int inChar = 0;
                StringBuffer inBuffer = null;

                STATUS = connection.getResponseCode();
                opStream.flush();
                Log.i("SOAPPARSER STATUS", STATUS + "");
                opStream.close();
                if(STATUS== HttpURLConnection.HTTP_INTERNAL_ERROR)
                {
                //HANDLE ERROR STREAM
                    try {
                        inStr = connection.getErrorStream();
                        {

                            if (inStr != null) {
                                inBuffer = new StringBuffer();
                                while ((inChar = inStr.read()) != -1)
                                {
                                    inBuffer.append((char) inChar);
                                }
                                                }

                            Log.i("HTTPERROR","INSIDE LOOP");

                            if (inBuffer != null)
                                Response = inBuffer.toString();
                            else
                                Response = new String("<err>No Data.</err>");
                        }

                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }

                }
                if (STATUS == HttpURLConnection.HTTP_OK) {
                    inStr = connection.getInputStream();
                    if (inStr != null) {
                        inBuffer = new StringBuffer();
                        while ((inChar = inStr.read()) != -1)
                        {
                            inBuffer.append((char) inChar);
                        }
                    }

                    if (inBuffer != null)
                        Response = inBuffer.toString();
                    else
                        Response = new String("<err>No Data.</err>");
                }
            }

        } catch (Exception e) {
            Response = e.getMessage();
            e.printStackTrace();
        }

        return Response;

    }

    public String GetVehicleDetailsByRegNo(String vehicleNumber)
    {
        String Response = null;
        String soapText =
                "<SOAP:Envelope xmlns:SOAP=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
                "<SOAP:Body>"+
                "<GetVehicleDetailsByRegNo_CSB xmlns=\"src.com.CSB\" preserveSpace=\"no\" qAccess=\"0\" qValues=\"\">" +
                "<REG_NUM>" +
                vehicleNumber +
                "</REG_NUM>"+
                "</GetVehicleDetailsByRegNo_CSB>"+
                "</SOAP:Body>"+
                "</SOAP:Envelope>";

        try {

            HttpURLConnection connection = null;
            InputStream inStr = null;
            URL url = new URL(qaURL);
            connection = (HttpURLConnection) url.openConnection();

            //   Toast.makeText(this.context, "TEST", Toast.LENGTH_SHORT).show();

            if (connection != null) {
                //SET PARAMETERS FOR CONNECTION
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.setConnectTimeout(500000);
                connection.setRequestProperty("Content-Type", "text/xml");
                connection.setRequestProperty("charset", "utf-8");
                connection.setRequestProperty("Content-Length", "" + soapText.getBytes().length);
                OutputStream opStream;
                //GET REQUEST AND WRITE REQUEST TO CONNECTION
                opStream = connection.getOutputStream();
                opStream.write(soapText.getBytes());

                //      Toast.makeText(this.context, "TEST2", Toast.LENGTH_SHORT).show();

                int inChar = 0;
                StringBuffer inBuffer = null;

                STATUS = connection.getResponseCode();
                opStream.flush();
                Log.i("STATUS",STATUS+"");
                opStream.close();
                if (STATUS == HttpURLConnection.HTTP_OK) {
                    inStr = connection.getInputStream();
                    if (inStr != null) {
                        inBuffer = new StringBuffer();
                        while ((inChar = inStr.read()) != -1)
                        {
                            inBuffer.append((char) inChar);
                        }
                    }

                    if (inBuffer != null)
                        Response = inBuffer.toString();
                    else
                        Response = new String("<err>No Data.</err>");
                }
            }

        } catch (Exception e) {
            Response = e.getMessage();
            e.printStackTrace();
        }

        return Response;
    }
}

/**
 * Created by Pranav on 08-01-2016.
 */
