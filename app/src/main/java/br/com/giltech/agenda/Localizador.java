package br.com.giltech.agenda;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;


/**
 * Created by Gilciney Marques on 11/09/2017.
 */

public class Localizador {

    Context ctx;
    public Localizador(Context ctx) {
        this.ctx = ctx;
    }

    public LatLng recuperaLocalizacao(){
        LocationManager manager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Location gps = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            return new LatLng(gps.getLatitude(), gps.getLongitude());
        } else {
            Toast.makeText(ctx, "Autorize o uso do GPS", Toast.LENGTH_SHORT).show();

            return null;
        }

    }
}
