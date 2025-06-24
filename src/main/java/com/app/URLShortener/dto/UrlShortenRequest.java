package com.app.URLShortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlShortenRequest {

       @URL(message = "Invalid URL")
       private String inputUrl;

       public String getInputUrl() {
              return inputUrl;
       }
}
