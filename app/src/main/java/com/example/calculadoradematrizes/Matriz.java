package com.example.calculadoradematrizes;

/** Matriz numérica mutável usada pela calculadora. */
public class Matriz {
    private final double[][] elementos;

    public Matriz(int linhas, int colunas) {
        if (linhas < 1 || colunas < 1) {
            throw new IllegalArgumentException("A matriz precisa ter pelo menos uma linha e uma coluna.");
        }
        elementos = new double[linhas][colunas];
    }

    public int getnLinhas() { return elementos.length; }
    public int getnColunas() { return elementos[0].length; }

    public void setValor(int linha, int coluna, double valor) {
        elementos[linha][coluna] = valor;
    }

    public double getValor(int linha, int coluna) {
        return elementos[linha][coluna];
    }

    public Matriz copiar() {
        Matriz copia = new Matriz(getnLinhas(), getnColunas());
        for (int i = 0; i < getnLinhas(); i++)
            for (int j = 0; j < getnColunas(); j++)
                copia.setValor(i, j, getValor(i, j));
        return copia;
    }
}
