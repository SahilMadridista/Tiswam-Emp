package com.example.tiswamemp;

public class Employee {

   String name;
   String phone;
   String email;
   String password;
   String team;
   String url;

   public Employee(){

   }

   public Employee(String name, String phone, String email,
                   String password, String team, String url) {
      this.name = name;
      this.phone = phone;
      this.email = email;
      this.password = password;
      this.team = team;
      this.url = url;

   }

   public String getName() {
      return name;
   }

   public String getPhone() {
      return phone;
   }

   public String getEmail() {
      return email;
   }

   public String getPassword() {
      return password;
   }

   public String getTeam() {
      return team;
   }

   public String getUrl() {
      return url;
   }
}
