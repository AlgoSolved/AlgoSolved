import { UserInfo } from "../types/users/Info";
import { useEffect, useState } from "react";
import { getUserInfo } from "../apis/v1/users/info";

export function useUserInfo(id: number) {
  const [user_info, setUserInfo] = useState<UserInfo | undefined>();

  useEffect(() => {
    const initUserInfo = async () => {
      try {
        const data = await getUserInfo(id);
        console.log("data: ", data);
        setUserInfo(data);
      } catch {
        console.log("error!");
      }
    };
    initUserInfo();
  }, [id]);

  return user_info;
}
