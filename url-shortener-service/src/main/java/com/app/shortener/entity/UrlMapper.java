package com.app.shortener.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlMapper {

    @Id
    String shortUrl;
    @Column(unique = true, nullable = false)
    String longUrl;
}
