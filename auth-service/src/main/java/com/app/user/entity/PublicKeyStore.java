package com.app.user.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Data
public class PublicKeyStore {

       @Id
       String kid;
       @Lob
       @Column(name = "public_key",columnDefinition = "TEXT")
       String key;
}
