import axios from "axios";

export const ApiClient = axios.create({
  baseURL: process.env.API_BASE_URL,
  headers: {
    "content-type": "application/json",
  },
});
