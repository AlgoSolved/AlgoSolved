import axios from "axios";

export const ApiClient = axios.create({
  baseURL: process.env.API_BASE_URL,
  headers: {
    "content-type": "application/json",
  },
});

ApiClient.interceptors.request.use(
  (response) => {
    return response;
  },
  (error) => {
    return Promise.reject(error);
  },
);

ApiClient.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response && error.response.status) {
      switch (error.response.status) {
        case 401:
          //TODO: 로그인 페이지 개발 후 리다이렉트 추가
          console.log("401 Error");
          break;
        case 500:
          return Promise.reject(error);
        default:
          console.log(error.response.status, " Error");
          break;
      }
    }
  },
);
