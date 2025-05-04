package ConversorDeMonedas.modelo;

import com.google.gson.annotations.SerializedName;

public class RespuestaTipoDeCambio {
    @SerializedName("result")
    private String resultado;
    
    @SerializedName("conversion_rates")
    private TasasDeCambio tasasDeCambio;
    
    public String getResultado() {
        return resultado;
    }
    
    public TasasDeCambio getTasasDeCambio() {
        return tasasDeCambio;
    }
    
    public static class TasasDeCambio {
        @SerializedName("USD")
        private double usd;
        @SerializedName("MXN")
        private double mxn;
        @SerializedName("ARS")
        private double ars;
        @SerializedName("BOB")
        private double bob;
        @SerializedName("BRL")
        private double brl;
        @SerializedName("CLP")
        private double clp;
        @SerializedName("COP")
        private double cop;
        
        public double getUsd() {
            return usd;
        }
        
        public double getMxn() {
            return mxn;
        }
        
        public double getArs() {
            return ars;
        }
        
        public double getBob() {
            return bob;
        }
        
        public double getBrl() {
            return brl;
        }
        
        public double getClp() {
            return clp;
        }
        
        public double getCop() {
            return cop;
        }
    }
}