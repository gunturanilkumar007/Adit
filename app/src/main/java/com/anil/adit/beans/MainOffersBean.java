package com.anil.adit.beans;

import java.io.Serializable;
import java.util.List;

public class MainOffersBean implements Serializable {
    private String ctagory;
    private List<OffersBean> offers;

    public String getCtagory() {
        return ctagory;
    }

    public void setCtagory(String ctagory) {
        this.ctagory = ctagory;
    }

    public List<OffersBean> getOffers() {
        return offers;
    }

    public void setOffers(List<OffersBean> offers) {
        this.offers = offers;
    }
}
