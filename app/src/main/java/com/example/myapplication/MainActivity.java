package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.overlay.PolygonOverlay;
import com.naver.maps.map.overlay.PolylineOverlay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Spinner spinner;
    private CheckBox checkBox;
    private PolygonOverlay polygon;
    private PolylineOverlay polyline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();

        }
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(@NonNull @org.jetbrains.annotations.NotNull NaverMap naverMap) {

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(35.9462109232765, 126.68205850973268));
        naverMap.moveCamera(cameraUpdate);

        final ArrayList<String> list = new ArrayList<>();
        list.add("위성지도");
        list.add("일반지도");
        list.add("하이브리드");

        checkBox=(CheckBox)findViewById(R.id.checkBox);
        spinner =(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        naverMap.setMapType(NaverMap.MapType.Satellite);
                        break;
                    case 1:
                        naverMap.setMapType(NaverMap.MapType.Basic);
                        break;
                    case 2:
                        naverMap.setMapType(NaverMap.MapType.Hybrid);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        naverMap.setOnMapClickListener((point, coord) ->
                Toast.makeText(this, coord.latitude + "  ,   " + coord.longitude,
                        Toast.LENGTH_SHORT).show());
        Marker marker1 = new Marker();
        marker1.setPosition(new LatLng(35.9462109232765, 126.68205850973268));
        marker1.setMap(naverMap);
        Marker marker2 = new Marker();
        marker2.setPosition(new LatLng(35.98785054141381, 126.74903971349413));
        marker2.setMap(naverMap);
        Marker marker3 = new Marker();
        marker3.setPosition(new LatLng(35.970526679440674, 126.61734220993289));
        marker3.setMap(naverMap);

        PolygonOverlay polygon = new PolygonOverlay();
        polygon.setCoords(Arrays.asList(
                new LatLng(35.9462109232765, 126.68205850973268),
                new LatLng(35.98785054141381, 126.74903971349413),
                new LatLng(35.970526679440674, 126.61734220993289)
        ));
        polygon.setColor(Color.argb(50,255,0,0));
        polygon.setMap(naverMap);
        PolylineOverlay polyline = new PolylineOverlay();
        polyline.setCoords(Arrays.asList(
                new LatLng(35.9462109232765, 126.68205850973268),
                new LatLng(35.98785054141381, 126.74903971349413),
                new LatLng(35.970526679440674, 126.61734220993289),
                new LatLng(35.9462109232765, 126.68205850973268)
        ));
        polyline.setWidth(10);
        polyline.setColor(Color.RED);
        polyline.setMap(naverMap);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    naverMap.setLayerGroupEnabled(NaverMap. LAYER_GROUP_CADASTRAL, true);
                }
                else {
                    naverMap.setLayerGroupEnabled(NaverMap. LAYER_GROUP_CADASTRAL, false);
                }
            }
        });
    }
}