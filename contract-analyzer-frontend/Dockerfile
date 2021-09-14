FROM node:latest
WORKDIR /usr/src/app
COPY . /usr/src/app

ARG REACT_APP_API_BASE_URL
ENV REACT_APP_API_BASE_URL=${REACT_APP_API_BASE_URL}

RUN npm install -g nodemon
RUN npm install

CMD ["npm", "start"]
ENTRYPOINT ["nodemon", "/usr/src/app/server.js"]