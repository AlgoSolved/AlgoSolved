import axios from "axios";
// import cookieClient from 'react-cookie'
//
//
// let cookie = cookieClient.load('JSESSIONID')
export const ApiClient = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL,
  headers: {
    "content-type": "application/json",
  },
  withCredentials: true,
});
