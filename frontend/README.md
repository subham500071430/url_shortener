# URL Shortener Frontend

A modern React frontend for the URL Shortener service, built with TypeScript and Vite.

## Features

- 🎨 **Modern UI**: Clean, responsive design with gradient backgrounds and modern styling
- 🔗 **URL Shortening**: Submit long URLs and receive shortened versions
- 📋 **Copy to Clipboard**: One-click copying of shortened URLs
- 📜 **History Management**: View and manage recently shortened URLs (stored locally)
- ⚡ **Fast Performance**: Built with Vite for lightning-fast development and builds
- 🛡️ **Type Safety**: Full TypeScript support for better development experience
- 📱 **Responsive**: Works seamlessly on desktop and mobile devices
- 🚨 **Error Handling**: User-friendly error messages and loading states

## Prerequisites

- Node.js 16+ and npm
- Backend URL Shortener service running on port 8080

## Getting Started

### Installation

```bash
cd frontend
npm install
```

### Development

```bash
npm run dev
```

The application will be available at `http://localhost:5173`

### Production Build

```bash
npm run build
npm run preview
```

### Linting

```bash
npm run lint
```

## Architecture

### Project Structure

```
frontend/
├── src/
│   ├── components/          # React components
│   │   ├── UrlShortenerForm.tsx
│   │   ├── UrlShortenerForm.css
│   │   ├── UrlList.tsx
│   │   └── UrlList.css
│   ├── hooks/               # Custom React hooks
│   │   └── useUrlHistory.ts
│   ├── services/            # API integration
│   │   └── api.ts
│   ├── types/               # TypeScript type definitions
│   │   └── api.ts
│   └── utils/               # Utility functions
├── public/                  # Static assets
└── package.json
```

### Components

- **UrlShortenerForm**: Main form for URL input and submission
- **UrlList**: Displays history of shortened URLs with copy/test functionality

### API Integration

The frontend connects to the Spring Boot backend via REST API:

- `POST /bit.ly/shorten/url` - Shortens a URL
- Backend should be running on `http://localhost:8080`

### State Management

- **URL History**: Managed via custom hook `useUrlHistory`
- **Local Storage**: Recent URLs are persisted in browser localStorage
- **Error States**: Handled at component level with user-friendly messages

## Configuration

### Backend URL

The API base URL can be modified in `src/services/api.ts`:

```typescript
const API_BASE_URL = 'http://localhost:8080';
```

### CORS Requirements

The backend must allow CORS requests from the frontend origin (`http://localhost:5173` for development).

## Usage

1. **Shorten URL**: Enter a long URL in the form and click "Shorten URL"
2. **Copy URL**: Click the copy button (📋) next to any shortened URL
3. **Test URL**: Click "Test" to open the shortened URL in a new tab
4. **Remove URL**: Click "×" to remove a URL from history
5. **Clear History**: Click "Clear All" to remove all URLs from history

## Error Handling

- **Invalid URLs**: Client-side validation prevents submission of invalid URLs
- **Backend Errors**: Network errors and backend failures show user-friendly messages
- **Loading States**: UI provides feedback during API requests

## Browser Support

- Chrome 88+
- Firefox 85+
- Safari 14+
- Edge 88+

## Contributing

1. Follow the existing code style and patterns
2. Add TypeScript types for new features
3. Include CSS for new components
4. Test UI changes across different screen sizes

## Troubleshooting

### Backend Connection Issues

If you see "Failed to shorten URL" errors:

1. Ensure the backend service is running on port 8080
2. Check CORS configuration in the Spring Boot application
3. Verify the API endpoints match the expected format

### Build Issues

- Clear node_modules and reinstall dependencies: `rm -rf node_modules package-lock.json && npm install`
- Check Node.js version compatibility

## Technology Stack

- **React 19** - UI library
- **TypeScript** - Type safety
- **Vite** - Build tool and dev server
- **Axios** - HTTP client
- **CSS3** - Styling with modern features (Grid, Flexbox, CSS Variables)
