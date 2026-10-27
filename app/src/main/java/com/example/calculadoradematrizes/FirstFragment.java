package com.example.calculadoradematrizes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.example.calculadoradematrizes.databinding.FragmentFirstBinding;


public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private boolean userHasInteracted = false;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @SuppressLint("ClickableViewAccessibility")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.selecioneOperacao1.setOnTouchListener((v, event) -> {
            userHasInteracted = true;
            return false;
        });

        binding.selecioneOperacao1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!userHasInteracted) {
                    return;
                }

                parent.post(() -> {
                    try {
                        ConstraintSet constraintSet = new ConstraintSet();
                        constraintSet.clone(binding.constraintLayoutFirst);

                        if (position == 0) {
                            // For matrix operations
                            binding.selecioneOperacao2.setVisibility(View.VISIBLE);
                            binding.selecioneOperacao3.setVisibility(View.GONE);
                            binding.grupoOperacoesLinha.setVisibility(View.GONE);

                            // Clear any existing constraints
                            constraintSet.clear(R.id.resultado_saida, ConstraintSet.TOP);
                            // Connect result view below selecioneOperacao2
                            constraintSet.connect(R.id.resultado_saida, ConstraintSet.TOP,
                                    R.id.selecioneOperacao2, ConstraintSet.BOTTOM,
                                    getResources().getDimensionPixelSize(R.dimen.default_margin));
                        } else {
                            // For row operations
                            binding.selecioneOperacao2.setVisibility(View.GONE);
                            binding.selecioneOperacao3.setVisibility(View.VISIBLE);
                            binding.grupoOperacoesLinha.setVisibility(View.VISIBLE);

                            // Clear any existing constraints
                            constraintSet.clear(R.id.resultado_saida, ConstraintSet.TOP);
                            // Connect result view below numero_linha_entrada
                            constraintSet.connect(R.id.resultado_saida, ConstraintSet.TOP,
                                    R.id.numero_linha_entrada, ConstraintSet.BOTTOM,
                                    getResources().getDimensionPixelSize(R.dimen.default_margin));
                        }

                        // Apply the constraints
                        constraintSet.applyTo(binding.constraintLayoutFirst);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}