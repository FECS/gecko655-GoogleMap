package jp.gecko655.googlemap;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends FragmentActivity {
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map_fragment);
        googleMap = mapFragment.getMap();
        LocationManager mgr = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = mgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation == null)
            myLocation = mgr.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        if(myLocation!=null){
            LatLng latLng =new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            CameraPosition.Builder position = CameraPosition.builder()
                .tilt(0f)
                .bearing(0f)
                .target(latLng)
                .zoom(18.0f);
            CameraUpdate update = CameraUpdateFactory.newCameraPosition(position.build());
            googleMap.moveCamera(update);
        	googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("緯度: " +latLng.latitude)
            );
            TextView textView = (TextView) findViewById(R.id.textView1);
            textView.setText("経度: "+latLng.longitude);
        }
        googleMap.setOnMapLongClickListener(new OnMapLongClickListener(){

            @Override
            public void onMapLongClick(LatLng arg0) {
            	googleMap.addMarker(new MarkerOptions()
                    .position(arg0)
            		.title("緯度: " +arg0.latitude)
            		);
            	TextView textView = (TextView) findViewById(R.id.textView1);
            	textView.setText("経度: "+arg0.longitude);
                Log.e("asdf","Latitude: "+arg0.latitude);
                Log.e("asdf","Longitude: "+arg0.longitude);
                
            }
            
        });

        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_map_detailtext, container,
                    false);
            return rootView;
        }
    }

    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }

}
