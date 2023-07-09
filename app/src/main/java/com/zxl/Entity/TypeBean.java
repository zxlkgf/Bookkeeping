package com.zxl.Entity;

/**
 * 表示收入或者支出具体类型的类
 */
public class TypeBean {
    int id;
    String typeName;    //类型名称
    int imageId;    //未被选中时的id
    int sImageId;   //被选中时的id
    int kind;   //收入 1 支出0

    public TypeBean(int id, String typeName, int imageId, int sImageId, int kind) {
        this.id = id;
        this.typeName = typeName;
        this.imageId = imageId;
        this.sImageId = sImageId;
        this.kind = kind;
    }

    public TypeBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getsImageId() {
        return sImageId;
    }

    public void setsImageId(int sImageId) {
        this.sImageId = sImageId;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "TypeBean{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", imageId=" + imageId +
                ", sImageId=" + sImageId +
                ", kind=" + kind +
                '}';
    }
}