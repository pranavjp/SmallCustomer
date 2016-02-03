package poc.pranav.tml.com.customerappdemo;;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.lang.reflect.Method;


/*Java Class for CheckConnectivity*/
public class CheckConnectivity{
    ConnectivityManager connectivityManager;
    NetworkInfo wifiInfo, mobileInfo;
/*Function for checking internet connetivity*/
   public Boolean checkNow(Context con){
 
        try{
            connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
 
            if(wifiInfo.isConnected() || mobileInfo.isConnected())
            {
                return true;
            }
        }
        catch(Exception e)
        {
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
        }
 
        return false;

       /*
       boolean mobileDataEnabled = false; // Assume disabled
       ConnectivityManager cm = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
       try {
           Class cmClass = Class.forName(cm.getClass().getName());
           Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
           method.setAccessible(true); // Make the method callable
           // get the setting for "mobile data"
           mobileDataEnabled = (Boolean)method.invoke(cm);


       } catch (Exception e) {
           // Some problem accessible private API
           // TODO do whatever error handling you want here
           e.printStackTrace();
           return true;
       }
        return mobileDataEnabled;

       */
    }



}
