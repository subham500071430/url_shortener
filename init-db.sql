-- Initialize databases for URL Shortener application
CREATE DATABASE IF NOT EXISTS `url_shortener`;
CREATE DATABASE IF NOT EXISTS `user`;

-- Grant permissions to root user for both databases
GRANT ALL PRIVILEGES ON `url_shortener`.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON `user`.* TO 'root'@'%';
FLUSH PRIVILEGES;