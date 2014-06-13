package com.example.csulacampustour;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.hirawrstudios.www.csulacampustour.R;

public class CampusMapView extends Activity implements LocationListener{
	double longitude,latitude, testlong,testlat;
	double maxLongitude,maxLatitude, minLongitude, minLatitude;
	double totalLatitudeRange,totalLongitudeRange;
	ImageView iv;
	Button testButton;
	//Location variables
	private LocationManager locationManager;
	private String provider;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_campusmapview);
	    
	    //latitude north-south
	    //longitude east-west
	    //(latitude,longitude)
	    
	    //top left : 34.072140, -118.174690
	    //bottom left : 34.061404, -118.174690
	    
	    //top right: 34.072140, -118.162330
	  //bottom right: 34.061404, -118.162330
	    /*
	    //first coordinate set
	     minLatitude = 34.061404;
	     maxLatitude = 34.072140;
	    minLongitude = -118.174690; 
	    maxLongitude = -118.162330;
	    */
	    
	  /*
	    //2nd coordinate set 
	    minLatitude = 34.06129;
	    maxLatitude = 34.072140;
	    minLongitude = -118.174690;
	    maxLongitude = -118.162615;
	    */
	    
	    /*
	  //3rd coordinate set 
	    minLatitude = 34.06120;
	    maxLatitude = 34.072140;
	    minLongitude = -118.17290;
	    maxLongitude = -118.162615;
	    */
	    
	  //Campus Map 2
	    minLatitude = 34.061407;
	    maxLatitude = 34.071739;
	    minLongitude = -118.174244; //-118.174406;
	    maxLongitude = -118.162452; //-118.162422;
	    
	    //bottom right: 34.061404, -118.162738
	    
	    //for testing only
	    //latitude = 34.066472; //engineering building
	    //longitude = -118.166456;
	    //latitude = 34.067519; //campus center 
	    //longitude = -118.168543;
	    //latitude = 34.064754; //field
	    //longitude = -118.166671;
	    //latitude = 34.069168; //luckman fine arts
	    //longitude = -118.168935;
	    /*
	    //King Hall
	    Latitude = 34.067977
	    Longitude = -118.16587
	    //Physical Sciences
	    Latitude = 34.065905
	    Longitude = -118.170567
	    //Biological Sciences
	    Latitude = 34.065687
	    Longitude = -118.169355
	    //Library
	    Latitude = 34.067429
	    Longitude = -118.167461
	    //La Kretz Hall
	    Latitude = 34.065118
	    Longitude = -118.168234
	    */
	    
	    
	    iv = (ImageView)findViewById(R.id.imageView1);
	    setUp();
	    //reDrawLocation(longitude,latitude);

	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    // Define the criteria how to select the locatioin provider -> use
	    // default
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);

	    // Initialize the location fields
	    if (location != null) 
	    {
	      System.out.println("Provider " + provider + " has been selected.");
	      onLocationChanged(location);
	      double lat = location.getLatitude();
	      double lon = location.getLongitude();
	      reDrawLocation(lon,lat);
	    } else {
	      //latituteField.setText("Location not available");
	      //longitudeField.setText("Location not available");
	    }
	  
	    
	    
	}
	
	  /* Request updates at startup */
	  @Override
	  protected void onResume() 
	  {
	    super.onResume();
	    locationManager.requestLocationUpdates(provider, 400, 1, this);
	  }

	  /* Remove the locationlistener updates when Activity is paused */
	  @Override
	  protected void onPause() 
	  {
	    super.onPause();
	    locationManager.removeUpdates(this);
	  }
	  
	@Override
	public void onLocationChanged(Location location) 
	{
			double lat = location.getLatitude();
			double lon = location.getLongitude();
			reDrawLocation(lon,lat);
			String text = "lat: " + lat + "long: " + lon;
			System.out.println(text);	
			boundsCheck(lon,lat);
			
	}

	
	
	

	
	public void setUp()
	{
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		
		

	}
	
	
	
	public void boundsCheck(double longitude, double latitude)
	{
			//longitude check
			if(longitude < minLongitude)
			{
				String text = "You are not within the School Boundaries";
				Toast.makeText(this, text, Toast.LENGTH_LONG).show();
			}
			if(longitude > maxLongitude )
			{
				String text = "You are not within the School Boundaries";
				Toast.makeText(this, text, Toast.LENGTH_LONG).show();
			}
			
			//latitude check
			if(latitude < minLatitude)
			{
				String text = "You are not within the School Boundaries";
				Toast.makeText(this, text, Toast.LENGTH_LONG).show();
			}
			if(latitude > maxLatitude)
			{
				String text = "You are not within the School Boundaries";
				Toast.makeText(this, text, Toast.LENGTH_LONG).show();
			}
			
			
			
		
		
	}
	
	
	
	public void reDrawLocation(double longitude,double latitude)
	{
		
		DisplayMetrics metrics = this.getResources().getDisplayMetrics();
		double screenWidth = metrics.widthPixels; //maybe has to be int instead of double
		double screenHeight = metrics.heightPixels;
		
		System.out.println("Hamoon : screen w: " + screenWidth + "screen h: " + screenHeight);
		
		totalLatitudeRange = maxLatitude - minLatitude;
		totalLongitudeRange = minLongitude - maxLongitude; //these are reversed in this case because the min is larger
														  //this is due to androids coordinate system starting at the top left
		
		//totalLongitudeRange = maxLongitude - minLongitude;
		

		// the top left corner of an android screen is (0,0)
		//therefore we take the max long and lat degree minus 
		// the one input by gps to draw the marker
		
		
		double tempDiffLatitude = maxLatitude - latitude ;  //this is the difference from the top to the user location
		double tempDiffLongitude = minLongitude - longitude ;
		System.out.println("Hamoon : tempLatitude:" + tempDiffLatitude + " tempLongitude: " + tempDiffLongitude);
		double dy = tempDiffLatitude/totalLatitudeRange;   //this is the percent change from the top 
		double dx = tempDiffLongitude/totalLongitudeRange;
		System.out.println("Hamoon : dx:" + dx + " dy: " + dy);
		
		double yMapLocation = screenHeight * dy ;//-25 //this is the screen height times the percent change in degree units
		double xMapLocation = screenWidth * dx ; //-15
		
		if(screenWidth >= 750 && screenHeight >= 1000)
		{
			yMapLocation = yMapLocation - 45;
			xMapLocation = xMapLocation - 2;
			
		}
		
		if(screenWidth >= 480 && screenHeight >= 800 && screenWidth < 750 && screenHeight <1000)
		{
			yMapLocation = yMapLocation - 60;
			xMapLocation = xMapLocation - 5;
	
		}
		
		if(screenWidth >= 320 && screenHeight >= 480 && screenWidth < 480 && screenHeight < 800)
		{
			yMapLocation = yMapLocation - 40;
			xMapLocation = xMapLocation - 8;
	
		}
		
		if(screenWidth >= 240 && screenHeight >= 320 && screenWidth < 320 && screenHeight < 480)
		{
			yMapLocation = yMapLocation - 30;
			xMapLocation = xMapLocation - 9;
	
		}
		
		
		
		
		
		
		
		
		
		System.out.println("Hamoon : x:" + xMapLocation + " y: " + yMapLocation);
		
		
		/*
		double dy = totalLatitudeRange/screenHeight;
		double dx = totalLongitudeRange/screenWidth;
		double yMapLocation = tempDiffLatitude * dy;
		double xMapLocation = tempDiffLongitude * dx;
		*/
		//iv.setTranslationX((float) xMapLocation);
		//iv.setTranslationY((float) yMapLocation);
		iv.setX((float) xMapLocation);
		iv.setY((float) yMapLocation);
		
	}
	
	public void sendMessageEngineeringBuilding(View v)
	{
		
		Intent intent = new Intent(CampusMapView.this,EngineeringBuilding.class);
		startActivity(intent);
	}
	
	public void sendMessageLuckManBuilding(View v)
	{
		Intent intent = new Intent(CampusMapView.this,LuckManBuilding.class);
		startActivity(intent);
	}
	
	public void sendMessageBookStore(View v)
	{
		Intent intent = new Intent(CampusMapView.this,BookStore.class);
		startActivity(intent);
	}

	public void sendMessageAdministrationBuildings(View v)
	{
		Intent intent = new Intent(CampusMapView.this,AdministrationBuildings.class);
		startActivity(intent);
	}

	public void sendMessageSimpsonHall(View v)
	{
		Intent intent = new Intent(CampusMapView.this,SimpsonHall.class);
		startActivity(intent);
	}
	
	public void sendMessageKingHall(View v)
	{
		Intent intent = new Intent(CampusMapView.this,KingHall.class);
		startActivity(intent);
	}
	
	public void sendMessageLibraryBuilding(View v)
	{
		Intent intent = new Intent(CampusMapView.this,Library.class);
		startActivity(intent);
	}
	
	public void sendMessagePhysicalSciences(View v)
	{
		Intent intent = new Intent(CampusMapView.this,PhysicalSciences.class);
		startActivity(intent);
	}
	
	public void sendMessageBiologicalSciences(View v)
	{
		Intent intent = new Intent(CampusMapView.this,BiologicalSciences.class);
		startActivity(intent);
	}

	public void sendMessageLaKretzHall(View v)
	{
		Intent intent = new Intent(CampusMapView.this,LaKretzHall.class);
		startActivity(intent);
	}


	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

}
