package com.app.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "userdetails")
public class User {

       @Id
       private String email;
       @Column
       private String firstName;
       @Column
       private String lastName;
       @Column
       private String password;
}
