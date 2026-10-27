package com.example.calculadoradematrizes;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.example.calculadoradematrizes.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {
    private FragmentFirstBinding binding;
    private boolean updatingType = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.selecioneOperacao1.setSelection(0);
        binding.selecioneOperacao1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                if (!updatingType) updateOperationType(position);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
        binding.botaoResolver.setOnClickListener(v -> resolveOperation());
        updateOperationType(0);
    }

    private void updateOperationType(int type) {
        if (binding == null) return;
        updatingType = true;
        boolean matrixMode = type == 0;
        binding.selecioneOperacao2.setVisibility(matrixMode ? View.VISIBLE : View.GONE);
        binding.selecioneOperacao3.setVisibility(matrixMode ? View.GONE : View.VISIBLE);
        binding.numeroLinhaEntrada.setVisibility(matrixMode ? View.GONE : View.VISIBLE);
        binding.numeroLinhaSubstituidaEntrada.setVisibility(matrixMode ? View.GONE : View.VISIBLE);

        String[] operations = getResources().getStringArray(
                matrixMode ? R.array.operacoes_matriz : R.array.operacoes_linha);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, operations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        (matrixMode ? binding.selecioneOperacao2 : binding.selecioneOperacao3).setAdapter(adapter);

        ConstraintSet set = new ConstraintSet();
        set.clone(binding.constraintLayoutFirst);
        int operationAnchor = matrixMode ? R.id.selecioneOperacao2 : R.id.selecioneOperacao3;
        if (matrixMode) {
            set.clear(R.id.numero_linha_entrada, ConstraintSet.TOP);
            set.clear(R.id.numero_linha_substituida_entrada, ConstraintSet.TOP);
            set.clear(R.id.botao_resolver, ConstraintSet.TOP);
            set.connect(R.id.botao_resolver, ConstraintSet.TOP, operationAnchor, ConstraintSet.BOTTOM,
                    dp(12));
        } else {
            set.connect(R.id.numero_linha_entrada, ConstraintSet.TOP, operationAnchor, ConstraintSet.BOTTOM, dp(8));
            set.connect(R.id.numero_linha_substituida_entrada, ConstraintSet.TOP, operationAnchor, ConstraintSet.BOTTOM, dp(8));
            set.clear(R.id.botao_resolver, ConstraintSet.TOP);
            set.connect(R.id.botao_resolver, ConstraintSet.TOP, R.id.numero_linha_entrada, ConstraintSet.BOTTOM, dp(12));
        }
        set.applyTo(binding.constraintLayoutFirst);
        updatingType = false;
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    private String text(com.google.android.material.textfield.TextInputEditText field) {
        Editable e = field.getText();
        return e == null ? "" : e.toString().trim();
    }

    private double scalarFromMatrix2() {
        String value = text(binding.matriz2Texto);
        if (value.isEmpty()) throw new IllegalArgumentException("Informe um número no campo Matriz 2 ou escalar.");
        try {
            return Double.parseDouble(value.replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Para esta operação, o campo Matriz 2 deve conter apenas um número.");
        }
    }

    private void resolveOperation() {
        try {
            int type = binding.selecioneOperacao1.getSelectedItemPosition();
            String operation = String.valueOf((type == 0
                    ? binding.selecioneOperacao2 : binding.selecioneOperacao3).getSelectedItem());
            Matriz a = MatrixCalculator.parse(text(binding.matriz1Texto));
            String result;

            if (type == 0) {
                switch (operation) {
                    case "Soma de matrizes":
                        result = MatrixCalculator.format(MatrixCalculator.add(a,
                                MatrixCalculator.parse(text(binding.matriz2Texto)), 1));
                        break;
                    case "Subtração de matrizes":
                        result = MatrixCalculator.format(MatrixCalculator.add(a,
                                MatrixCalculator.parse(text(binding.matriz2Texto)), -1));
                        break;
                    case "Soma de escalar":
                        result = MatrixCalculator.format(MatrixCalculator.scalar(a, scalarFromMatrix2(), true));
                        break;
                    case "Multiplicação escalar":
                        result = MatrixCalculator.format(MatrixCalculator.scalar(a, scalarFromMatrix2(), false));
                        break;
                    case "Multiplicação de matrizes":
                        result = MatrixCalculator.format(MatrixCalculator.multiply(a,
                                MatrixCalculator.parse(text(binding.matriz2Texto))));
                        break;
                    case "Determinante":
                        result = "det(A) = " + MatrixCalculator.formatNumber(MatrixCalculator.determinant(a));
                        break;
                    case "Matriz inversa":
                        result = MatrixCalculator.format(MatrixCalculator.inverse(a));
                        break;
                    case "Transposta":
                        result = MatrixCalculator.format(MatrixCalculator.transpose(a));
                        break;
                    default:
                        throw new IllegalArgumentException("Selecione uma operação válida.");
                }
            } else {
                int source = parseIndex(text(binding.numeroLinhaTexto), "origem");
                int target = parseIndex(text(binding.numeroLinhaSubstituidaTexto), "destino");
                result = MatrixCalculator.format(MatrixCalculator.rowColumnOperation(
                        a, type == 1, source, target, operation,
                        (operation.startsWith("Multiplicar destino") || operation.startsWith("Dividir destino"))
                                ? scalarFromMatrix2() : 1));
            }
            binding.resultadoTexto.setText(result);
        } catch (IllegalArgumentException e) {
            binding.resultadoTexto.setText("");
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            binding.resultadoTexto.setText("");
            Toast.makeText(requireContext(), "Não foi possível calcular. Verifique os dados informados.",
                    Toast.LENGTH_LONG).show();
        }
    }

    private int parseIndex(String value, String name) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Informe o índice de " + name + " como número inteiro.");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
