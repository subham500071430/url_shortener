import React, { useState } from 'react';
import { urlShortenerApi } from '../services/api';
import type { UrlMapping } from '../types/api';
import './UrlShortenerForm.css';

interface UrlShortenerFormProps {
  onUrlShortened: (mapping: UrlMapping) => void;
}

export const UrlShortenerForm: React.FC<UrlShortenerFormProps> = ({ onUrlShortened }) => {
  const [longUrl, setLongUrl] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);

    // Basic validation
    if (!longUrl.trim()) {
      setError('Please enter a URL');
      return;
    }

    if (!urlShortenerApi.validateUrl(longUrl)) {
      setError('Please enter a valid URL (include http:// or https://)');
      return;
    }

    setIsLoading(true);
    try {
      const response = await urlShortenerApi.shortenUrl({ longUrl });
      
      const mapping: UrlMapping = {
        shortUrl: response.outputUrl,
        longUrl: longUrl,
        createdAt: new Date().toISOString(),
      };

      onUrlShortened(mapping);
      setLongUrl(''); // Clear form
    } catch (err) {
      console.error('Error shortening URL:', err);
      setError('Failed to shorten URL. Please check if the backend is running and try again.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="url-shortener-form">
      <h2>Shorten Your URL</h2>
      <form onSubmit={handleSubmit}>
        <div className="input-group">
          <input
            type="text"
            value={longUrl}
            onChange={(e) => setLongUrl(e.target.value)}
            placeholder="Enter your long URL here (e.g., https://example.com/very/long/path)"
            className={`url-input ${error ? 'error' : ''}`}
            disabled={isLoading}
          />
          <button 
            type="submit" 
            className="shorten-btn"
            disabled={isLoading || !longUrl.trim()}
          >
            {isLoading ? 'Shortening...' : 'Shorten URL'}
          </button>
        </div>
        {error && <div className="error-message">{error}</div>}
      </form>
    </div>
  );
};