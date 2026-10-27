package com.example.calc_matrizes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.Gravity;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // Instâncias das matrizes
    private Matriz matriz1;
    private Matriz matriz2;
    private Matriz matriz3;
    
    /**
     * Preenche uma TableLayout com os valores de uma Matriz
     * @param tabela TableLayout que será preenchida
     * @param matriz Matriz com os dados a serem exibidos
     */
    private void preencherTabela(TableLayout tabela, Matriz matriz) {
        tabela.removeAllViews(); // Limpa a tabela existente

        for (int i = 0; i < matriz.getnLinhas(); i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < matriz.getnColunas(); j++) {
                TextView textView = new TextView(this);
                // Formata o número para exibir com vírgula como separador decimal usando locale pt-BR
                String valorFormatado = String.format(new Locale("pt", "BR"), "%.2f", matriz.getValor(i, j));
                textView.setText(valorFormatado);
                textView.setGravity(Gravity.CENTER);
                
                // Aplica o estilo MatrixCell
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    textView.setTextAppearance(R.style.MatrixCell);
                } else {
                    textView.setTextAppearance(this, R.style.MatrixCell);
                }
                
                // Garante que o background seja aplicado
                textView.setBackgroundResource(R.drawable.cell_border);
                
                row.addView(textView);
            }

            tabela.addView(row, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
    
    /**
     * Inicializa as matrizes e preenche as tabelas
     */
    private void inicializarMatrizes() {
        try {
            // Inicializar matriz 1 2 e 3
            matriz1 = new Matriz(5, 5);
            matriz2 = new Matriz(2, 5);
            matriz3 = new Matriz(5, 2);

            //Matriz 1 Linha 1 (posição 0 no array)
            matriz1.setValor(0, 0, 0.1);
            matriz1.setValor(0, 1, 0.0);
            matriz1.setValor(0, 2, -0.1);
            matriz1.setValor(0, 3, 0.0);
            matriz1.setValor(0, 4, 0.0);

            //Matriz 1 Linha 2 (posição 1 no array)
            matriz1.setValor(1, 0, 0.0);
            matriz1.setValor(1, 1, 0.0);
            matriz1.setValor(1, 2, -0.125);
            matriz1.setValor(1, 3, 0.125);
            matriz1.setValor(1, 4, 0.0);

            //Matriz 1 Linha 3 (posição 2 no array)
            matriz1.setValor(2, 0, 0.0);
            matriz1.setValor(2, 1, 0.25);
            matriz1.setValor(2, 2, -0.25);
            matriz1.setValor(2, 3, 0.0);
            matriz1.setValor(2, 4, 0.0);

            //Matriz 1 Linha 4 (posição 3 no array)
            matriz1.setValor(3, 0, -0.1);
            matriz1.setValor(3, 1, -0.25);
            matriz1.setValor(3, 2, 0.475);
            matriz1.setValor(3, 3, -0.125);
            matriz1.setValor(3, 4, 1.0);

            //Matriz 1 Linha 5 (posição 4 no array)
            matriz1.setValor(4, 0, 0.0);
            matriz1.setValor(4, 1, 0.0);
            matriz1.setValor(4, 2, 1.0);
            matriz1.setValor(4, 3, 0.0);
            matriz1.setValor(4, 4, -1.0);

            //Matriz 2 Linha 1 (posição 0 no array)
            matriz2.setValor(0, 0, 3);
            matriz2.setValor(0, 1, 22);
            matriz2.setValor(0, 2, 1);
            matriz2.setValor(0, 3, 44);
            matriz2.setValor(0, 4, 0);

            //Matriz 2 Linha 2 (posição 1 no array)
            matriz2.setValor(1, 0, 1.5);
            matriz2.setValor(1, 1, 11);
            matriz2.setValor(1, 2, 0.5);
            matriz2.setValor(1, 3, 22);
            matriz2.setValor(1, 4, 0);

            //Matriz 3 Linha 1 (posição 0 no array)
            matriz3.setValor(0, 0, 5);
            matriz3.setValor(0, 1, 0);

            //Matriz 3 Linha 2 (posição 1 no array)
            matriz3.setValor(1, 0, 9);
            matriz3.setValor(1, 1, 1);

            //Matriz 3 Linha 3 (posição 2 no array)
            matriz3.setValor(2, 0, 13);
            matriz3.setValor(2, 1, 2);

            //Matriz 3 Linha 4 (posição 3 no array)
            matriz3.setValor(3, 0, 17);
            matriz3.setValor(3, 1, 3);

            //Matriz 3 Linha 5 (posição 4 no array)
            matriz3.setValor(4, 0, 21);
            matriz3.setValor(4, 1, 4);
            
            // Preencher as tabelas com as matrizes
            preencherTabela(resultado1Tabela, matriz1);
            preencherTabela(resultado2Tabela, matriz2);
            preencherTabela(resultado3Tabela, matriz3);
        } catch (IllegalArgumentException e) {
            // Log do erro para debug
            Log.e("MainActivity", "Erro ao inicializar matrizes: " + e.getMessage());
            // Exibe uma mensagem de erro para o usuário
            Toast.makeText(this, "Erro ao inicializar as matrizes: " + e.getMessage(),
                         Toast.LENGTH_LONG).show();
        }
    }
    // Views para droplist/Spinner e Radiobuttons
    private Spinner qualOperacaoSpinner;
    private RadioGroup tipoOperacaoRadio;
    private RadioButton operacaoMatrizBotao;
    private RadioGroup metodoSubstituicaoRadio;
    private RadioButton apenasSubstituiBotao;
    
    // Views para operações de linha/coluna
    private TextView colunaOperacaoTitulo;
    private EditText colunaOperacaoEntrada;
    private TextView colunaSubstituidaTitulo;
    private EditText colunaSubstituidaEntrada;
    private TextView metodoSubstituicaoTitulo;
    
    // Views para exibição de resultados
    private TextView resultadosTitulo;
    private TableLayout resultado1Tabela;
    private TableLayout resultado2Tabela;
    private TableLayout resultado3Tabela;
    
    // Arrays de operações de matriz
    private final String[] operacoesMatrizArray = {
            "Soma entre Matrizes",
            "Multiplicação entre matrizes",
            "Soma a Número Real",
            "Multiplicação por número Real",
            "Calcular Determinante",
            "Identificar Matriz inversa"
    };

    //Array de operações de linha/coluna
    private final String[] operacoesColunaArray = {
            "Soma",
            "Multiplicação",
            "Divisão"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        // 1. INICIALIZAÇÃO DE TODAS AS VIEWS
        inicializarViews();
        
        // 2. CONFIGURAÇÃO DOS ESTADOS INICIAIS
        configurarEstadosIniciais();
        
        // 3. CONFIGURAÇÃO DOS LISTENERS
        configurarListeners();
        
        // Inicializa as matrizes e preenche as tabelas
        inicializarMatrizes();
    }
    
    /**
     * Inicializa todas as views do layout
     */
    private void inicializarViews() {
        // Views de droplist/Spinner e RadioButtons
        qualOperacaoSpinner = findViewById(R.id.qual_operacao_spinner);
        operacaoMatrizBotao = findViewById(R.id.operacao_matriz_botao);
        tipoOperacaoRadio = findViewById(R.id.tipo_operacao_radio);
        
        // Views de Operações de Linha/Coluna
        colunaOperacaoTitulo = findViewById(R.id.coluna_operacao_titulo);
        colunaOperacaoEntrada = findViewById(R.id.coluna_operacao_entrada);
        colunaSubstituidaTitulo = findViewById(R.id.coluna_substituida_titulo);
        colunaSubstituidaEntrada = findViewById(R.id.coluna_substituida_entrada);
        metodoSubstituicaoTitulo = findViewById(R.id.metodo_substituicao_titulo);
        metodoSubstituicaoRadio = findViewById(R.id.metodo_substituicao_radio);
        apenasSubstituiBotao = findViewById(R.id.apenas_substitui_botao);
        
        // Views de resultado
        resultadosTitulo = findViewById(R.id.resultados_titulo);
        resultado1Tabela = findViewById(R.id.resultado1_tabela);
        resultado2Tabela = findViewById(R.id.resultado2_tabela);
        resultado3Tabela = findViewById(R.id.resultado3_tabela);
    }
    
    /**
     * Configura os estados iniciais das views
     */
    private void configurarEstadosIniciais() {
        // Configura o spinner com as operações de matriz por padrão
        setupSpinner(true);
        
        // Configura o estado inicial dos botões de rádio
        if (apenasSubstituiBotao != null) {
            apenasSubstituiBotao.setChecked(true);
        }
        
        if (operacaoMatrizBotao != null) {
            operacaoMatrizBotao.setChecked(true);
        }
        
        // Configura o WindowInsets para lidar com as barras do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    
    /**
     * Configura os listeners das views
     */
    private void configurarListeners() {
        // Listener para mudanças no RadioGroup
        tipoOperacaoRadio.setOnCheckedChangeListener((group, checkedId) -> {
            boolean isMatrizInteira = checkedId == R.id.operacao_matriz_botao;
            boolean isOperacaoLinha = checkedId == R.id.operacao_linha_botao;
            
            // Atualiza o spinner com base na seleção do RadioGroup
            setupSpinner(isMatrizInteira);
            
            // Atualiza a visibilidade dos campos de linha/coluna
            toggleOperacaoCamposVisibilidade(isMatrizInteira);
            
            // Atualiza a visibilidade dos resultados
            atualizarVisibilidadeResultados(isMatrizInteira, isOperacaoLinha);
        });
    }
    
    /**
     * Configura o spinner com as operações apropriadas com base no tipo de operação selecionado
     * @param isMatrizInteira true se for operação em matriz inteira, false para operações de linha/coluna
     */
    private void setupSpinner(boolean isMatrizInteira) {
        ArrayAdapter<String> adapter;
        
        if (isMatrizInteira) {
            adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    operacoesMatrizArray
            );
        } else {
            adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    operacoesColunaArray
            );
        }
        
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qualOperacaoSpinner.setAdapter(adapter);
    }
    
    /**
     * Alterna a visibilidade dos campos de operação de linha/coluna
     * @param isMatrizInteira true para operações em matriz inteira, false para operações de linha/coluna
     */
    private void toggleOperacaoCamposVisibilidade(boolean isMatrizInteira) {
        
        // Se é uma operação de Matriz inteira, oculta todos os campos, senão mostra
        colunaOperacaoTitulo.setVisibility(isMatrizInteira ? View.GONE : View.VISIBLE);
        colunaOperacaoEntrada.setVisibility(isMatrizInteira ? View.GONE : View.VISIBLE);
        colunaSubstituidaTitulo.setVisibility(isMatrizInteira ? View.GONE : View.VISIBLE);
        colunaSubstituidaEntrada.setVisibility(isMatrizInteira ? View.GONE : View.VISIBLE);
        metodoSubstituicaoTitulo.setVisibility(isMatrizInteira ? View.GONE : View.VISIBLE);
        metodoSubstituicaoRadio.setVisibility(isMatrizInteira ? View.GONE : View.VISIBLE);
        }
    
    /**
     * Atualiza a visibilidade dos resultados com base no tipo de operação selecionada
     * @param isMatrizInteira true se for operação sobre matriz
     * @param isOperacaoLinha true se for operação sobre linha
     * || é ou      : é senão      ? é then/então      If é implícito.
     */
    private void atualizarVisibilidadeResultados(boolean isMatrizInteira, boolean isOperacaoLinha) {
        // Sempre visível
        resultadosTitulo.setVisibility(View.VISIBLE);
        resultado3Tabela.setVisibility(View.VISIBLE);

        // Visível se for operação de matriz inteira ou linha
        resultado2Tabela.setVisibility(isOperacaoLinha || isMatrizInteira ? View.VISIBLE : View.GONE);

        // Visível apenas se for operação de matriz inteira
        resultado1Tabela.setVisibility(isMatrizInteira ? View.VISIBLE : View.GONE);

        // Atualiza as restrições de layout
        updateLayoutConstraints(isMatrizInteira, isOperacaoLinha);
    }
    
    /**
     * Helper method to check if a view is currently visible
     * @param view The view to check
     * @return true if view is View.VISIBLE, false otherwise
     */
    private boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }
    
    private void updateLayoutConstraints(boolean mostrarResultado1, boolean mostrarResultado2) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) findViewById(R.id.main));
        
        // resultado1_tabela sempre fica abaixo de resultados_titulo
        constraintSet.connect(R.id.resultado1_tabela, ConstraintSet.TOP, R.id.resultados_titulo, ConstraintSet.BOTTOM, 8);
        
        // resultado2_tabela fica abaixo de resultado1_tabela se estiver visível, senão fica abaixo de resultados_titulo
        if (isVisible(resultado1Tabela)) {
            constraintSet.connect(R.id.resultado2_tabela, ConstraintSet.TOP, R.id.resultado1_tabela, ConstraintSet.BOTTOM, 8);
        } else {
            constraintSet.connect(R.id.resultado2_tabela, ConstraintSet.TOP, R.id.resultados_titulo, ConstraintSet.BOTTOM, 8);
        }
        
        // resultado3_tabela fica abaixo de resultado2_tabela se estiver visível, senão fica abaixo de resultados_titulo
        if (isVisible(resultado2Tabela)) {
            constraintSet.connect(R.id.resultado3_tabela, ConstraintSet.TOP, R.id.resultado2_tabela, ConstraintSet.BOTTOM, 8);
        } else if (isVisible(resultado1Tabela)) {
            constraintSet.connect(R.id.resultado3_tabela, ConstraintSet.TOP, R.id.resultado1_tabela, ConstraintSet.BOTTOM, 8);
        } else {
            constraintSet.connect(R.id.resultado3_tabela, ConstraintSet.TOP, R.id.resultados_titulo, ConstraintSet.BOTTOM, 8);
        }
        
        // Aplica as restrições
        constraintSet.applyTo((ConstraintLayout) findViewById(R.id.main));
    }
}