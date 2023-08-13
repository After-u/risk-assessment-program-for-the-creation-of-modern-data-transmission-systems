package com.RiskPO.Models;

public class TypeOfModel {
    private String nameOfModel;
    private long idOfModel;

    public TypeOfModel(String nameOfModel, long idOfModel) {
        this.nameOfModel = nameOfModel;
        this.idOfModel = idOfModel;
    }

    public String getNameOfModel() {
        return nameOfModel;
    }

    public void setNameOfModel(String nameOfModel) {
        this.nameOfModel = nameOfModel;
    }

    public long getIdOfModel() {
        return idOfModel;
    }

    public void setIdOfModel(long idOfModel) {
        this.idOfModel = idOfModel;
    }
}
