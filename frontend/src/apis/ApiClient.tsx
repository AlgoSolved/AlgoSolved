import axios from "axios";

export const ApiClient = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL,
  headers: {
    "content-type": "application/json",
  },
});
