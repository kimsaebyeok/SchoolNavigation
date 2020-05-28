package samsung.myapplication;

import android.Manifest;
import  android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Handler mHandler = new Handler();

    CountDownTimer mCountDownTimer = null;

    int remainDistance = 0;
    int pathDistance = 0;

    int linecount = 1;
    Polyline line[] = new Polyline[32];

    LatLng mh = new LatLng(37.496326, 126.954400);
    LatLng ag = new LatLng(37.495947, 126.954936);
    LatLng gs = new LatLng(37.496445, 126.955092);
    LatLng hn = new LatLng(37.495845, 126.956154);
    LatLng bd = new LatLng(37.496351, 126.956755);
    LatLng hh = new LatLng(37.496730, 126.956937);
    LatLng hg = new LatLng(37.495789, 126.957570);
    LatLng ml = new LatLng(37.495645, 126.958461);
    LatLng jd = new LatLng(37.496266, 126.958423);
    LatLng jl = new LatLng(37.496823, 126.957484);
    LatLng jm = new LatLng(37.497151, 126.958150);
    LatLng bj = new LatLng(37.497435, 126.957261);
    LatLng gy = new LatLng(37.497713, 126.956814);
    LatLng bm = new LatLng(37.497726, 126.956159);
    LatLng js = new LatLng(37.495436, 126.959539);
    LatLng jg = new LatLng(37.494695, 126.959684);
    LatLng street1 = new LatLng(37.496025, 126.954511);
    LatLng street2 = new LatLng(37.496161, 126.956183);
    LatLng street3 = new LatLng(37.496112, 126.956836);
    LatLng street4 = new LatLng(37.496588, 126.957116);
    LatLng street5 = new LatLng(37.496582, 126.957966);
    LatLng street6 = new LatLng(37.496022, 126.957875);
    LatLng street7 = new LatLng(37.495703, 126.958952);
    LatLng street8 = new LatLng(37.495258, 126.958133);
    LatLng street9 = new LatLng(37.495065, 126.958876);
    LatLng street10 = new LatLng(37.495372, 126.956623);
    LatLng street11 = new LatLng(37.497918, 126.955515);
    LatLng street12 = new LatLng(37.495880, 126.954228);
    LatLng street13 = new LatLng(37.495295, 126.954700);
    LatLng street14 = new LatLng(37.495003, 126.954960);
    LatLng street15 = new LatLng(37.495192, 126.955361);
    LatLng street16 = new LatLng(37.494882, 126.956507);

    LatLng vertex[] = {mh,ag,gs,hn,bd,hh,hg,ml,jd,jl,jm,bj,gy,bm,js,jg,street1,street2,street3,street4,street5,street6,street7,street8,street9,street10,street11,street12,street13,street14,street15,street16};
    int nextVertex = 0;

    int patharr[] = new int[20];
    int vertcount = 0;

    int destination = 0;

    int a;

    static int max = 100000;
    static int n = 32;
    static int data[][] = {
            {0,max,63,max,max,max,max,max,max,max,max,max,max,max,max,max,40,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max},
            {max,0,max,max,max,max,max,max,max,max,max,max,max,max,max,max,18,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max},
            {63,max,0,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max},
            {max,max,max,0,max,max,max,max,max,max,max,max,max,max,max,max,max,29,max,max,max,max,max,max,max,max,max,max,max,max,max,max},
            {max,max,max,max,0,max,max,max,max,max,max,max,max,max,max,max,max,max,25,49,max,max,max,max,max,max,max,max,max,max,max,max},
            {max,max,max,max,max,0,max,max,max,max,max,max,max,max,max,max,max,max,max,10,max,max,max,max,max,max,max,max,max,max,max,max},
            {max,max,max,max,max,max,0,max,max,max,max,max,max,max,max,max,max,max,75,max,max,31,max,max,max,max,max,max,max,max,max,max},
            {max,max,max,max,max,max,max,0,max,max,max,max,max,max,max,max,max,max,max,max,max,70,43,38,70,max,max,max,max,max,max,max},
            {max,max,max,max,max,max,max,max,0,max,max,max,max,max,max,max,max,max,max,max,53,55,max,max,max,max,max,max,max,max,max,max},
            {max,max,max,max,max,max,max,max,max,0,max,max,max,max,max,max,max,max,max,48,45,max,max,max,max,max,max,max,max,max,max,max},
            {max,max,max,max,max,max,max,max,max,max,0,83,max,max,max,max,max,max,max,max,62,max,max,max,max,max,max,max,max,max,max,max},
            {max,max,max,max,max,max,max,max,max,max,83,0,50,max,max,max,max,max,max,90,max,max,max,max,max,max,max,max,max,max,max,max},
            {max,max,max,max,max,max,max,max,max,max,max,50,0,58,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max},
            {max,max,max,max,max,max,max,max,max,max,max,max,58,0,58,max,max,max,max,max,max,max,max,max,max,max,58,max,max,max,max,max},
            {max,max,max,max,max,max,max,max,max,max,max,max,max,max,0,max,max,max,max,max,max,max,55,max,max,max,max,max,max,max,max,max},
            {max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,0,max,max,max,max,max,max,max,max,73,max,max,max,max,max,max,268},
            {40,18,max,max,max,max,max,max,max,max,max,max,max,max,max,max,0,121,max,max,max,max,max,max,max,max,max,50,max,max,max,max},
            {max,max,max,29,max,max,max,max,max,max,max,max,max,max,max,max,121,0,64,max,max,max,max,max,max,max,max,max,max,max,max,max},
            {max,max,max,max,25,max,75,max,max,max,max,max,max,max,max,max,max,64,0,58,114,89,max,max,max,93,max,max,max,max,max,max},
            {max,max,max,max,49,10,max,max,max,48,max,90,max,max,max,max,max,max,58,0,77,93,max,max,max,max,max,max,max,max,max,max},
            {max,max,max,max,max,max,max,max,53,45,62,max,max,max,max,max,max,max,114,77,0,max,max,max,max,max,max,max,max,max,max,max},
            {max,max,max,max,max,max,31,70,55,max,max,max,max,max,max,max,max,max,89,93,max,0,102,90,max,max,max,max,max,max,max,max},
            {max,max,max,max,max,max,max,43,max,max,max,max,max,max,55,max,max,max,max,max,max,102,0,max,75,max,max,max,max,max,max,max},
            {max,max,max,max,max,max,max,38,max,max,max,max,max,max,max,max,max,max,max,max,max,90,max,0,70,136,max,max,max,max,max,max},
            {max,max,max,max,max,max,max,70,max,max,max,max,max,max,max,73,max,max,max,max,max,max,75,70,0,204,max,max,max,max,max,max},
            {max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,93,max,max,max,max,136,204,0,max,max,216,max,max,57},
            {max,max,max,max,max,max,max,max,max,max,max,max,max,58,max,max,max,max,max,max,max,max,max,max,max,max,0,max,max,max,max,max},
            {max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,50,max,max,max,max,max,max,max,max,max,max,0,80,max,max,max},
            {max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,216,max,80,0,40,max,max},
            {max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,40,0,41,max},
            {max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,41,0,107},
            {max,max,max,max,max,max,max,max,max,max,max,max,max,max,max,268,max,max,max,max,max,max,max,max,max,57,max,max,max,max,107,0}};

    LocationManager locationManager;

    MarkerOptions makerOptions = new MarkerOptions().position(street1);
    Marker marker;

    private Vibrator vibrator;

    double mLatitude;  //위도
    double mLongitude;

    double beforeLat=0;
    double beforeLon=0;

    int h,m,s;
    int rH,rM,rS;

    double nowSpeed = 0;
    double before1Speed = 0;
    double before2Speed = 0;
    double curSpeed = 0;

    int first = 0;
    public void remaintime()
    {
        Calendar c = Calendar.getInstance();
        int curH = c.get(Calendar.HOUR_OF_DAY);
        int curM = c.get(Calendar.MINUTE);
        int curS = c.get(Calendar.SECOND);

        rH = h - curH;
        rM = m - curM;
        if(rM < 0)
        {
            rH--;
            rM += 60;
        }
        rS = s - curS;
        if(rS < 0)
        {
            rM--;
            rS += 60;
            if(rM < 0)
            {
                curH--;
                rM += 60;
            }
        }
    }

    public void path(int a,int b)
    {
        int i,j,k=0;
        int s,e,min;
        int [] v = new int[n];
        int [] distance = new int[n];
        int [] via = new int[n];

        s = a;
        e = b;
        for( j = 0; j < n; j++ )
        {
            v[j] = 0;
            distance[j] = max;
        }

        distance[s-1] = 0;

        for( i=0; i < n; i++ )
        {
            min = max;
            for( j=0; j < n; j++ )
            {
                if( v[j] == 0 && distance[j] < min )
                {
                    k = j;
                    min = distance[j];
                }
            }

            v[k] = 1;

            if(min == max) break;

            for(j = 0; j < n; j++)
            {
                if(distance[j] > distance[k] + data[k][j])
                {
                    distance[j] = distance[k] + data[k][j];
                    via[j]=k;
                }
            }
        }

        int path[] = new int[n];
        int path_cnt=0;
        k = e-1;
        while(true)
        {
            path[path_cnt++]=k;
            if(k == s-1)break;
            k = via[k];
        }

        LatLng my = new LatLng(mLatitude,mLongitude);
        line[nextVertex] = mMap.addPolyline(new PolylineOptions().add(my, vertex[patharr[nextVertex] - 1]).width(5).color(Color.RED));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLatitude, mLongitude)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));

        for(i = path_cnt-2; i >= 1; i--)
        {
            line[linecount] = mMap.addPolyline(new PolylineOptions().add(vertex[path[i]],vertex[path[i+1]]).width(5).color(Color.RED));
            pathDistance += data[path[i]][path[i+1]];
            patharr[vertcount] = path[i+1]+1;
            vertcount++;
            remainDistance += data[path[i]][path[i+1]];
            linecount++;
        }
        line[linecount] = mMap.addPolyline(new PolylineOptions().add(vertex[path[i]],vertex[path[i+1]]).width(5).color(Color.RED));
        pathDistance += data[path[i]][path[i+1]];
        patharr[vertcount] = path[i+1]+1;
        vertcount++;
        remainDistance += data[path[i]][path[i+1]];
        linecount++;
    }

    public void calspeed()
    {
        LatLng before = new LatLng(beforeLat,beforeLon);
        requestMyLocation();
        beforeLat = mLatitude;
        beforeLon = mLongitude;
        LatLng my = new LatLng(mLatitude,mLongitude);

        before2Speed = before1Speed;
        before1Speed = nowSpeed;
        nowSpeed = getDistance(my,before);

        TextView curspeed = (TextView) findViewById(R.id.curspeed);
        curspeed.setText(String.format("%.2f",(before1Speed+before2Speed+nowSpeed)/3) + "m/s");

        curSpeed = (before1Speed+before2Speed+nowSpeed)/3;
    }

    public void remaindistance()
    {
        LatLng my = new LatLng(mLatitude,mLongitude);
        TextView remaind = (TextView) findViewById(R.id.remaind);
        remaind.setText(String.format("%.2f",getDistance(my,vertex[patharr[nextVertex]-1])+pathDistance) + "m");
    }

    public void expectSpeed() {

        LatLng my = new LatLng(mLatitude, mLongitude);
        TextView expectspeed = (TextView) findViewById(R.id.expectspeed);

            expectspeed.setText(String.format("%.2f", (getDistance(my, vertex[patharr[nextVertex] - 1]) + pathDistance) / (rH * 60 * 60 + rM * 60 + rS)) + "m/s");

            if (curSpeed < (getDistance(my, vertex[patharr[nextVertex]]) + pathDistance) / (rH * 60 * 60 + rM * 60 + rS)) {
                vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);

        }
    }

    public void changemap() {
        LatLng my = new LatLng(mLatitude, mLongitude);

        if ((vertcount != 0) && (getDistance(my, vertex[patharr[nextVertex] - 1]) > getDistance(vertex[patharr[nextVertex + 1] - 1], my))) {

                line[nextVertex].remove();
                line[nextVertex + 1].remove();
                pathDistance -= data[patharr[nextVertex] - 1][patharr[nextVertex - 1] - 1];
                vertcount--;
                nextVertex++;
                line[nextVertex] = mMap.addPolyline(new PolylineOptions().add(my, vertex[patharr[nextVertex] - 1]).width(5).color(Color.RED));
            }
            else {
                line[nextVertex].remove();
                line[nextVertex] = mMap.addPolyline(new PolylineOptions().add(my, vertex[patharr[nextVertex] - 1]).width(5).color(Color.RED));
            }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(my));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));

    }

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView destbuilding = (TextView) findViewById(R.id.destbuiling);
        TextView remaint = (TextView) findViewById(R.id.remaint);

        Intent recvintent = getIntent();

        destbuilding.setText(recvintent.getExtras().getString("building"));

        switch (recvintent.getExtras().getString("building")) {
            case "경상관": {
                destination = 3;
                break;
            }
            case "교육관": {
                destination = 13;
                break;
            }
            case "문화관": {
                destination = 1;
                break;
            }
            case "미래관": {
                destination = 8;
                break;
            }
            case "백마관": {
                destination = 14;
                break;
            }
            case "베어드홀": {
                destination = 5;
                break;
            }
            case "벤처중소기업센터": {
                destination = 12;
                break;
            }
            case "안익태기념관": {
                destination = 2;
                break;
            }
            case "학생회관": {
                destination = 6;
                break;
            }
            case "전산관": {
                destination = 15;
                break;
            }
            case "정보과학관": {
                destination = 16;
                break;
            }
            case "조만식기념관": {
                destination = 11;
                break;
            }
            case "중앙도서관": {
                destination = 9;
                break;
            }
            case "진리관": {
                destination = 10;
                break;
            }
            case "한경직기념관": {
                destination = 7;
                break;
            }
            case "형남공학관": {
                destination = 4;
                break;
            }
        }


        switch (recvintent.getExtras().getString("time")) {
            case "1교시 (09:00~10:15)" : {
                h=9;
                m=0;
                s=0;
                remaintime();
                break;
            }

            case "2교시 (10:30~11:45)" : {
                h=10;
                m=30;
                s=0;
                remaintime();
                break;
            }

            case "3교시 (12:00~13:15)" : {
                h=12;
                m=0;
                s=0;
                remaintime();
                break;
            }

            case "4교시 (13:30~14:45)" : {
                h=13;
                m=30;
                s=0;
                remaintime();
                break;
            }

            case "5교시 (15:00~16:15)" : {
                h=15;
                m=0;
                s=0;
                remaintime();
                break;
            }

            case "6교시 (16:30~17:45)" : {
                h=16;
                m=30;
                s=0;
                remaintime();
                break;
            }

            case "7교시 (18:00~19:15)" : {
                h=18;
                m=0;
                s=0;
                remaintime();
                break;
            }

            case "8교시 (19:30~20:45)" : {
                h=19;
                m=30;
                s=0;
                remaintime();
                break;
            }
            case "9교시 (21:00~22:15)" : {
                h=21;
                m=0;
                s=0;
                remaintime();
                break;
            }
        }

        remaint.setText(rH + ":" + rM + ":" + rS);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //GPS가 켜져있는지 체크
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //GPS 설정화면으로 이동
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
            finish();
        }

        //마시멜로 이상이면 권한 요청하기
        if(Build.VERSION.SDK_INT >= 23){
            //권한이 없는 경우
            if(ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION , Manifest.permission.ACCESS_FINE_LOCATION} , 1);
            }
            //권한이 있는 경우
            else{
                requestMyLocation();
            }
        }
        //마시멜로 아래
        else{
            requestMyLocation();
        }

        requestMyLocation();

        beforeLat = mLatitude;
        beforeLon = mLongitude;

        Thread countThread = new Thread("Count Thread") {
            public void run() {
                for (int i = 0 ; i < 10000 ; i ++) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            TextView remaint = (TextView) findViewById(R.id.remaint);
                            remaintime();
                            remaint.setText(rH + ":" + rM + ":" + rS);

                            calspeed();

                            remaindistance();

                            expectSpeed();
                            if (mLongitude != 0 && mLatitude != 0)
                            changemap();
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        countThread.start();

        //mCountDownTimer = new TestCountDownTimer(60000*60, 5000);
        //mCountDownTimer.start();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    public void requestMyLocation(){
        if(ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }

        //요청
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, locationListener);
    }

    //위치정보 구하기 리스너
    LocationListener locationListener = new LocationListener() {


        @Override
        public void onLocationChanged(Location location) {
            if(ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                return;
            }

           // locationManager.removeUpdates(locationListener);

            mLatitude = location.getLatitude();   //위도
            mLongitude = location.getLongitude(); //경도
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { Log.d("gps", "onStatusChanged"); }

        @Override
        public void onProviderEnabled(String provider) { }

        @Override
        public void onProviderDisabled(String provider) { }
    };

    public double getDistance(LatLng LatLng1, LatLng LatLng2) {
        double distance = 0;
        Location locationA = new Location("A");
        locationA.setLatitude(LatLng1.latitude);
        locationA.setLongitude(LatLng1.longitude);
        Location locationB = new Location("B");
        locationB.setLatitude(LatLng2.latitude);
        locationB.setLongitude(LatLng2.longitude);
        distance = locationA.distanceTo(locationB);

        return distance;
    }


    @Override
    public void onMapReady(final GoogleMap map) {

        LatLng my = new LatLng(mLatitude,mLongitude);

        mMap = map;

        mMap.moveCamera(CameraUpdateFactory.newLatLng(street1));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));

        a = 16;
        double least = getDistance(my, vertex[16]);
        for (int i = 17; i < 32; i++)//자신의 현재 위치와 가장가까운 street vertex를 연결
        {
            if (getDistance(my, vertex[i]) < least) {
                a = i;
                least = getDistance(my, vertex[i]);
            }
        }

        patharr[vertcount] = a + 1;
        vertcount++;

        path(a + 1, destination);
    }
}