import React from "react";

import NavBar from "../components/common/Nav";
import Footer from "../components/common/Footer";
import UserInfo from "../components/users/Info";
import Repository from "../components/users/Repository";
import { useSolutionList } from "../hooks/solutionList";
import style from "../styles/pages/Home.module.css";

const Users = () => {
  return (
    <div>
      <div className={style.wrapper}>
        <NavBar />
        <UserInfo />
        <Repository />
      </div>
      <div className={style.footer}>
        <Footer />
      </div>
    </div>
  );
};

export default Users;
