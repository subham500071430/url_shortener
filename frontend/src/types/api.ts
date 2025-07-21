export interface UrlShortenRequest {
  longUrl: string;
}

export interface UrlShortenResponse {
  outputUrl: string;
}

export interface UrlMapping {
  shortUrl: string;
  longUrl: string;
  createdAt?: string;
}