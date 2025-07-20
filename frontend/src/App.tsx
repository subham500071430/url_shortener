import { UrlShortenerForm } from './components/UrlShortenerForm';
import { UrlList } from './components/UrlList';
import { useUrlHistory } from './hooks/useUrlHistory';
import type { UrlMapping } from './types/api';
import './App.css';

function App() {
  const { urlHistory, addUrl, clearHistory, removeUrl } = useUrlHistory();

  const handleUrlShortened = (mapping: UrlMapping) => {
    addUrl(mapping);
  };

  return (
    <div className="app">
      <header className="app-header">
        <h1>URL Shortener</h1>
        <p>Transform your long URLs into short, shareable links</p>
      </header>
      
      <main className="app-main">
        <UrlShortenerForm onUrlShortened={handleUrlShortened} />
        <UrlList 
          urls={urlHistory} 
          onRemoveUrl={removeUrl}
          onClearHistory={clearHistory}
        />
      </main>
      
      <footer className="app-footer">
        <p>Built with React and TypeScript | Backend: Spring Boot</p>
      </footer>
    </div>
  );
}

export default App;
