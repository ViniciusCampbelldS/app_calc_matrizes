package com.example.calc_matrizes;

/**
 * Classe que representa uma matriz matemática com operações básicas.
 * Armazena os valores em uma estrutura de array bidimensional e fornece
 * métodos para acessar e modificar seus elementos.
 */
public class Matriz {
    // Array bidimensional para armazenar os valores da matriz
    private double[][] elemento;
    // Número de Linhas e Nº de Colunas da matriz
    private int nLinhas;
    private int nColunas;

    /**
     * Construtor que cria uma nova matriz com o número especificado de Linhas e Colunas.
     * @param nLinhas Número de Linhas da matriz (deve ser maior que 0)
     * @param nColunas Número de Colunas da matriz (deve ser maior que 0)
     */
    public Matriz(int nLinhas, int nColunas) {
        this.nLinhas = nLinhas;
        this.nColunas = nColunas;
        this.elemento = new double[nLinhas][nColunas];
    }

    /**
     * Define o valor de uma posição específica na matriz.
     * @param linha Índice da linha (começando em 0)
     * @param coluna Índice da coluna (começando em 0)
     * @param valor Valor a ser armazenado na posição especificada
     */
    public void setValor(int linha, int coluna, double valor) {
        // Verifica se os índices estão dentro dos limites da matriz
        if (linha >= 0 && linha < nLinhas && coluna >= 0 && coluna < nColunas) {
            elemento[linha][coluna] = valor;
        }
        // Se os índices estiverem fora dos limites, a operação é ignorada
    }

    /**
     * Obtém o valor de uma posição específica na matriz.
     * @param linha Índice da linha (começando em 0)
     * @param coluna Índice da coluna (começando em 0)
     * @return O valor armazenado na posição especificada, ou 0 se os índices forem inválidos
     */
    /**
     * Obtém o valor de uma posição específica na matriz.
     * @param linha Índice da linha (começando em 0)
     * @param coluna Índice da coluna (começando em 0)
     * @return O valor armazenado na posição especificada
     * @throws IllegalArgumentException se os índices estiverem fora dos limites da matriz
     */
    public double getValor(int linha, int coluna) {
        // Verifica se os índices estão dentro dos limites da matriz
        if (linha < 0 || linha >= nLinhas || coluna < 0 || coluna >= nColunas) {
            throw new IllegalArgumentException(
                String.format("Não existe elemento na linha %d e coluna %d da matriz. " +
                            "A matriz tem dimensões %dx%d.", 
                            linha, coluna, nLinhas, nColunas)
            );
        }
        return elemento[linha][coluna];
    }

    /**
     * Retorna o número de Linhas da matriz.
     * @return Número de Linhas da matriz
     */
    public int getnLinhas() {
        return nLinhas;
    }

    /**
     * Retorna o número de Colunas da matriz.
     * @return Número de Colunas da matriz
     */
    public int getnColunas() {
        return nColunas;
    }
}
