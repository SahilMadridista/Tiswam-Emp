package com.example.tiswamemp;

import java.util.ArrayList;

public class Lead {

   String bde_name;
   String bde_phone;
   String bde_email;
   String name;
   String email;
   String phone;
   String business_name;
   String business_address;
   String meeting_date;
   String meeting_time;
   ArrayList<String> services_offered;
   String bdm;
   String deal_amount;
   String advance_amount;
   String remaining_amount;
   String payment_mode;
   String bdm_assigned_status;

   public Lead(){

   }

   public Lead(String bde_name, String bde_phone, String bde_email, String name,
               String email, String phone, String business_name, String business_address,
               String meeting_date, String meeting_time, ArrayList<String> services_offered,
               String bdm, String deal_amount, String advance_amount,
               String remaining_amount, String payment_mode, String deal_status) {
      this.bde_name = bde_name;
      this.bde_phone = bde_phone;
      this.bde_email = bde_email;
      this.name = name;
      this.email = email;
      this.phone = phone;
      this.business_name = business_name;
      this.business_address = business_address;
      this.meeting_date = meeting_date;
      this.meeting_time = meeting_time;
      this.services_offered = services_offered;
      this.bdm = bdm;
      this.deal_amount = deal_amount;
      this.advance_amount = advance_amount;
      this.remaining_amount = remaining_amount;
      this.payment_mode = payment_mode;
      this.bdm_assigned_status = deal_status;
   }

   public String getBde_name() {
      return bde_name;
   }

   public String getBde_phone() {
      return bde_phone;
   }

   public String getBde_email() {
      return bde_email;
   }

   public String getName() {
      return name;
   }

   public String getEmail() {
      return email;
   }

   public String getPhone() {
      return phone;
   }

   public String getBusiness_name() {
      return business_name;
   }

   public String getBusiness_address() {
      return business_address;
   }

   public String getMeeting_date() {
      return meeting_date;
   }

   public String getMeeting_time() {
      return meeting_time;
   }

   public ArrayList<String> getServices_offered() {
      return services_offered;
   }

   public String getBdm() {
      return bdm;
   }

   public String getDeal_amount() {
      return deal_amount;
   }

   public String getAdvance_amount() {
      return advance_amount;
   }

   public String getRemaining_amount() {
      return remaining_amount;
   }

   public String getPayment_mode() {
      return payment_mode;
   }

   public String getBdm_assigned_status() {
      return bdm_assigned_status;
   }
}
