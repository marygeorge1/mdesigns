package com.sparta.mdesigns.dtos;

public class PriceRangeDTO {

    private boolean priceRange1;
    private boolean priceRange2;
    private boolean priceRange3;

    public boolean isPriceRange1() {
        return priceRange1;
    }

    public void setPriceRange1(boolean priceRange1) {
        this.priceRange1 = priceRange1;
    }

    public boolean isPriceRange2() {
        return priceRange2;
    }

    public void setPriceRange2(boolean priceRange2) {
        this.priceRange2 = priceRange2;
    }

    public boolean isPriceRange3() {
        return priceRange3;
    }

    public void setPriceRange3(boolean priceRange3) {
        this.priceRange3 = priceRange3;
    }


    @Override
    public String toString() {
        return "PriceRangeDTO{" +
                "priceRange1=" + priceRange1 +
                ", priceRange2=" + priceRange2 +
                ", priceRange3=" + priceRange3 +
                '}';
    }
}
