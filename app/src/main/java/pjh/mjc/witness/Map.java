package pjh.mjc.witness;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.libraries.places.api.model.Place;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

// 구글맵 구현을 위해서는 OnMapReadyCallback 인터페이스를 상속


public class Map extends AppCompatActivity implements OnMapReadyCallback {
    String TAG = "placeautocomplete";
    MapFragment mapFrag;
    GoogleMap gMap;
    GroundOverlayOptions Mark;
    View dialogView;
    ImageButton addMarker, deleteMarker;
    EditText dlgEdtTitle, dlgEdtContent;
    boolean flag = false;
    String s, a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.couple_map);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MODE_PRIVATE);
        addMarker = findViewById(R.id.addMarker);
        deleteMarker = findViewById(R.id.deleteMarker);


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyCBJHBNAEvx4Ho-a5_kKvnDICscaPH3r08");
        }
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

    // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

    // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                Toast.makeText(getApplication(),"완료",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
                Toast.makeText(getApplication(),"완료",Toast.LENGTH_SHORT).show();
            }
        });


        mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this); // 스레드임을 알림 이 메소드로 OnMapReady가 호출되어 지도가 출력되면서 클릭이벤트를 처리할 준비까지 함
    }

    @Override
    public void onMapReady(GoogleMap googleMap) { // 지도 초기화, 이벤트 처리 등을 오버라이딩
        gMap = googleMap;

        //지도 초기화
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.568256, 126.897240), 15)); // 하드코딩 하였으나 앞으로는 받아올 예정 , 장소의 위경도 설정, 확대 레벨로 장소 출력

        //UI 추가, + - 버튼으로 줌 컨트롤 하기
        gMap.getUiSettings().setZoomControlsEnabled(true);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) { // 권한을 요청한다
            //Request missing location permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MODE_PRIVATE); // 권한을 수락한다
        }
        else{
            // Location permission has been granted, continue as usual.
            gMap.setMyLocationEnabled(true); // 현재위치를 **GPS 모듈** 에서 받아올 수 있도록 설정 수락할 수 있도록 설정한 것이다.
            gMap.getUiSettings().setMyLocationButtonEnabled(true); //현재위치 버튼 추가
        }

        //맵의 어딘가 클릭 이벤트 처리
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //기본 마커 및 스니펫 출력
                MarkerOptions marker = new MarkerOptions();
                LatLng location = new LatLng(37.568256,126.897240);
                marker.position(location);
                marker.alpha(0.7f);
                marker.draggable(true);
                gMap.addMarker(marker);
                AlertDialog.Builder dlg = new AlertDialog.Builder(Map.this);
                dlg.setTitle("한줄 평 남기기");

                dialogView = View.inflate(Map.this, R.layout.map_dialog, null);
                dlgEdtTitle = dialogView.findViewById(R.id.dlgEdt1);
                dlgEdtContent = dialogView.findViewById(R.id.dlgEdt2);
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) { //확인 버튼을 클릭했을때

                         s = dlgEdtTitle.getText().toString();
                         a = dlgEdtContent.getText().toString();
                        flag = !flag;
                    }
                });
                dlg.setNegativeButton("취소",null);
                dlg.show();
            }
        });

        //마커 클릭 이벤트 처리
        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if(flag) {
                    marker.setTitle(s);
                    marker.setSnippet(a);
                }
                //Intent, Activity를 사용해서 상세 페이지로 이동 가능
                return false;
            }
        });

        addMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"d",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
