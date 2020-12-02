package com.example.vanken.ui.tools;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.vanken.LoginActivity;
import com.example.vanken.MainTecnicoActivity;
import com.example.vanken.R;

import static android.content.Context.MODE_PRIVATE;

public class ConfiguracionesFragment extends Fragment {

    private ConfiguracionesViewModel configuracionesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        configuracionesViewModel =
                ViewModelProviders.of(this).get(ConfiguracionesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_configuraciones, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        configuracionesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user.dat", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent =  new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
        return root;
    }
}