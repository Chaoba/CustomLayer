package com.mapbox.customlayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.CustomLayer;
import com.mapbox.mapboxsdk.testapp.model.customlayer.ExampleCustomLayer;

public class MainActivity extends AppCompatActivity {

  private MapboxMap mapboxMap;
  private MapView mapView;
  private CustomLayer customLayer;

  private FloatingActionButton fab;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Mapbox.getInstance(this, "your token");

    setContentView(R.layout.activity_main);

    mapView = findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(map -> {
      mapboxMap = map;
      mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(39.91448, -243.60947), 10));
      mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> initFab());
    });
  }

  private void initFab() {
    fab = findViewById(R.id.fab);
    fab.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
    fab.setOnClickListener(view -> {
      if (mapboxMap != null) {
        swapCustomLayer();
      }
    });
  }

  private void swapCustomLayer() {
    Style style = mapboxMap.getStyle();
    if (customLayer != null) {
      style.removeLayer(customLayer);
      customLayer = null;
      fab.setImageResource(R.mipmap.ic_launcher);
    } else {
      customLayer = new CustomLayer("custom",
        ExampleCustomLayer.createContext());
      style.addLayerBelow(customLayer, "building");
      fab.setImageResource(R.drawable.ic_launcher_foreground);
    }
  }

  private void updateLayer() {
    if (mapboxMap != null) {
      mapboxMap.triggerRepaint();
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    mapView.onStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mapView.onPause();
  }

  @Override
  protected void onStop() {
    super.onStop();
    mapView.onStop();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_custom_layer, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_update_layer:
        updateLayer();
        return true;
      case R.id.action_set_color_red:
        ExampleCustomLayer.setColor(1, 0, 0, 1);
        return true;
      case R.id.action_set_color_green:
        ExampleCustomLayer.setColor(0, 1, 0, 1);
        return true;
      case R.id.action_set_color_blue:
        ExampleCustomLayer.setColor(0, 0, 1, 1);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
