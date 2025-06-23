FROM node:lts AS builder

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

ARG BUILD_ENV=production
ENV NODE_ENV=$BUILD_ENV

RUN npm run build

EXPOSE 3000

ENTRYPOINT ["npm", "run", "dev"]

