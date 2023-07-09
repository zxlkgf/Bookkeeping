package com.zxl.Entity;

/**
 * 表示单条用户消费记录
 */
public class AccountBean {
     int id;
     String typeName;    //类型
     int sImageId;   //被选中类型图片
     String notes;  //备注
     float money;    //价格
     String time ;   //保存时间字符串
     int year;      //年
     int month;     //月
     int day;       //日
     int hour;      //时
     int minute;    //分
     int kind;       //收入还是支出  收入是1 支出0

     public AccountBean(int id, String typeName, int sImageId, String notes, float money, String time, int year, int month, int day, int hour, int minute, int kind) {
          this.id = id;
          this.typeName = typeName;
          this.sImageId = sImageId;
          this.notes = notes;
          this.money = money;
          this.time = time;
          this.year = year;
          this.month = month;
          this.day = day;
          this.hour = hour;
          this.minute = minute;
          this.kind = kind;
     }

     public AccountBean() {
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

     public int getsImageId() {
          return sImageId;
     }

     public void setsImageId(int sImageId) {
          this.sImageId = sImageId;
     }

     public String getNotes() {
          return notes;
     }

     public void setNotes(String notes) {
          this.notes = notes;
     }

     public float getMoney() {
          return money;
     }

     public void setMoney(float money) {
          this.money = money;
     }

     public String getTime() {
          return time;
     }

     public void setTime(String time) {
          this.time = time;
     }

     public int getYear() {
          return year;
     }

     public void setYear(int year) {
          this.year = year;
     }

     public int getMonth() {
          return month;
     }

     public void setMonth(int month) {
          this.month = month;
     }

     public int getDay() {
          return day;
     }

     public void setDay(int day) {
          this.day = day;
     }

     public int getHour() {
          return hour;
     }

     public void setHour(int hour) {
          this.hour = hour;
     }

     public int getMinute() {
          return minute;
     }

     public void setMinute(int minute) {
          this.minute = minute;
     }

     public int getKind() {
          return kind;
     }

     public void setKind(int kind) {
          this.kind = kind;
     }

     @Override
     public String toString() {
          return "AccountBean{" +
                  "id=" + id +
                  ", typeName='" + typeName + '\'' +
                  ", sImageId=" + sImageId +
                  ", notes='" + notes + '\'' +
                  ", money=" + money +
                  ", time='" + time + '\'' +
                  ", year=" + year +
                  ", month=" + month +
                  ", day=" + day +
                  ", hour=" + hour +
                  ", minute=" + minute +
                  ", kind=" + kind +
                  '}';
     }
}
