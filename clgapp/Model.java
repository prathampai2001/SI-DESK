package com.example.clgapp;

public class Model {
   private String TimeTableUrl;
   public Model(){

   }

   public Model(String timeTableUrl) {
      this.TimeTableUrl = timeTableUrl;
   }

   public String getTimeTableUrl() {
      return TimeTableUrl;
   }

   public void setTimeTableUrl(String timeTableUrl) {
      this.TimeTableUrl = timeTableUrl;
   }

   /*public Model(String imageUrl)
   {
      this.TimeTableUrl=imageUrl;
   }*//*

   public String getImageUrl() {
      return TimeTableUrl;
   }

   public void setImageUrl(String imageUrl) {
      this.TimeTableUrl = imageUrl;
   }*/
}
