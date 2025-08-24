# ---- Stage 1: Build the React app ----
FROM node:18 AS build
WORKDIR /app

# Copy package files and install dependencies first (to leverage Docker cache)
COPY package*.json ./
RUN npm ci

# Copy the remaining source code and build
COPY . .
RUN npm run build

# ---- Stage 2: Serve the built app ----
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Copy build output from the previous stage
COPY --from=build /app/build ./

# Heroku provides the port via $PORT, so update Nginx to listen on that port
RUN sed -i 's/80/${PORT}/g' /etc/nginx/conf.d/default.conf

# Expose the Heroku-assigned port
EXPOSE 8080
ENV PORT=8080

CMD ["nginx", "-g", "daemon off;"]