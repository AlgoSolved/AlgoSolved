import React from "react";

import NavBar from "../components/common/Nav";
import Footer from "../components/common/Footer";
import AccountInfo from "../components/users/Info";
import Repository from "../components/users/Repository";
import { useUserInfo } from "../hooks/userInfo";
import style from "../styles/pages/Home.module.css";

const UserInfo = () => {
  return (
    <div>
      <div className={style.wrapper}>
        <NavBar />
        <AccountInfo user={useUserInfo()} />
        <Repository />
      </div>
      <div className={style.footer}>
        <Footer />
      </div>
    </div>
  );
};

export default UserInfo;
