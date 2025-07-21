package com.app.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {

       @Id
       private String email;
       @Column
       private String password;
}
