package com.example.casahogwarts;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileInputStream;
import java.io.IOException;


import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button btnPrever;
    SeekBar seekCorajoso, seekOusado, seekAmbicioso, seekAstuto, seekInteligente, seekCriativo, seekLeal, seekPaciente;
    TextView txtCasa;
    private Interpreter interpreter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        inicializarComponentes();

        try {
            interpreter = new Interpreter(carregarModelo());
            Log.d("MainActivity", "Modelo carregado com sucesso");
        } catch (IOException e) {
            Log.e("MainActivity", "Erro ao carregar modelo TFLite", e);
        }

        btnPrever.setOnClickListener(view -> {
            preverCasa();
        });
    }

    private void inicializarComponentes() {
        // Inicializa componentes
        btnPrever = findViewById(R.id.buttonPreverCasa);
        seekCorajoso = findViewById(R.id.seekBarCorajoso);
        seekOusado = findViewById(R.id.seekBarOusado);
        seekAmbicioso = findViewById(R.id.seekBarAmbicioso);
        seekAstuto = findViewById(R.id.seekBarAstuto);
        seekInteligente = findViewById(R.id.seekBarInteligente);
        seekCriativo = findViewById(R.id.seekBarCriativo);
        seekLeal = findViewById(R.id.seekBarLeal);
        seekPaciente = findViewById(R.id.seekBarPaciente);
        txtCasa = findViewById(R.id.textPredictedHouse);
    }

    private MappedByteBuffer carregarModelo() throws IOException {
        // Abre o AssetFileDescriptor diretamente a partir dos assets
        AssetFileDescriptor assetFileDescriptor = getAssets().openFd("model_hogwarts.tflite");
        FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = fileInputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long declaredLength = assetFileDescriptor.getDeclaredLength();

        // Mapeia o arquivo para leitura
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private void preverCasa() {
        Log.d("MainActivity", "Prever casa chamado");

        // Obtém os valores das características digitadas pelo usuário
        float corajoso = seekCorajoso.getProgress();
        float ousado = seekOusado.getProgress();
        float ambicioso = seekAmbicioso.getProgress();
        float astuto = seekAstuto.getProgress();
        float inteligente = seekInteligente.getProgress();
        float criativo = seekCriativo.getProgress();
        float leal = seekLeal.getProgress();
        float paciente = seekPaciente.getProgress();


        // Normaliza os valores para 0.0 a 1.0 (se necessário)
        float[] inputValues = {
                corajoso / 10.0f,
                ousado / 10.0f,
                ambicioso / 10.0f,
                astuto / 10.0f,
                inteligente / 10.0f,
                criativo / 10.0f,
                leal / 10.0f,
                paciente / 10.0f
        };

        try {
            // Cria os inputs para o modelo
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 8}, DataType.FLOAT32);
            inputFeature0.loadArray(inputValues);

            // Executa a inferência do modelo
            TensorBuffer outputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 4}, DataType.FLOAT32);
            interpreter.run(inputFeature0.getBuffer(), outputFeature0.getBuffer());

            // Obtém o resultado da saída
            float[] outputArray = outputFeature0.getFloatArray();

            // Interpreta o resultado (Encontra o índice com a maior probabilidade)
            int casaPrevista = interpretarResultado(outputArray);
            Log.d("MainActivity", "Saída do modelo: " + Arrays.toString(outputArray));

            txtCasa.setText("A sua casa é: " + CasaHogwarts.getNomeCasa(casaPrevista));
            txtCasa.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            Log.e("MainActivity", "Erro ao fazer a previsão:", e);
            Toast.makeText(this, "Erro ao fazer a previsão.", Toast.LENGTH_SHORT).show();
        }
    }

    private int interpretarResultado(float[] outputArray) {
        int indiceMax = 0;
        for (int i = 1; i < outputArray.length; i++) {
            if (outputArray[i] > outputArray[indiceMax]) {
                indiceMax = i;
            }
        }
        return indiceMax;
    }

}