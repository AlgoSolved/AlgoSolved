import React from "react";

import NavBar from "../components/common/Nav";
import AccountInfo from "../components/users/Info";
import Repository from "../components/users/Repository";
import { useUserInfo } from "../hooks/userInfo";
import style from "../styles/pages/Home.module.css";

// TODO: userUserInfo 에 현재 로그인 한 사용자의 id를 넣도록 수정
const UserInfo = () => {
  return (
    <div className={style.wrapper}>
      <NavBar />
      <AccountInfo user={useUserInfo(1)} />
      <Repository />
    </div>
  );
};

export default UserInfo;
