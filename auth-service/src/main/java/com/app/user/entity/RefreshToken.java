package com.app.user.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class RefreshToken {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       int id;
       String email;
       String token;

       public RefreshToken(String email, String token) {
              this.email = email;
              this.token = token;
       }
}
