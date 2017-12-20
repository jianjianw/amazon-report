package com.nhsoft.module.azure.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
@Entity
public class Bizday implements Serializable{
    @Id
    private String bizdayDate;
    private String bizdayYear;
    private Integer bizdayQuarter;
    private String bizdayYearMonth;
    private Integer bizdayMonth;
    private Integer bizdayDayofyear;
    private String bizdayYearweek;
    private Integer bizdayWeekofYear;
    private Integer bizdayDayofweek;
    private String bizdayYearName;
    private String bizdayQuarterName;
    private String bizdayMonthName;
    private String bizdayWeekofyearName;
    private String bizdayDayName;
    private String bizdayDayofweekName;
    private String bizdayYearandmonthName;
    private String bizdayYearandweekName;
    private int bizdayIsthisweek;


    public String getBizdayDate() {
        return bizdayDate;
    }

    public void setBizdayDate(String bizdayDate) {
        this.bizdayDate = bizdayDate;
    }

    public String getBizdayYear() {
        return bizdayYear;
    }

    public void setBizdayYear(String bizdayYear) {
        this.bizdayYear = bizdayYear;
    }

    public Integer getBizdayQuarter() {
        return bizdayQuarter;
    }

    public void setBizdayQuarter(Integer bizdayQuarter) {
        this.bizdayQuarter = bizdayQuarter;
    }

    public String getBizdayYearMonth() {
        return bizdayYearMonth;
    }

    public void setBizdayYearMonth(String bizdayYearMonth) {
        this.bizdayYearMonth = bizdayYearMonth;
    }

    public Integer getBizdayMonth() {
        return bizdayMonth;
    }

    public void setBizdayMonth(Integer bizdayMonth) {
        this.bizdayMonth = bizdayMonth;
    }

    public Integer getBizdayDayofyear() {
        return bizdayDayofyear;
    }

    public void setBizdayDayofyear(Integer bizdayDayofyear) {
        this.bizdayDayofyear = bizdayDayofyear;
    }

    public String getBizdayYearweek() {
        return bizdayYearweek;
    }

    public void setBizdayYearweek(String bizdayYearweek) {
        this.bizdayYearweek = bizdayYearweek;
    }

    public Integer getBizdayWeekofYear() {
        return bizdayWeekofYear;
    }

    public void setBizdayWeekofYear(Integer bizdayWeekofYear) {
        this.bizdayWeekofYear = bizdayWeekofYear;
    }

    public Integer getBizdayDayofweek() {
        return bizdayDayofweek;
    }

    public void setBizdayDayofweek(Integer bizdayDayofweek) {
        this.bizdayDayofweek = bizdayDayofweek;
    }

    public String getBizdayYearName() {
        return bizdayYearName;
    }

    public void setBizdayYearName(String bizdayYearName) {
        this.bizdayYearName = bizdayYearName;
    }

    public String getBizdayQuarterName() {
        return bizdayQuarterName;
    }

    public void setBizdayQuarterName(String bizdayQuarterName) {
        this.bizdayQuarterName = bizdayQuarterName;
    }

    public String getBizdayMonthName() {
        return bizdayMonthName;
    }

    public void setBizdayMonthName(String bizdayMonthName) {
        this.bizdayMonthName = bizdayMonthName;
    }

    public String getBizdayWeekofyearName() {
        return bizdayWeekofyearName;
    }

    public void setBizdayWeekofyearName(String bizdayWeekofyearName) {
        this.bizdayWeekofyearName = bizdayWeekofyearName;
    }

    public String getBizdayDayName() {
        return bizdayDayName;
    }

    public void setBizdayDayName(String bizdayDayName) {
        this.bizdayDayName = bizdayDayName;
    }

    public String getBizdayDayofweekName() {
        return bizdayDayofweekName;
    }

    public void setBizdayDayofweekName(String bizdayDayofweekName) {
        this.bizdayDayofweekName = bizdayDayofweekName;
    }

    public String getBizdayYearandmonthName() {
        return bizdayYearandmonthName;
    }

    public void setBizdayYearandmonthName(String bizdayYearandmonthName) {
        this.bizdayYearandmonthName = bizdayYearandmonthName;
    }

    public String getBizdayYearandweekName() {
        return bizdayYearandweekName;
    }

    public void setBizdayYearandweekName(String bizdayYearandweekName) {
        this.bizdayYearandweekName = bizdayYearandweekName;
    }

    public int getBizdayIsthisweek() {
        return bizdayIsthisweek;
    }

