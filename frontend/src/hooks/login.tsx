import { useEffect, useState } from "react";

import { loginUser } from "../apis/v1/users/login";

export function useLogin() {
  const [loginInfo, setLoginInfo] = useState<any>();

  useEffect(() => {
    const initLoginInfo = async () => {
      const data = await loginUser();

      setLoginInfo(data);
    };
    initLoginInfo();
  }, []);

  return loginInfo;
}


