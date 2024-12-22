# Previsão da Casa de Hogwarts

## Descrição  
Este aplicativo Android prevê a casa de Hogwarts de um usuário com base em características definidas por meio de barras deslizantes (SeekBars). Ele utiliza um modelo TensorFlow Lite (TFLite) treinado para classificar os usuários em uma das quatro casas: Grifinória, Sonserina, Corvinal e Lufa-Lufa.

## Funcionalidades  
- Permite que o usuário insira valores de características como coragem, ambição, criatividade, etc., usando SeekBars.  
- Realiza inferência no modelo TFLite para prever a casa de Hogwarts.  
- Exibe a casa prevista na interface do aplicativo.

## Tecnologias Utilizadas  
- **Android**: Desenvolvimento do aplicativo.  
- **TensorFlow Lite**: Modelo de Machine Learning para classificação.  
- **Java**: Linguagem de programação principal.

## Como Funciona  
1. O usuário ajusta os valores de cada característica usando as SeekBars.  
2. Os valores são normalizados e enviados como entrada ao modelo TensorFlow Lite.  
3. O modelo retorna uma matriz com as probabilidades das casas.  
4. O aplicativo interpreta a saída para determinar a casa com maior probabilidade.  

## Telas

