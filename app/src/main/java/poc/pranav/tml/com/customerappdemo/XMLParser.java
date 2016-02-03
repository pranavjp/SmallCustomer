package poc.pranav.tml.com.customerappdemo;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

/**
 * Created by Pranav on 18-01-2016.
 */

//THIS CLASS JUST PARSES THE RESPONSES AND ISOLATES DIFFERENT TAGS

public class XMLParser {

    String getSAMLToken(String xmlRequest)
    {
        String SAMLToken = null;

        try {

        //Create the parser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xmlParser = factory.newPullParser();
            xmlParser.setInput(new StringReader(xmlRequest));

        //Parse the Inputs loop through all tags
            int eventType = xmlParser.getEventType();
            while(eventType!=XmlPullParser.END_DOCUMENT)
            {
                switch (eventType) {
                        case XmlPullParser.START_TAG: {
                            String tagName = xmlParser.getName();
                            //SAML TOKEN COMES IN THE TAG AssertionArtifact
                            if (tagName.equalsIgnoreCase("AssertionArtifact"))
                            {
                                SAMLToken = xmlParser.nextText();
                                Log.d("Token string", SAMLToken);
                            }
                            break;
                        }
                                    }
                eventType = xmlParser.next();

            }

        }
        catch(Exception e)
        {
            Log.v(this.getClass().getSimpleName(), e.toString());
        }
        return SAMLToken;
    }

    public VehicleDetails getVehicleDetails(String xmlRequest)
    {
        VehicleDetails v = new VehicleDetails();

        try {

            //Create the parser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xmlParser = factory.newPullParser();
            xmlParser.setInput(new StringReader(xmlRequest));

            //Parse the Inputs loop through all tags
            int eventType = xmlParser.getEventType();
            while(eventType!=XmlPullParser.END_DOCUMENT)
            {
                switch (eventType) {
                    case XmlPullParser.START_TAG: {
                        String tagName = xmlParser.getName();
                        //SAML TOKEN COMES IN THE TAG AssertionArtifact
                        if (tagName.equalsIgnoreCase("LCNS_NUM"))
                        {
                            v.ROW_ID = xmlParser.nextText();
                            Log.d("VehicleDetail", v.ROW_ID);

                        }

                        if (tagName.equalsIgnoreCase("ASSET_NUM"))
                        {
                            v.ASSET_NUM = xmlParser.nextText();
                            Log.d("VehicleDetail", v.ASSET_NUM);

                        }

                        if (tagName.equalsIgnoreCase("FST_NAME"))
                        {
                            v.FST_NAME = xmlParser.nextText();
                            Log.d("VehicleDetail", v.FST_NAME);

                        }

                        if (tagName.equalsIgnoreCase("LAST_NAME"))
                        {
                            v.LAST_NAME = xmlParser.nextText();
                            Log.d("VehicleDetail", v.LAST_NAME);

                        }

                        if (tagName.equalsIgnoreCase("CELL_PH_NUM"))
                        {
                            v.CELL_PH_NUM = xmlParser.nextText();
                            Log.d("VehicleDetail", v.CELL_PH_NUM);
                            break;
                        }

                    }
                }
                eventType = xmlParser.next();

            }

        }
        catch(Exception e)
        {
            Log.v(this.getClass().getSimpleName(), e.toString());
        }
        return v;


    }

    public String getErrorMessage(String xmlRequest)
    {
        String response=null;
        try {

            //Create the parser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xmlParser = factory.newPullParser();
            xmlParser.setInput(new StringReader(xmlRequest));

            //Parse the Inputs loop through all tags
            int eventType = xmlParser.getEventType();
            while(eventType!=XmlPullParser.END_DOCUMENT)
            {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                    {
                        String tagName = xmlParser.getName();
                        //SAML TOKEN COMES IN THE TAG AssertionArtifact
                        if (tagName.equalsIgnoreCase("faultstring"))
                        {
                            response = xmlParser.nextText();
                            Log.d("ERROR STRING", response);
                            break;
                        }
                    }
                }
                eventType = xmlParser.next();

            }
        }
        catch(Exception e)
        {
            Log.v(this.getClass().getSimpleName(), e.toString());
        }
        return response;


    }


}
