import React, { useState } from 'react';
import type { UrlMapping } from '../types/api';
import './UrlList.css';

interface UrlListProps {
  urls: UrlMapping[];
  onRemoveUrl: (shortUrl: string) => void;
  onClearHistory: () => void;
}

export const UrlList: React.FC<UrlListProps> = ({ urls, onRemoveUrl, onClearHistory }) => {
  const [copiedUrl, setCopiedUrl] = useState<string | null>(null);

  const copyToClipboard = async (url: string) => {
    try {
      await navigator.clipboard.writeText(url);
      setCopiedUrl(url);
      setTimeout(() => setCopiedUrl(null), 2000); // Reset after 2 seconds
    } catch (err) {
      console.error('Failed to copy URL:', err);
      // Fallback for older browsers
      const textArea = document.createElement('textarea');
      textArea.value = url;
      document.body.appendChild(textArea);
      textArea.select();
      document.execCommand('copy');
      document.body.removeChild(textArea);
      setCopiedUrl(url);
      setTimeout(() => setCopiedUrl(null), 2000);
    }
  };

  const formatDate = (dateString: string) => {
    try {
      return new Date(dateString).toLocaleString();
    } catch {
      return 'Unknown';
    }
  };

  const truncateUrl = (url: string, maxLength: number = 50) => {
    if (url.length <= maxLength) return url;
    return url.substring(0, maxLength) + '...';
  };

  if (urls.length === 0) {
    return (
      <div className="url-list">
        <div className="list-header">
          <h3>Recent URLs</h3>
        </div>
        <div className="empty-state">
          <p>No URLs shortened yet. Create your first short URL above!</p>
        </div>
      </div>
    );
  }

  return (
    <div className="url-list">
      <div className="list-header">
        <h3>Recent URLs ({urls.length})</h3>
        <button onClick={onClearHistory} className="clear-btn">
          Clear All
        </button>
      </div>
      
      <div className="url-items">
        {urls.map((urlMapping) => (
          <div key={urlMapping.shortUrl} className="url-item">
            <div className="url-info">
              <div className="short-url-section">
                <label>Short URL:</label>
                <div className="url-with-copy">
                  <span className="short-url">{urlMapping.shortUrl}</span>
                  <button 
                    onClick={() => copyToClipboard(urlMapping.shortUrl)}
                    className={`copy-btn ${copiedUrl === urlMapping.shortUrl ? 'copied' : ''}`}
                    title="Copy to clipboard"
                  >
                    {copiedUrl === urlMapping.shortUrl ? '✓' : '📋'}
                  </button>
                </div>
              </div>
              
              <div className="original-url-section">
                <label>Original URL:</label>
                <span className="original-url" title={urlMapping.longUrl}>
                  {truncateUrl(urlMapping.longUrl)}
                </span>
              </div>
              
              {urlMapping.createdAt && (
                <div className="date-section">
                  <label>Created:</label>
                  <span className="date">{formatDate(urlMapping.createdAt)}</span>
                </div>
              )}
            </div>
            
            <div className="url-actions">
              <a 
                href={urlMapping.shortUrl} 
                target="_blank" 
                rel="noopener noreferrer"
                className="test-btn"
                title="Test the short URL"
              >
                Test
              </a>
              <button 
                onClick={() => onRemoveUrl(urlMapping.shortUrl)}
                className="remove-btn"
                title="Remove from history"
              >
                ×
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};