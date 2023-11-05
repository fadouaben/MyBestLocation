package benhajyedder.fadoua.mybestlocationdash;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyPositionAdapter extends BaseAdapter {
    Context con;
    ArrayList<MyPosition> positions;

    public MyPositionAdapter(Context con, ArrayList<MyPosition> positions) {
        this.con = con;
        this.positions = positions;
    }

    @Override
    public int getCount() {
        return positions.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inf = LayoutInflater.from(con);
        View l = inf.inflate(R.layout.position_affiche,null);
        TextView tvdesc = l.findViewById(R.id.tvDescription_position);
        TextView tvlongit = l.findViewById(R.id.tvLongitude_position);
        TextView tvlantit = l.findViewById(R.id.tvLatitude_position);
        tvlongit.setText(String.valueOf(positions.get(i).longitude));
        tvlantit.setText(String.valueOf(positions.get(i).latitude));
        tvdesc.setText(positions.get(i).description);
        ImageButton imgMap = l.findViewById(R.id.imgbtnMap_positionaffiche);
        ImageButton imgDelete = l.findViewById(R.id.btnimgDelete_positionaffiche);
        int id = positions.get(i).id;


        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("MyPositionAdapter", "Clicked on position " + id);
                AlertDialog.Builder alert = new AlertDialog.Builder(con);
                alert.setTitle("Attention!! ");
                alert.setMessage("Etes vous sur de supprimer \n"+ tvdesc.getText().toString() +"Longitude :" + tvlongit.getText().toString() +" \nLatitude : " + tvlantit.getText().toString());

                alert.setPositiveButton("Suppeimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int w) {
                        Log.d("MyPositionAdapter", "Delete clicked on position " + id);
                        Suppremer s = new Suppremer(con,id);
                        s.execute();
                        Toast.makeText(con, "contact supprimé!!", Toast.LENGTH_SHORT).show();
                        positions.remove(i);
                        ((MyPositionAdapter) MyPositionAdapter.this).updatePositions(positions);


                    }
                });
                alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(con, "Suppression annulée", Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();}



        });

        imgMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String longit = tvlongit.getText().toString();
                String latitude = tvlantit.getText().toString();
                Intent i=new Intent(con,MapActivity.class);
                i.putExtra("Longit",longit);
                i.putExtra("Latitude",latitude);
                con.startActivity(i);

            }
        });


        return l;
    }
    class  Suppremer extends AsyncTask {
        Context con;
        String msg;
        int id;
        public Suppremer(Context con, int id) {
            this.con =con;
            this.id = id;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Object doInBackground(Object[]objects) {

                String url = "http://" + Config.IP + "/servicephp/delete_position.php";
                JSONParser jsonParser = new JSONParser();
                HashMap<String, String> params = new HashMap<>();
                params.put("position_id", String.valueOf(id));
                JSONObject response = jsonParser.makeHttpRequest(url, "GET", params);



            return null;
        }

        @Override
        protected void onPostExecute(Object s) {
            super.onPostExecute(s);


        }
    }




    public void updatePositions(ArrayList<MyPosition> newListe){
        positions = newListe;
        notifyDataSetChanged();
    }

}
