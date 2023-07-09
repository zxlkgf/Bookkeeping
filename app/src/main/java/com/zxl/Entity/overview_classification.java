package com.zxl.Entity;

public class overview_classification {
    int sImageId;
    String typeName;
    float totalMoney;
    float percent;

    public overview_classification(int sImageId, String typeName, float totalMoney, float percent) {
        this.sImageId = sImageId;
        this.typeName = typeName;
        this.totalMoney = totalMoney;
        this.percent = percent;
    }

    public overview_classification() {
    }

    @Override
    public String toString() {
        return "overview_classification{" +
                "sImageId=" + sImageId +
                ", typeName='" + typeName + '\'' +
                ", totalMoney=" + totalMoney +
                ", percent=" + percent +
                '}';
    }

    public int getsImageId() {
        return sImageId;
    }

    public void setsImageId(int sImageId) {
        this.sImageId = sImageId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
}
