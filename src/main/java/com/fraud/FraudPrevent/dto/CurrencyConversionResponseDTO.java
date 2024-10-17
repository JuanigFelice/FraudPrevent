package com.fraud.FraudPrevent.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

//Clase de Respuesta de Conversion de la API Externa
public class CurrencyConversionResponseDTO {
		
	@JsonProperty("success")
    private boolean success;

    @JsonProperty("query")
    private Query query;

    @JsonProperty("info")
    private Info info;

    @JsonProperty("date")
    private String date;

    @JsonProperty("result")
    private double result;


    public CurrencyConversionResponseDTO(boolean success, double result) {
		super();
		this.success = success;
		this.result = result;
	}

    public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	
	public static class Query {
        @JsonProperty("from")
        private String from;

        @JsonProperty("to")
        private String to;

        @JsonProperty("amount")
        private double amount;

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}

		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}
    }
	
	
	
    public static class Info {
        @JsonProperty("timestamp")
        private long timestamp;

        @JsonProperty("rate")
        private double rate;

		public long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}

		public double getRate() {
			return rate;
		}

		public void setRate(double rate) {
			this.rate = rate;
		}

		
       
    }
    
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
