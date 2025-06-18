import React from "react";

import Banner from "../components/common/Banner";
import NavBar from "../components/common/Nav";
import CircularProgress from '@mui/material/CircularProgress';
import { useLogin } from "../hooks/login";
import style from "../styles/pages/Home.module.css";


const Login = () => {
  const data = useLogin();

  return (
    <div className={style.wrapper}>
      <NavBar />
      <CircularProgress />
      <div>깃허브 정보를 가져오는 중입니다..</div>
    </div>
  );
};

export default Login;
