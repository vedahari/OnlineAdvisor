package cps.project.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OnlineAdvisorMainActivity extends Activity {

    private static final long ONE_SECOND = 1000;
    private static final long ONE_MIN = 1000 * 60;
    private static final long TWO_MIN = ONE_MIN * 2;
    private static final long FIVE_MIN = ONE_MIN * 5;
    private static final long MEASURE_TIME = 1000 * 30;
    //private static final long POLLING_FREQ = 1000 * 10;
    private static final long POLLING_FREQ = ONE_SECOND;
    private static final float MIN_ACCURACY = 25.0f;
    private static final float MIN_LAST_READ_ACCURACY = 5.0f;
    private static final float MIN_DISTANCE = 10.0f;

    // Views for display location information
    private TextView mAccuracyView;
    private TextView mTimeView;
    private TextView mLatView;
    private TextView mLngView;

    private int mTextViewColor = Color.BLUE;

    // Current best location estimate
    private Location mBestReading;

    // Reference to the LocationManager and LocationListener
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    private final String TAG = "LocationGetLocationActivity";

    private boolean mFirstUpdate = true;

    private static VehicleLocator oVehicleLocator = new VehicleLocator();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onButtonClickDummy();

        setContentView(R.layout.activity_main);

        mAccuracyView = (TextView) findViewById(R.id.accuracy_view);
        mTimeView = (TextView) findViewById(R.id.time_view);
        mLatView = (TextView) findViewById(R.id.lat_view);
        mLngView = (TextView) findViewById(R.id.lng_view);

        // Acquire reference to the LocationManager
        if (null == (mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE)))
            finish();


        // Get best last location measurement
        mBestReading = bestLastKnownLocation(MIN_LAST_READ_ACCURACY, FIVE_MIN);

        // Display last reading information
        if (null != mBestReading) {

            updateDisplay(mBestReading);

        } else {

            mAccuracyView.setText("No Initial Reading Available");

        }

        mLocationListener = new LocationListener() {

            // Called back when location changes

            public void onLocationChanged(Location location) {

                ensureColor();
                Log.i(TAG, "onLocationChanged: ");

                // Determine whether new location is better than current best
                // estimate

                //if (null == mBestReading || location.getAccuracy() <= mBestReading.getAccuracy())
                {

                    // Update best estimate
                    mBestReading = location;

                    // Update display
                    updateDisplay(location);

					/*if (mBestReading.getAccuracy() < MIN_ACCURACY)
						mLocationManager.removeUpdates(mLocationListener);
						*/
                    oVehicleLocator.vSetVehiclePosition(location);

                }
            }

            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                // NA
            }

            public void onProviderEnabled(String provider) {
                // NA
            }

            public void onProviderDisabled(String provider) {
                // NA
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Determine whether initial reading is
        // "good enough". If not, register for
        // further location updates

        if (null == mBestReading
                || mBestReading.getAccuracy() > MIN_LAST_READ_ACCURACY
                || mBestReading.getTime() < System.currentTimeMillis()
                - ONE_SECOND) {

            // Register for network location updates
			/*if (null != mLocationManager
					.getProvider(LocationManager.NETWORK_PROVIDER)) {
				mLocationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, POLLING_FREQ,
						MIN_DISTANCE, mLocationListener);
			}*/

            // Register for GPS location updates
            if (null != mLocationManager
                    .getProvider(LocationManager.GPS_PROVIDER)) {

                try {
                    mLocationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, POLLING_FREQ,
                            MIN_DISTANCE, mLocationListener);
                }
                catch (SecurityException s)
                {
                    Log.e(TAG, "onResume:User Disabled Permission"+s.getMessage());
                }
            }

            // Schedule a runnable to unregister location listeners
//			Executors.newScheduledThreadPool(1).schedule(new Runnable() {
//
//				@Override
//				public void run() {
//
//					Log.i(TAG, "location updates cancelled");
//
//					//mLocationManager.removeUpdates(mLocationListener);
//
//				}
//			}, MEASURE_TIME, TimeUnit.MILLISECONDS);
        }
    }

    // Unregister location listeners
    @Override
    protected void onPause() {
        super.onPause();
        try {
            mLocationManager.removeUpdates(mLocationListener);
        }
        catch (SecurityException s)
        {
            Log.e(TAG, "onPause: User Denied Access"+s.getMessage());
        }
    }

    // Get the last known location from all providers
    // return best reading that is as accurate as minAccuracy and
    // was taken no longer then minAge milliseconds ago. If none,
    // return null.

    private Location bestLastKnownLocation(float minAccuracy, long maxAge) {

        Location bestResult = null;
        float bestAccuracy = Float.MAX_VALUE;
        long bestAge = Long.MIN_VALUE;

        List<String> matchingProviders = mLocationManager.getAllProviders();

        for (String provider : matchingProviders) {

            try {
                Location location = mLocationManager.getLastKnownLocation(provider);
                if (location != null) {

                    float accuracy = location.getAccuracy();
                    long time = location.getTime();

                    if (accuracy < bestAccuracy) {

                        bestResult = location;
                        bestAccuracy = accuracy;
                        bestAge = time;

                    }
                }
            }
            catch (SecurityException s)
            {
                Log.e(TAG, "bestLastKnownLocation: "+s.getMessage() );
            }


        }

        // Return best reading or null
        if (bestAccuracy > minAccuracy
                || (System.currentTimeMillis() - bestAge) > maxAge) {
            return null;
        } else {
            return bestResult;
        }
    }

    // Update display
    private void updateDisplay(Location location) {

        mAccuracyView.setText("Accuracy:" + location.getAccuracy());

        mTimeView.setText("Time:"
                + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale
                .getDefault()).format(new Date(location.getTime())));

        mLatView.setText("Longitude:" + location.getLongitude());

        mLngView.setText("Latitude:" + location.getLatitude());

    }

    private void ensureColor() {
        if (mFirstUpdate) {
            setTextViewColor(mTextViewColor);
            mFirstUpdate = false;
        }
    }

    private void setTextViewColor(int color) {

        mAccuracyView.setTextColor(color);
        mTimeView.setTextColor(color);
        mLatView.setTextColor(color);
        mLngView.setTextColor(color);

    }

    private  void onButtonClickDummy()
    {

    }

    //TODO: DO this after adding a new button in the view
    public void onButtonClick (View V)
    {
        OnAire oOnAire = new OnAire();
        oOnAire.vInitialize();
        oOnAire.vExecute();
        Toast.makeText(OnlineAdvisorMainActivity.this, "Display Idling Time!", Toast.LENGTH_LONG).show();
    }

}

