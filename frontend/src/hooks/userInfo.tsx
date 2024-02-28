import { UserInfo } from "../types/users/Info";
import { useEffect, useState } from "react";
import { getUserInfo } from "../apis/v1/users/info";

export function useUserInfo() {
  const [user_info, setUserInfo] = useState<UserInfo | undefined>();

  useEffect(() => {
    const initUserInfo = async () => {
      const data = await getUserInfo();
      console.log(data);
      setUserInfo(data);
    };
    initUserInfo();
  }, []);

  return user_info;
}
