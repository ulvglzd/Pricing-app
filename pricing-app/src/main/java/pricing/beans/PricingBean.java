/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricing.beans;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import java.io.IOException;
import java.io.Serializable;
import pricing.business.PricingEnterpriseBean;
import pricing.helper.PriceConverter;

/**
 *
 * @author Ulvi
 */
@SessionScoped//it specifies that a new instance of the bean is created and destroyed for every Http session
@Named /*by doing it, I give a unique name to the CDI bean, so bean instance can
        be located by this name. Then, the bean can be used directly in JSF using
        jakarta experssion language */
public class PricingBean implements Serializable { 

    private static final long serialVersionUID = 1L;
//bean class should implement serializable interface to enable session preservation
    
    private Integer todaysPriceChange;
    private Double priceChangeForConversion;
    private String selectedItem;
    private Double oldPrice;
    private Double newPrice;
    private Double currentPrice;
    private Double exchangeRate=1d;
    private String currency ="";
    
    @Inject
    PriceConverter priceConverter;
    
    @EJB
    private PricingEnterpriseBean pricingEnterpriseBean;
    
    public void lookUpPriceChange(){
        try {
            
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:8080/daily-price-change/webapi/dailyPriceChange");
            Integer response = target.request().get(Integer.class);
            todaysPriceChange = response;
            priceChangeForConversion = Double.valueOf(String.valueOf(response)) *exchangeRate;
            } catch (Exception e) {
        }
     }
    
    public void showCurrentPrice(){
        currentPrice = pricingEnterpriseBean.getCurrentPrice(selectedItem)*getExchangeRate();
    }
    
   
    public void applyPriceChange(){
        
        pricingEnterpriseBean.applyPriceChange(selectedItem, todaysPriceChange);
        
        Double[] prices = pricingEnterpriseBean.getOldAndNewPrices(selectedItem);
        
                
        if(prices != null){
           oldPrice = prices[0];
           newPrice = prices[1]; 
        }
        
    }
    
    
    public void defineExchangeRate() throws IOException{
        
        switch (currency) {
            case "AZN":
                exchangeRate = priceConverter.convertPriceFromDollar(currency);
                break;
            case "USD":
                exchangeRate = 1d;
                break;
            case "TRY":
            exchangeRate = priceConverter.convertPriceFromDollar(currency);
            break;
            case "EUR":
            exchangeRate = priceConverter.convertPriceFromDollar(currency);
            break;
            default:
                exchangeRate = 1d;
                break;
        }
        
    }
    
    
    public Integer getTodaysPriceChange() {
        return todaysPriceChange;
    }

    public void setTodaysPriceChange(Integer todaysPriceChange) {
        this.todaysPriceChange = todaysPriceChange;
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Double newPrice) {
        this.newPrice = newPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public String getCurrency() {
        return currency;
    }

    public PriceConverter getPriceConverter() {
        return priceConverter;
    }

    public Double getPriceChangeForConversion() {
        return priceChangeForConversion;
    }

    public void setPriceChangeForConversion(Double priceChangeForConversion) {
        this.priceChangeForConversion = priceChangeForConversion;
    }
    
    
    
    
    
    
    
    
}
