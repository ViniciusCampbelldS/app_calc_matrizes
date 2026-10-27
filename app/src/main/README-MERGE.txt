# Calculadora de Matrizes — arquivos mesclados

Este pacote contém os arquivos-fonte e recursos mesclados, não um projeto Android Studio completo. Os arquivos Gradle/build e demais configurações do projeto 2 não foram incluídos porque não vieram nos ZIPs originais.

## Como aplicar

1. Faça uma cópia de segurança do seu projeto Android Studio atual.
2. Mantenha os arquivos `build.gradle`, `settings.gradle`, Gradle wrapper e configurações locais do seu projeto 2.
3. Copie o conteúdo das pastas `java/` e `res/` deste pacote para as pastas correspondentes do módulo `app/src/main/` do seu projeto, substituindo os arquivos de mesmo nome.
4. Confira se o pacote do app continua `com.example.calculadoradematrizes` e se as dependências já existentes incluem AndroidX Navigation, ViewBinding, ConstraintLayout e Material Components.
5. Faça Sync Gradle e compile.

## O que foi mesclado

- Mantida a estrutura de Activity + Fragments + Navigation e a interface Material do projeto 2.
- Adicionada a classe `Matriz` baseada no modelo de dados do projeto 1.
- Adicionada a classe `MatrixCalculator` com parser, validação e operações matemáticas.
- Atualizada a tela principal para usar espaços/tabulações entre colunas e Enter entre linhas (não vírgulas como separadores de coluna).
- Adicionado o botão Resolver operação e ligação entre entradas, seleção da operação e resultado.
- Criado `SecondFragment.java`, pois o grafo de navegação original apontava para esse fragmento, mas o arquivo não estava presente no ZIP recebido.
- A segunda tela agora apresenta instruções sobre as operações em vez de texto de preenchimento.

## Formato de entrada

Exemplo de matriz 2 × 3:

    1 2 3
    4 5 6

Os índices das linhas/colunas são contados a partir de 1. Para soma/multiplicação escalar e operações escalares de linha/coluna, digite um único número no campo “Matriz 2 ou escalar”.

## Operações incluídas

- Soma e subtração de matrizes.
- Soma de escalar a cada elemento.
- Multiplicação de matriz por escalar.
- Multiplicação de matrizes.
- Determinante (matriz quadrada).
- Matriz inversa (matriz quadrada não singular).
- Transposta (matrizes quadradas ou retangulares).
- Operações sobre linha ou coluna: somar origem ao destino, subtrair origem do destino, multiplicar destino por escalar e dividir destino por escalar.

## Observações

- O código Java puro de `Matriz.java` e `MatrixCalculator.java` foi compilado e as operações básicas foram testadas isoladamente. Não foi possível executar uma compilação Android completa sem os arquivos Gradle e SDK/dependências do projeto original.
- A operação “Somar escalar” e a operação “Multiplicação escalar” usam o campo Matriz 2 como número único.
- A divisão por escalar zero é bloqueada; a inversa de matriz singular e operações com dimensões incompatíveis mostram mensagens de erro.
