package com.example.kidapp.Models;

public class ImmunizationRecord {

    private String irID;
    private String vaccineName;
    private int doseNumber;
    private MyDate vdate;
    private String vaccinatorName;
    private String HMOName;
    private String creatorName;


    public ImmunizationRecord() {
    }

    public ImmunizationRecord(String vaccineName, int doseNumber, MyDate vdate, String vaccinatorName, String HMOName, String creatorName) {
        this.vaccineName = vaccineName;
        this.doseNumber = doseNumber;
        this.vdate = vdate;
        this.vaccinatorName = vaccinatorName;
        this.HMOName = HMOName;
        this.creatorName = creatorName;
        this.irID = this.vaccineName + "id" + this.doseNumber;
    }

    public ImmunizationRecord setvdate(MyDate vdate) {
        this.vdate = vdate;
        return this;
    }


    public String getIrID() {
        return irID;
    }

    public ImmunizationRecord setIrID(String irID) {
        this.irID = irID;
        return this;
    }


    public ImmunizationRecord setVdate(MyDate vdate) {
        this.vdate = vdate;
        return this;
    }

    public ImmunizationRecord setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
        return this;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public int getDoseNumber() {

        return doseNumber;
    }

    public ImmunizationRecord setDoseNumber(int doseNumber) {
        this.doseNumber = doseNumber;
        return this;
    }

    public void initIrID() {
        this.irID = this.vaccineName + "id" + this.doseNumber;
    }


    public MyDate getvdate() {
        return vdate;
    }


    public ImmunizationRecord setvdate(int day, int month, int year) {
        this.vdate = new MyDate(day, month, year);
        return this;
    }

    public String getVaccinatorName() {
        return vaccinatorName;
    }

    public ImmunizationRecord setVaccinatorName(String vaccinatorName) {
        this.vaccinatorName = vaccinatorName;
        return this;
    }

    public String getHMOName() {
        return HMOName;
    }

    public ImmunizationRecord setHMOName(String HMOName) {
        this.HMOName = HMOName;
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public ImmunizationRecord setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }
}
