package com.fraud.FraudPrevent.dto;

// Clase que representa las métricas de fraude relacionadas con un usuario

public class FraudMetricsDTO {

	//Si el usuario es nuevo
    private boolean is_new_user;
    
    //Cantidad de rechazos el ultimo dia
    private int qty_rejected_1d;
    
    //Total de montos rechazados los ultimos 7 dias, que será devuelto en USD
    private double total_amt_7d;


    
    public FraudMetricsDTO(boolean is_new_user, int qty_rejected_1d, double total_amt_7d) {
        this.is_new_user = is_new_user;
        this.qty_rejected_1d = qty_rejected_1d;
        this.total_amt_7d = total_amt_7d;
    }

	public boolean isIs_new_user() {
		return is_new_user;
	}
    
    public void setIs_new_user(boolean is_new_user) {
		this.is_new_user = is_new_user;
	}


	public int getQty_rejected_1d() {
		return qty_rejected_1d;
	}


	public void setQty_rejected_1d(int qty_rejected_1d) {
		this.qty_rejected_1d = qty_rejected_1d;
	}


	public double getTotal_amt_7d() {
		return total_amt_7d;
	}


	public void setTotal_amt_7d(double total_amt_7d) {
		this.total_amt_7d = total_amt_7d;
	}
    
   
}
