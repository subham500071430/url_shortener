package com.app.URLShortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlShortenRequest {

       @URL(message = "Invalid URL")
       private String inputUrl;

}
