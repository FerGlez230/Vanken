package com.example.vanken.ui.tecnicos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vanken.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleTecnicoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleTecnicoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
private int id=-1;
private ImageView imageView;
private TextView nombre, apellido, telefono, username, rango;
    public DetalleTecnicoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleTecnicoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleTecnicoFragment newInstance(String param1, String param2) {
        DetalleTecnicoFragment fragment = new DetalleTecnicoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id=getArguments().getInt("id",0);
            //Toast.makeText(getContext(), Integer.toString(id),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_detalle_tecnico, container, false);
        nombre=(TextView)view.findViewById(R.id.textViewNombreDetalleTecnico);
        apellido=(TextView)view.findViewById(R.id.textViewApellidoDetalleTecnico);
        telefono=(TextView)view.findViewById(R.id.textViewTelefonoDetalleTecnico);
        rango=(TextView)view.findViewById(R.id.textViewRangoDetalleTecnico);
        imageView=(ImageView)view.findViewById(R.id.imgVDetalleTecnico);
        username=(TextView)view.findViewById(R.id.textViewUserDetalleTecnico);
        return view;
    }
}