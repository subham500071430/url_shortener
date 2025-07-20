import { useState, useEffect } from 'react';
import type { UrlMapping } from '../types/api';

const STORAGE_KEY = 'url-shortener-history';

export const useUrlHistory = () => {
  const [urlHistory, setUrlHistory] = useState<UrlMapping[]>([]);

  // Load from localStorage on mount
  useEffect(() => {
    try {
      const saved = localStorage.getItem(STORAGE_KEY);
      if (saved) {
        const parsed = JSON.parse(saved);
        setUrlHistory(parsed);
      }
    } catch (error) {
      console.error('Error loading URL history:', error);
    }
  }, []);

  // Save to localStorage whenever history changes
  useEffect(() => {
    try {
      localStorage.setItem(STORAGE_KEY, JSON.stringify(urlHistory));
    } catch (error) {
      console.error('Error saving URL history:', error);
    }
  }, [urlHistory]);

  const addUrl = (mapping: UrlMapping) => {
    setUrlHistory(prev => {
      // Prevent duplicates based on longUrl
      const filtered = prev.filter(item => item.longUrl !== mapping.longUrl);
      // Add new item at the beginning (most recent first)
      return [mapping, ...filtered].slice(0, 50); // Keep only last 50 items
    });
  };

  const clearHistory = () => {
    setUrlHistory([]);
  };

  const removeUrl = (shortUrl: string) => {
    setUrlHistory(prev => prev.filter(item => item.shortUrl !== shortUrl));
  };

  return {
    urlHistory,
    addUrl,
    clearHistory,
    removeUrl,
  };
};