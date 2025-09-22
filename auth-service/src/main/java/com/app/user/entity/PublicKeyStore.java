package com.app.user.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class PublicKeyStore {

       @Id
       String kid;
       String key;
}
