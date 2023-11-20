FROM node:14

WORKDIR /usr/app/frontend

COPY package* .

RUN npm install

COPY . .

EXPOSE 3000

CMD ["npm", "start"]
