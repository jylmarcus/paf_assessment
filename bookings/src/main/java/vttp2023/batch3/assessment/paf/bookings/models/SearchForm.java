package vttp2023.batch3.assessment.paf.bookings.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SearchForm {

    @NotNull(message="Country cannot be null")
    private String country;

    @NotNull(message = "Cannot have no guests")
    @Min(value=1, message = "Cannot have less than 1 guest")
    @Max(value=10, message = "Cannot have more than 10 guests")
    private Integer guests;

    @NotNull(message = "Min cannot be null")
    @Min(value=1, message = "Cannot be less than 1")
    @Max(value=10000, message = "We do not have accomdations above 10000")
    private Integer min;

    @NotNull(message = "Max cannot be null")
    @Min(value=1, message = "Cannot be less than 1")
    @Max(value=10000, message = "We do not have accomodations above 10000")
    private Integer max;

    private Integer range;

    public SearchForm(){

    }

    public SearchForm(String country, Integer guests, Integer min, Integer max, Integer range) {
        this.country = country;
        this.guests = guests;
        this.min = min;
        this.max = max;
        this.range = max-min;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getGuests() {
        return guests;
    }

    public void setGuests(Integer guests) {
        this.guests = guests;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public boolean checkRange(){
        if(this.max-this.min <= 0) {
            return false;
        }
        return true;
    }
}