    public void setBizdayIsthisweek(int bizdayIsthisweek) {
        this.bizdayIsthisweek = bizdayIsthisweek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bizday bizday = (Bizday) o;

        if (bizdayIsthisweek != bizday.bizdayIsthisweek) return false;
        if (bizdayDate != null ? !bizdayDate.equals(bizday.bizdayDate) : bizday.bizdayDate != null) return false;
        if (bizdayYear != null ? !bizdayYear.equals(bizday.bizdayYear) : bizday.bizdayYear != null) return false;
        if (bizdayQuarter != null ? !bizdayQuarter.equals(bizday.bizdayQuarter) : bizday.bizdayQuarter != null)
            return false;
        if (bizdayYearMonth != null ? !bizdayYearMonth.equals(bizday.bizdayYearMonth) : bizday.bizdayYearMonth != null)
            return false;
        if (bizdayMonth != null ? !bizdayMonth.equals(bizday.bizdayMonth) : bizday.bizdayMonth != null) return false;
        if (bizdayDayofyear != null ? !bizdayDayofyear.equals(bizday.bizdayDayofyear) : bizday.bizdayDayofyear != null)
            return false;
        if (bizdayYearweek != null ? !bizdayYearweek.equals(bizday.bizdayYearweek) : bizday.bizdayYearweek != null)
            return false;
        if (bizdayWeekofYear != null ? !bizdayWeekofYear.equals(bizday.bizdayWeekofYear) : bizday.bizdayWeekofYear != null)
            return false;
        if (bizdayDayofweek != null ? !bizdayDayofweek.equals(bizday.bizdayDayofweek) : bizday.bizdayDayofweek != null)
            return false;
        if (bizdayYearName != null ? !bizdayYearName.equals(bizday.bizdayYearName) : bizday.bizdayYearName != null)
            return false;
        if (bizdayQuarterName != null ? !bizdayQuarterName.equals(bizday.bizdayQuarterName) : bizday.bizdayQuarterName != null)
            return false;
        if (bizdayMonthName != null ? !bizdayMonthName.equals(bizday.bizdayMonthName) : bizday.bizdayMonthName != null)
            return false;
        if (bizdayWeekofyearName != null ? !bizdayWeekofyearName.equals(bizday.bizdayWeekofyearName) : bizday.bizdayWeekofyearName != null)
            return false;
        if (bizdayDayName != null ? !bizdayDayName.equals(bizday.bizdayDayName) : bizday.bizdayDayName != null)
            return false;
        if (bizdayDayofweekName != null ? !bizdayDayofweekName.equals(bizday.bizdayDayofweekName) : bizday.bizdayDayofweekName != null)
            return false;
        if (bizdayYearandmonthName != null ? !bizdayYearandmonthName.equals(bizday.bizdayYearandmonthName) : bizday.bizdayYearandmonthName != null)
            return false;
        return bizdayYearandweekName != null ? bizdayYearandweekName.equals(bizday.bizdayYearandweekName) : bizday.bizdayYearandweekName == null;
    }

    @Override
    public int hashCode() {
        int result = bizdayDate != null ? bizdayDate.hashCode() : 0;
        result = 31 * result + (bizdayYear != null ? bizdayYear.hashCode() : 0);
        result = 31 * result + (bizdayQuarter != null ? bizdayQuarter.hashCode() : 0);
        result = 31 * result + (bizdayYearMonth != null ? bizdayYearMonth.hashCode() : 0);
        result = 31 * result + (bizdayMonth != null ? bizdayMonth.hashCode() : 0);
        result = 31 * result + (bizdayDayofyear != null ? bizdayDayofyear.hashCode() : 0);
        result = 31 * result + (bizdayYearweek != null ? bizdayYearweek.hashCode() : 0);
        result = 31 * result + (bizdayWeekofYear != null ? bizdayWeekofYear.hashCode() : 0);
        result = 31 * result + (bizdayDayofweek != null ? bizdayDayofweek.hashCode() : 0);
        result = 31 * result + (bizdayYearName != null ? bizdayYearName.hashCode() : 0);
        result = 31 * result + (bizdayQuarterName != null ? bizdayQuarterName.hashCode() : 0);
        result = 31 * result + (bizdayMonthName != null ? bizdayMonthName.hashCode() : 0);
        result = 31 * result + (bizdayWeekofyearName != null ? bizdayWeekofyearName.hashCode() : 0);
        result = 31 * result + (bizdayDayName != null ? bizdayDayName.hashCode() : 0);
        result = 31 * result + (bizdayDayofweekName != null ? bizdayDayofweekName.hashCode() : 0);
        result = 31 * result + (bizdayYearandmonthName != null ? bizdayYearandmonthName.hashCode() : 0);
        result = 31 * result + (bizdayYearandweekName != null ? bizdayYearandweekName.hashCode() : 0);
        result = 31 * result + bizdayIsthisweek;
        return result;
    }
}
