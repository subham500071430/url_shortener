package com.app.URLShortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UrlShortenResponse {

       private String outputUrl;

       public UrlShortenResponse(String outputUrl){
              this.outputUrl = outputUrl;
       }
}
