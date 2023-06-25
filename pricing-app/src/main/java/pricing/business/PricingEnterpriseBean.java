/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package pricing.business;

import jakarta.ejb.Stateful;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.Calendar;
import java.util.List;
import pricing.entity.Price;



/**
 *
 * @author Ulvi
 */
@Stateless
public class PricingEnterpriseBean {

    @PersistenceContext
    private EntityManager em;
    
        
    private Double getLastPrice(String itemCode) {
        String stringQuery = "SELECT p.price FROM Price p WHERE p.itemCode = ? ORDER BY "
                + "p.date DESC FETCH FIRST 1 ROWS ONLY";     
        
        Query q = em.createNativeQuery(stringQuery);
        q.setParameter(1, itemCode);
               
        return (Double)q.getSingleResult();
    }
    
        
    private List<Price> getLastTwoPrices(String itemCode) {
        String stringQuery = "SELECT p.id, p.itemCode, p.price, p.date FROM Price p "
                + "WHERE p.itemCode = ? ORDER BY "
                + "p.date DESC FETCH FIRST 2 ROWS ONLY";
        
        Query q = em.createNativeQuery(stringQuery, Price.class);
        q.setParameter(1, itemCode);
        
        return q.getResultList();
    }
    
    public Double getCurrentPrice(String itemCode){
        return getLastPrice(itemCode);
    }
    
    public void saveLastPriceChange(String itemCode, Double newPrice) {
        Price price = new Price(itemCode, newPrice, Calendar.getInstance());
        em.persist(price);
    }
    
    public void applyPriceChange(String itemCode, int priceChange) {
        Double lastPrice = getLastPrice(itemCode);
        
        Double newPrice = lastPrice + (priceChange);
        
        saveLastPriceChange(itemCode, newPrice);
    }
    
    public Double[] getOldAndNewPrices(String itemCode) {
        Double[] prices = null;
        
        List<Price> lastTwoPrices = getLastTwoPrices(itemCode);
        
        if(lastTwoPrices != null && lastTwoPrices.size() == 2) {
            prices = new Double[2];
            prices[0] = lastTwoPrices.get(1).getPrice();
            prices[1] = lastTwoPrices.get(0).getPrice();
            
        }
        return prices;
    }
}
