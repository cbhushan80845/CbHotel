# Stage 1: Build the Vite application
FROM node:18-alpine as build

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json to install dependencies
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application files
COPY . .

# Output the current directory structure for debugging
RUN ls -R

# Build the Vite application
RUN npm run build

# Stage 2: Serve the Vite application with a simple Node.js server
FROM node:18-alpine

# Set working directory
WORKDIR /app

# Copy the build output from the previous stage to the current stage
COPY --from=build /app/dist ./dist

# Install a simple static file server (e.g., serve)
RUN npm install -g serve

# Expose port 3000
EXPOSE 5173

# Start the server
CMD ["serve", "-s", "dist", "-l", "5173"]
