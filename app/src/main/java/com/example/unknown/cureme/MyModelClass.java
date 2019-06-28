package com.example.unknown.cureme;

class MyModelClass {
    String ID, Name, Blood_Group,Sugar,Temperature,Blood_Pressure,HeartBeat;

    public MyModelClass(String ID, String name, String blood_Group, String sugar, String temperature, String blood_Pressure, String heartBeat) {
        this.ID = ID;
        Name = name;
        Blood_Group = blood_Group;
        Sugar = sugar;
        Temperature = temperature;
        Blood_Pressure = blood_Pressure;
        HeartBeat = heartBeat;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBlood_Group() {
        return Blood_Group;
    }

    public void setBlood_Group(String blood_Group) {
        Blood_Group = blood_Group;
    }

    public String getSugar() {
        return Sugar;
    }

    public void setSugar(String sugar) {
        Sugar = sugar;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getBlood_Pressure() {
        return Blood_Pressure;
    }

    public void setBlood_Pressure(String blood_Pressure) {
        Blood_Pressure = blood_Pressure;
    }

    public String getHeartBeat() {
        return HeartBeat;
    }

    public void setHeartBeat(String heartBeat) {
        HeartBeat = heartBeat;
    }
}