import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';

import { loginUser } from "../apis/v1/users/login";
import Cookies from 'js-cookie';

export function useLogin() {
  const navigate = useNavigate();
  const [loginInfo, setLoginInfo] = useState<any>();

  useEffect(() => {
    const initLoginInfo = async () => {
      const data = await loginUser();
      if (data !== undefined ) {

        Cookies.set('accessToken', data.accessToken, { expires: 1, path: '', secure: true, sameSite: "None" });
        Cookies.set('refreshToken', data.refreshToken, { expires: 7, path: '', secure: true, sameSite: "None" });

        navigate('/');
      }

      setLoginInfo(data);
    };
    initLoginInfo();
  }, []);

  return loginInfo;
}


