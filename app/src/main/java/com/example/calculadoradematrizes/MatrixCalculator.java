package com.example.calculadoradematrizes;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/** Parser e operações matemáticas da calculadora. Índices de interface são 1-based. */
public final class MatrixCalculator {
    private static final double EPS = 1e-10;
    private MatrixCalculator() {}

    public static Matriz parse(String texto) {
        if (texto == null || texto.trim().isEmpty())
            throw new IllegalArgumentException("Digite uma matriz.");
        String[] linhas = texto.trim().split("\\R");
        String[][] tokens = new String[linhas.length][];
        int colunas = -1;
        for (int i = 0; i < linhas.length; i++) {
            String linha = linhas[i].trim();
            if (linha.isEmpty()) throw new IllegalArgumentException("Há uma linha vazia na matriz.");
            // Espaços ou tabulações separam colunas. Vírgula decimal é aceita dentro de um número.
            tokens[i] = linha.split("\\s+");
            if (colunas == -1) colunas = tokens[i].length;
            if (tokens[i].length != colunas)
                throw new IllegalArgumentException("Todas as linhas precisam ter o mesmo número de elementos.");
        }
        Matriz m = new Matriz(linhas.length, colunas);
        for (int i = 0; i < linhas.length; i++) {
            for (int j = 0; j < colunas; j++) {
                try {
                    m.setValor(i, j, Double.parseDouble(tokens[i][j].replace(',', '.')));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Valor inválido: " + tokens[i][j]);
                }
            }
        }
        return m;
    }

    public static Matriz add(Matriz a, Matriz b, double fatorB) {
        sameSize(a,b);
        Matriz r = new Matriz(a.getnLinhas(), a.getnColunas());
        for (int i=0;i<a.getnLinhas();i++) for(int j=0;j<a.getnColunas();j++)
            r.setValor(i,j,a.getValor(i,j)+fatorB*b.getValor(i,j));
        return r;
    }

    public static Matriz scalar(Matriz a, double k, boolean add) {
        Matriz r = new Matriz(a.getnLinhas(),a.getnColunas());
        for(int i=0;i<a.getnLinhas();i++) for(int j=0;j<a.getnColunas();j++)
            r.setValor(i,j, add ? a.getValor(i,j)+k : a.getValor(i,j)*k);
        return r;
    }

    public static Matriz multiply(Matriz a, Matriz b) {
        if(a.getnColunas()!=b.getnLinhas())
            throw new IllegalArgumentException("Para multiplicar, as colunas da Matriz 1 devem ser iguais às linhas da Matriz 2.");
        Matriz r=new Matriz(a.getnLinhas(),b.getnColunas());
        for(int i=0;i<a.getnLinhas();i++) for(int j=0;j<b.getnColunas();j++) {
            double sum=0;
            for(int k=0;k<a.getnColunas();k++) sum+=a.getValor(i,k)*b.getValor(k,j);
            r.setValor(i,j,sum);
        }
        return r;
    }

    public static double determinant(Matriz a) {
        square(a);
        int n=a.getnLinhas();
        double[][] m=toArray(a);
        double det=1;
        for(int col=0;col<n;col++) {
            int pivot=col;
            for(int row=col+1;row<n;row++)
                if(Math.abs(m[row][col])>Math.abs(m[pivot][col])) pivot=row;
            if(Math.abs(m[pivot][col])<EPS) return 0;
            if(pivot!=col) { double[] tmp=m[pivot];m[pivot]=m[col];m[col]=tmp;det=-det; }
            double p=m[col][col]; det*=p;
            for(int row=col+1;row<n;row++) {
                double f=m[row][col]/p;
                for(int j=col+1;j<n;j++) m[row][j]-=f*m[col][j];
            }
        }
        return det;
    }

