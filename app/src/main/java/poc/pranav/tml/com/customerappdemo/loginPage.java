package poc.pranav.tml.com.customerappdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class loginPage extends Activity {
    public String userName;
    public String password;
    public String ErrorMessage;
    String samlToken;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

       // this.textView = (TextView)findViewById(R.id.textView);

        buttonclickListner();
    }

    private class AsyncTaskGreetingServiceCaller extends AsyncTask<Void, Void, Void> {
        String responseMessage;
        VehicleDetails v;
        protected Void doInBackground(Void...voids)
        {

            try
            {
                //CREATE CONNECTION AND GET SAMLART
                SOAPParser sc = new SOAPParser(getBaseContext(),"");
                EditText Username = (EditText) findViewById(R.id.editTextUsername);
                EditText Password = (EditText) findViewById(R.id.editTextPassword);

                responseMessage = sc.Login(userName, password);

                XMLParser x = new XMLParser();
                samlToken = x.getSAMLToken(responseMessage);
                Log.i("SAMLART",samlToken + " ");

                //SAMLTOKEN ERROR MAPPING
                if(samlToken==null)
                {
                   ErrorMessage=x.getErrorMessage(responseMessage);
                    return null;
                }

                //IF SAMLTOKEN IS PRESENT THEN GET VEHICLE DETAILS
                if(!samlToken.isEmpty()) {
                                 final GlobalStorageClass gc = (GlobalStorageClass)getApplicationContext();
                                 gc.SAMLToken= samlToken;
                               SOAPParser sc1 = new SOAPParser(getApplicationContext(), samlToken);
                                responseMessage = sc1.GetVehicleDetailsByRegNo("MH01AB1234");
                                 gc.vehicleDetails = x.getVehicleDetails(responseMessage);
                                         }
            } catch (Exception exception)
            {
               // exception.printStackTrace();
                responseMessage = exception.getMessage();
            }
            return null;
        }

        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            setContentView(R.layout.activity_login_page);
            TextView soap = (TextView) findViewById(R.id.textViewSoapResponse);
            Log.i("Post execute", responseMessage + " ");

            if(samlToken==null)
            {
                soap.setText(ErrorMessage);
                return;
            }

            soap.setText("Login Successful");
                   Intent i = new Intent(getApplicationContext(),DisplayVehicleDetails.class);
                    startActivity(i);
        }

}

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonclickListner()
    {
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(new CheckConnectivity().checkNow(getApplicationContext())) {
                    //setContentView(R.layout.activity_login_page);
                    TextView soap = (TextView) findViewById(R.id.textViewSoapResponse);
                    soap.setText("Connecting...");

                    EditText Username = (EditText) findViewById(R.id.editTextUsername);
                    EditText Password = (EditText) findViewById(R.id.editTextPassword);
                    userName = Username.getText().toString();
                    password = Password.getText().toString();
                    try
                    {
                        AsyncTaskGreetingServiceCaller asyncTaskGreetingServiceCaller = new AsyncTaskGreetingServiceCaller();
                        asyncTaskGreetingServiceCaller.execute();
                    }
                    catch(Exception e)
                    {
                        Log.d("THREAD EXCEPTION",e.getMessage());
                    }

                }
                else
                {
                    //ErrorMessage= new String("ConnectionError").toCharArray();
                    TextView soap = (TextView) findViewById(R.id.textViewSoapResponse);
                    soap.setText("APPLICATION CONNECTION ERROR");
                }

                //t.setText(new SOAPParser().GetResponse()0);
            }
        });


    }


    }
