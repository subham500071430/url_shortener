import axios from 'axios';
import type { UrlShortenRequest, UrlShortenResponse } from '../types/api';

const API_BASE_URL = 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  // Add timeout to handle slow responses
  timeout: 10000,
});

export const urlShortenerApi = {
  /**
   * Shorten a long URL
   */
  shortenUrl: async (request: UrlShortenRequest): Promise<UrlShortenResponse> => {
    const response = await api.post<UrlShortenResponse>('/shorten/url', request);
    return response.data;
  },

  /**
   * Validate if a URL is accessible (basic check)
   */
  validateUrl: (url: string): boolean => {
    try {
      new URL(url);
      return true;
    } catch {
      return false;
    }
  }
};

export default api;