    public static Matriz inverse(Matriz a) {
        square(a);
        int n=a.getnLinhas();
        double[][] aug=new double[n][2*n];
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) aug[i][j]=a.getValor(i,j);
            aug[i][n+i]=1;
        }
        for(int col=0;col<n;col++) {
            int pivot=col;
            for(int row=col+1;row<n;row++)
                if(Math.abs(aug[row][col])>Math.abs(aug[pivot][col])) pivot=row;
            if(Math.abs(aug[pivot][col])<EPS)
                throw new IllegalArgumentException("A matriz não possui inversa (determinante igual a zero).");
            double[] tmp=aug[pivot];aug[pivot]=aug[col];aug[col]=tmp;
            double divisor=aug[col][col];
            for(int j=0;j<2*n;j++) aug[col][j]/=divisor;
            for(int row=0;row<n;row++) if(row!=col) {
                double f=aug[row][col];
                for(int j=0;j<2*n;j++) aug[row][j]-=f*aug[col][j];
            }
        }
        Matriz r=new Matriz(n,n);
        for(int i=0;i<n;i++) for(int j=0;j<n;j++) r.setValor(i,j,aug[i][n+j]);
        return r;
    }

    public static Matriz transpose(Matriz a) {
        Matriz r=new Matriz(a.getnColunas(),a.getnLinhas());
        for(int i=0;i<a.getnLinhas();i++) for(int j=0;j<a.getnColunas();j++)
            r.setValor(j,i,a.getValor(i,j));
        return r;
    }

    public static Matriz rowColumnOperation(Matriz input, boolean rowMode, int source1Based,
                                             int target1Based, String operation, double scalar) {
        Matriz r=input.copiar();
        int limit=rowMode?r.getnLinhas():r.getnColunas();
        if(source1Based<1 || source1Based>limit || target1Based<1 || target1Based>limit)
            throw new IllegalArgumentException("Os índices devem estar entre 1 e " + limit + ".");
        int source=source1Based-1, target=target1Based-1;
        if(operation.startsWith("Multiplicar destino")) {
            for(int k=0;k<(rowMode?r.getnColunas():r.getnLinhas());k++)
                setLineValue(r,rowMode,target,k,getLineValue(r,rowMode,target,k)*scalar);
        } else if(operation.startsWith("Dividir destino")) {
            if(Math.abs(scalar)<EPS) throw new IllegalArgumentException("Não é possível dividir por zero.");
            for(int k=0;k<(rowMode?r.getnColunas():r.getnLinhas());k++)
                setLineValue(r,rowMode,target,k,getLineValue(r,rowMode,target,k)/scalar);
        } else {
            double sign=operation.startsWith("Subtrair")?-1:1;
            for(int k=0;k<(rowMode?r.getnColunas():r.getnLinhas());k++)
                setLineValue(r,rowMode,target,k,getLineValue(r,rowMode,target,k)+sign*getLineValue(r,rowMode,source,k));
        }
        return r;
    }

    private static double getLineValue(Matriz m, boolean row, int line, int k) {
        return row ? m.getValor(line,k) : m.getValor(k,line);
    }
    private static void setLineValue(Matriz m, boolean row, int line, int k, double v) {
        m.setValor(row?line:k,row?k:line,v);
    }
    private static void sameSize(Matriz a,Matriz b) {
        if(a.getnLinhas()!=b.getnLinhas()||a.getnColunas()!=b.getnColunas())
            throw new IllegalArgumentException("As matrizes precisam ter as mesmas dimensões.");
    }
    private static void square(Matriz a) {
        if(a.getnLinhas()!=a.getnColunas())
            throw new IllegalArgumentException("Esta operação exige uma matriz quadrada.");
    }
    private static double[][] toArray(Matriz a) {
        double[][] m=new double[a.getnLinhas()][a.getnColunas()];
        for(int i=0;i<a.getnLinhas();i++) for(int j=0;j<a.getnColunas();j++) m[i][j]=a.getValor(i,j);
        return m;
    }
    public static String format(Matriz m) {
        StringBuilder out=new StringBuilder();
        for(int i=0;i<m.getnLinhas();i++) {
            for(int j=0;j<m.getnColunas();j++) {
                if(j>0) out.append("   ");
                out.append(formatNumber(m.getValor(i,j)));
            }
            if(i<m.getnLinhas()-1) out.append('\n');
        }
        return out.toString();
    }
    public static String formatNumber(double value) {
        if(Math.abs(value)<EPS) value=0;
        DecimalFormat df=new DecimalFormat("0.##########", DecimalFormatSymbols.getInstance(Locale.US));
        return df.format(value);
    }
}
