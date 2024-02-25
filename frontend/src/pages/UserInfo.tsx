import React from "react";

import NavBar from "../components/common/Nav";
import Footer from "../components/common/Footer";
import Banner from "../components/common/Banner";
import UserInfo from "../components/users/Info";
import { useSolutionList } from "../hooks/solutionList";
import style from "../styles/pages/Home.module.css";

const Users = () => {
  return (
    <div>
      <div className={style.wrapper}>
        <NavBar />
        {/* <Banner /> */}
        {/* // 유저 정보 API */}
        <UserInfo />
      </div>
      <div className={style.footer}>
        <Footer />
      </div>
    </div>
  );
};

export default Users;
