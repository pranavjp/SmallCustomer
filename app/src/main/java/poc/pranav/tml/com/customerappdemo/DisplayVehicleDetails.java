package poc.pranav.tml.com.customerappdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class DisplayVehicleDetails extends Activity {
VehicleDetails v;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_vehicle_details);
        v =new VehicleDetails();
        final GlobalStorageClass gc = (GlobalStorageClass)getApplicationContext();

        v.CELL_PH_NUM= gc.vehicleDetails.CELL_PH_NUM;
        v.ASSET_NUM= gc.vehicleDetails.ASSET_NUM;
        v.FST_NAME=gc.vehicleDetails.FST_NAME;
        v.LAST_NAME=gc.vehicleDetails.LAST_NAME;

        EditText phoneNumber = (EditText) findViewById(R.id.phonenumber);
        EditText vehicleNumber = (EditText) findViewById(R.id.vehiclenumber);
        phoneNumber.setText(v.CELL_PH_NUM);
        vehicleNumber.setText(v.ASSET_NUM);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_vehicle_details, menu);
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
}
