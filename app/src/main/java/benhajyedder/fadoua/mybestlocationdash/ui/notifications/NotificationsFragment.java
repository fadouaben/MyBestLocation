package benhajyedder.fadoua.mybestlocationdash.ui.notifications;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import benhajyedder.fadoua.mybestlocationdash.Config;
import benhajyedder.fadoua.mybestlocationdash.JSONParser;
import benhajyedder.fadoua.mybestlocationdash.MainActivity;
import benhajyedder.fadoua.mybestlocationdash.MyPosition;
import benhajyedder.fadoua.mybestlocationdash.MyPositionAdapter;
import benhajyedder.fadoua.mybestlocationdash.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {
    ListView lvFavoris;
    ImageButton imgmap,imgdelete;
    ArrayList<MyPosition> arrayList=new ArrayList<MyPosition>();

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        lvFavoris = binding.lvFavorisNotification;

        //MyPositionAdapter myad = new MyPositionAdapter(getActivity(), MainActivity.data);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Download d = new Download(getActivity());
                d.execute();

            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    class Download extends AsyncTask {
        Context con;
        AlertDialog alert;

        public Download(Context con) {
            this.con = con;
        }

        @Override
        protected void onPreExecute() {
            AlertDialog.Builder dialog = new AlertDialog.Builder(con);
            dialog.setTitle("téléchargement");
            dialog.setMessage("veuillez pationter ...");
            alert=dialog.create();
            alert.show();

        }

        @Override
        protected Object doInBackground(Object[] objects) {
            //second thread : run
            try {

                String url = "http://"+ Config.IP+"/servicephp/get_all_user.php";
                JSONParser jsonParser=new JSONParser();
                JSONObject response = jsonParser.makeRequest(url);
                try {
                    int success = response.getInt("success");
                    if (success==0){
                        String msg =response.getString("message");
                    }
                    else{
                        JSONArray tableau = response.getJSONArray("UnePosition");
                        arrayList.clear();
                        for (int i = 0; i<tableau.length(); i++) {
                            JSONObject ligne = tableau.getJSONObject(i);
                            int id= ligne.getInt("id");
                            String longit = ligne.getString("longitude");
                            String latit = ligne.getString("latitude");
                            String desc = ligne.getString("description");
                            MyPosition p =new MyPosition(id,Double.parseDouble(longit),Double.parseDouble(latit),desc);
                            arrayList.add(p);

                        }

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            MyPositionAdapter myad = new MyPositionAdapter(getActivity(), arrayList);
            lvFavoris.setAdapter(myad);


            alert.dismiss();
        }
    }
}