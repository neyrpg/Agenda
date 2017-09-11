package br.com.giltech.agenda;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.giltech.agenda.dao.AlunoDAO;
import br.com.giltech.agenda.modelo.Aluno;

/**
 * Created by Gilciney Marques on 10/09/2017.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng latLng = recuperaCoordenadaEndereco("Quadra 1505 bloco H Cruzeiro Novo Brasília");
        if(latLng != null){
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(recuperaLocalizacao(), 17);
            googleMap.moveCamera(cameraUpdate);
        }

        AlunoDAO dao = new AlunoDAO(getContext());
        for (Aluno aluno: dao.buscaAlunos()){
            LatLng coordenada = recuperaCoordenadaEndereco(aluno.getEndereco());
            if(coordenada != null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(aluno.getNota().toString());
                googleMap.addMarker(marcador);
            }
        }
        dao.close();

    }

    private LatLng recuperaCoordenadaEndereco(String endereco){

        Geocoder geocoder = new Geocoder(getContext());
        try {
            List<Address> enderecos = geocoder.getFromLocationName(endereco, 1);
            if(!enderecos.isEmpty()){
                LatLng latLng = new LatLng(enderecos.get(0).getLatitude(),enderecos.get(0).getLongitude());
                return latLng;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    //TODO implementar forma dinamica
    public LatLng recuperaLocalizacao(){
        LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location gps = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            return new LatLng(gps.getLatitude(), gps.getLongitude());
        } else {
            Toast.makeText(getContext(), "Autorize o uso do GPS", Toast.LENGTH_SHORT).show();

            return null;
        }

    }

}
