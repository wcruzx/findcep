package com.viacep.findcep.com.services;

import com.google.gson.Gson;
import com.viacep.findcep.com.models.CEP;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CEPService {

    public CEP consultarCEP(String cep) {
        try {
            cep = cep.replaceAll("\\D", "");

            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Falha na conexão HTTP: " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            conn.disconnect();

            Gson gson = new Gson();
            return gson.fromJson(response.toString(), CEP.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public double calcularFrete(String estado) {
        switch (estado.toUpperCase()) {
            case "SP":
            case "RJ":
            case "ES":
            case "MG":
                return 7.85;
            case "GO":
            case "MT":
            case "MS":
            case "DF":
                return 12.50;
            case "MA":
            case "PI":
            case "CE":
            case "RN":
            case "PE":
            case "PB":
            case "SE":
            case "AL":
            case "BA":
                return 15.98;
            case "PR":
            case "RS":
            case "SC":
                return 17.30;
            default:
                return 20.83;
        }
    }
}

