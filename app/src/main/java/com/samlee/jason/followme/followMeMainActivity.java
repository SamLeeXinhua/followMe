package com.samlee.jason.followme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

public class followMeMainActivity extends AppCompatActivity {

    MapView mMapView = null;
    BaiduMap mBaiduMap ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_follow_me_main);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);

        mBaiduMap = mMapView.getMap();

        LocationClient myLocation;

        myLocation = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd90ll");
        option.setScanSpan(1000);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        myLocation.setLocOption(option);
        myLocation.start();

//        private MKOfflineMap mOffline
//        mOffline = new MKOfflineMap();
//// 传入接口事件，离线地图更新会触发该回调
//        mOffline.init(this);
//
//// 获取城市可更新列表
//        ArrayList<MKOLSearchRecord> records = mOffline.searchCity(cityNameView
//                .getText().toString());
//// 开始下载离线地图，传入参数为cityID, cityID 763表示城市的数字标识。
//        mOffline.start(763);
//// 暂停下载
//        mOffline.pause(763);
//// 删除下载
//        mOffline.remove(763);


        Marker marker;
        BitmapDescriptor mCurrentMarker;
//普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//卫星地图
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
//空白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);

        //定义Maker坐标点
        LatLng pointSZ = new LatLng(22.5431, 114.0579);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.smallcar);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions optionOverlay = new MarkerOptions().position(pointSZ).icon(bitmap);
//在地图上添加Marker，并显示
        mBaiduMap.addOverlay(optionOverlay);

        mBaiduMap.setMyLocationEnabled(true);

         MapStatus mMapStatus = new MapStatus.Builder()
                .target(pointSZ)
                .zoom(15)
                .build();

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);




        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override

            public void onMapClick(LatLng latLng) {
                double currentLatitude ;
                double currentLongitude;
                currentLatitude = mBaiduMap.getLocationData().latitude;
                currentLongitude = mBaiduMap.getLocationData().longitude;
                Toast draft =Toast.makeText(getApplicationContext(), "Location : " + currentLatitude + "|"+ currentLongitude, Toast.LENGTH_SHORT);
                draft.show();

                LatLng pointCurrent = new LatLng(currentLatitude,currentLongitude);

                MapStatus mMapStatusCurrent = new MapStatus.Builder()
                        .target(pointCurrent)
                        .zoom(15)
                        .build();

                MapStatusUpdate mMapStatusUpdateCurrent = MapStatusUpdateFactory.newMapStatus(mMapStatusCurrent);
//改变地图状态
                mBaiduMap.setMapStatus(mMapStatusUpdateCurrent);


            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

//TODO start

        // 开启定位图层
        BDLocation location = new BDLocation();

        mBaiduMap.setMyLocationEnabled(true);
//// 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
//                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
//// 设置定位数据
        mBaiduMap.setMyLocationData(locData);
//// 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.smallcar);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfiguration(config);
//// 当不需要定位图层时关闭定位图层
//        mBaiduMap.setMyLocationEnabled(false);

        //TODO end
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}