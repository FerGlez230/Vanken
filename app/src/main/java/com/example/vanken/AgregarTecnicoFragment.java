package com.example.vanken;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgregarTecnicoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgregarTecnicoFragment extends Fragment {


    public AgregarTecnicoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AgregarTecnicoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AgregarTecnicoFragment newInstance(String param1, String param2) {
        AgregarTecnicoFragment fragment = new AgregarTecnicoFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_tecnico, container, false);
    }
}