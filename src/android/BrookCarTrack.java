package com.stanleyidesis.cordova.plugin;
// The native Toast API
import android.os.Bundle;
import android.widget.Toast;
// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.cartrack.blesdk.ctg.BleListener;
import com.cartrack.blesdk.ctg.BleService;
import com.cartrack.blesdk.ctg.BleTerminal;
import com.cartrack.blesdk.enumerations.*;


public class BrookCarTrack extends CordovaPlugin {
  private static final String DURATION_LONG = "long";
  private BleTerminal bleTerminal;
  //private BleService bleService;
    //private  ProgressDialog progressDialog;
    private  PermissionManager permissionManager;


    //public BrookCarTrack(Bundle savedInstanceState)
    //{

        //progressDialog =  new ProgressDialog(cordova.getContext());
        //permissionManager =new PermissionManager();
        //permissionManager.requestPermission();

        //String lastUsedTerminalId = getSharedPreferences().getString(LAST_USED_TERMINAL_ID, "");
        //et_terminal_id.setText(lastUsedTerminalId);

        //val scanTimeoutDuration = getSharedPreferences().getString(SCAN_TIMER_DURATION, "10");
        //et_scan_duration.setText(scanTimeoutDuration);

    //}
  @Override
  public boolean execute(String action, JSONArray args,
    final CallbackContext callbackContext) {


      // Verify that the user sent a 'show' action
      if (action.equals("show")) {
          RequestPermission();
          String message;
          String duration;
          try {
              JSONObject options = args.getJSONObject(0);
              message = options.getString("message");
              duration = options.getString("duration");
          } catch (JSONException e) {
              callbackContext.error("Error encountered: " + e.getMessage());
              return false;
          }
          // Create the toast
          Toast toast = Toast.makeText(cordova.getActivity(), message,
                  DURATION_LONG.equals(duration) ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
          // Display toast
          toast.show();
          // Send a positive result to the callbackContext
          PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
          callbackContext.sendPluginResult(pluginResult);
          return true;
      }
      else if(action.equals("CreateBle"))
      {



          configBle("TEST");

          showToast("CreateBle1234");

          getAuthKey();





          return true;
      }
      else if(action.equals("saveKey"))
      {


          saveAuthKey("9555DBDC5DC124423A82D3D691DD31BD5123C103EC4118343B61E4796569637B");
          //configBle();
          //getAuthKey();
          showToast("saveKey success");



          return true;
      }
      else if (action.equals("connect")) {

          connect("TEST");

          return true;
      }
      else if (action.equals("lock")) {
         lock();
          showToast("lock success");
          return true;
      }
      else if (action.equals("unlock")) {
          unlock();
          showToast("unlock success");
          return true;
      }
      else if (action.equals("horn")) {

          horn();
          showToast("horn success");
          return true;
      }
      else if (action.equals("oneClickConnect")) {

          String terminalID;
          String key;
          try {
              JSONObject options = args.getJSONObject(0);
              terminalID = options.getString("terminalID");
              key = options.getString("key");
          } catch (JSONException e) {
              callbackContext.error("Error encountered: " + e.getMessage());
              return false;
          }


          configBle(terminalID);
          getAuthKey();
          saveAuthKey(key);
          connect(terminalID);

          return true;
      }
      else if (action.equals("disconnect")) {

          disconnect();
          return true;
      }
      else
      {
          callbackContext.error("\"" + action + "\" is not a recognized action.");
          return false;
      }

  }
    private void showToast(String text) {

        // Create the toast
        Toast toast = Toast.makeText(cordova.getActivity(), text,
                DURATION_LONG.equals("long") ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        // Display toast
        toast.show();
    }
    BleListener bleListener = new BleListener() {
        @Override
        public void onTerminalConnected(BleTerminal bleTerminal) {
            showToast(bleTerminal.getTerminalId()+" Connected");
        }

        @Override
        public void onTerminalCommandResult(BleAction bleAction) {

            showToast("Success executing "+bleAction.name());
        }

        @Override
        public void onTerminalDidGetVehicleStats(byte b, GetVehicleStats getVehicleStats) {

            showToast("onTerminalDidGetVehicleStats");
        }

        @Override
        public void onTerminalDidGetVehicleStatus(byte b, GetVehicleStatus getVehicleStatus) {
            showToast(bleTerminal.getTerminalId()+" Disconnected");
        }

        @Override
        public void onTerminalDisconnected(BleTerminal bleTerminal) {
            showToast(bleTerminal.getTerminalId()+" Terminal Disconnected");
        }

        @Override
        public void onSaveAuthKeySuccess(BleTerminal bleTerminal, String s) {
            showToast("Save Auth Key Successfully");
        }

        @Override
        public void onSignalStrength(BleSignalStrength bleSignalStrength) {
            showToast(bleSignalStrength.name());
        }

        @Override
        public void onRemoveAuthKeySuccess() {
            showToast("Remove Auth Key Successfully");
        }

        @Override
        public void onError(BleError bleError) {

            if (bleError.getActionCode() != -1) {
                showToast( "Action code:"+bleError.getActionCode()+" Error Code:"+bleError.getErrorCode()+" "+ bleError.getLocalizedDescription());
            }
            else {
                showToast( "Error Code:"+bleError.getErrorCode()+" "+ bleError.getLocalizedDescription());
            }
        }
    };


    private void RequestPermission()
    {
        permissionManager =new PermissionManager(this.cordova.getActivity());
        permissionManager.requestPermission();
    }

    private boolean configBle(String TerminalID)
    {
        //BleService(cordova.getContext());
        //BleService.configure();
        BleService.Companion.clear();
       // BleService.Companion.configure(this.webView.getContext());
        BleService.Companion.configure(this.cordova.getContext());

        if (bleTerminal == null) {
            bleTerminal = BleService.Companion.getTerminal(TerminalID);
            bleTerminal.setBleListener(bleListener);
        }

        return true;
    }
    private void getAuthKey()
    {
        String authKey = bleTerminal.getAuthKey();

    }

    private void saveAuthKey(String key) {
        bleTerminal.saveAuthKey(key);
    }

    private void connect(String TerminalID)
    {
		showToast("1");
        bleTerminal = BleService.Companion.getTerminal(TerminalID);
		showToast("2");
        bleTerminal.setBleListener(bleListener);
		showToast("3");
        bleTerminal.scanAndConnectToPeripheral(100000);
		showToast("4");
    }
    private void lock()
    {
        bleTerminal.sendAction(BleAction.LOCK);
    }
    private void unlock()
    {
        bleTerminal.sendAction(BleAction.UNLOCK);
    }
    private void horn()
    {
        bleTerminal.sendAction(BleAction.HORN);
    }

    private void disconnect()
    {
        bleTerminal.disconnect();
    }
}