package benhajyedder.fadoua.mybestlocationdash.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import benhajyedder.fadoua.mybestlocationdash.Config;
import benhajyedder.fadoua.mybestlocationdash.JSONParser;
import benhajyedder.fadoua.mybestlocationdash.MainActivity;
import benhajyedder.fadoua.mybestlocationdash.MapActivity;
import benhajyedder.fadoua.mybestlocationdash.MyPosition;
import benhajyedder.fadoua.mybestlocationdash.databinding.FragmentDashboardBinding;



public class DashboardFragment extends Fragment {
    EditText edDesc,edLong,edLat;
    ImageButton imgbtnAdd,imgbtnMAp;
    private static final int REQUEST_MAP = 1;


    private FragmentDashboardBinding binding;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        edDesc = binding.edDescDash;
        edLat = binding.editLatitDash;
        edLong = binding.editLongitDash;
        imgbtnAdd = binding.imgbtnAddDash;
        imgbtnMAp = binding.imgbtnMapDash;

        /*FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getActivity());
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                                                          @Override
                                                          public void onSuccess(Location location) {
                                                              double longitude = location.getLongitude();
                                                              double latitude = location.getLatitude();
                                                              edLat.setText(String.valueOf(latitude));
                                                              edLong.setText(String.valueOf(longitude));


                                                          }
                                                      });*/


        imgbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edLat.getText().toString().equals("") && !edLong.getText().toString().equals("")){
                    MyPosition myPosition = new MyPosition(0,Double.parseDouble(edLong.getText().toString()), Double.parseDouble(edLat.getText().toString()), edDesc.getText().toString());
                    MainActivity.data.add(myPosition);
                    Ajout a = new Ajout(getActivity());
                    a.execute();
                    updateFrag();
                    Toast.makeText(getContext(), "Position ajouté", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Toast.makeText(getContext(), "Remplir les champs", Toast.LENGTH_SHORT).show();
                }


            }
        });


        imgbtnMAp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MapActivity.class);

                /*intent.putExtra("Longit",edLong.getText().toString());
                intent.putExtra("Latitude",edLat.getText().toString());
                startActivity(intent);*/
                startActivityForResult(intent,REQUEST_MAP);


            }
        });








        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }



    class  Ajout extends AsyncTask{
        Context con;
        public Ajout(Context con) {
            this.con =con;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            String url = "http://"+ Config.IP+"/servicephp/add_position.php";
            JSONParser jsonParser=new JSONParser();
            HashMap<String,String> parms = new HashMap<String,String>();
            parms.put("longitude",edLong.getText().toString());
            parms.put("latitude",edLat.getText().toString());
            parms.put("description",edDesc.getText().toString());
            JSONObject response = jsonParser.makeHttpRequest(url,"GET",parms);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_MAP){
            if (resultCode == Activity.RESULT_OK){
                edLat.setText(String.valueOf(data.getDoubleExtra("latitude",0.0)));
                edLong.setText(String.valueOf(data.getDoubleExtra("longitude",0.0)));
            }
        }
    }

    public void updateFrag(){
        edLong.setText("");
        edLat.setText("");
        edDesc.setText("");

    }


}