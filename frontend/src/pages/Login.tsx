import React from "react";
import { useNavigate } from 'react-router-dom';

import Banner from "../components/common/Banner";
import NavBar from "../components/common/Nav";
import CircularProgress from '@mui/material/CircularProgress';
import { useLogin } from "../hooks/login";
import style from "../styles/pages/Home.module.css";




const Login = () => {
  const navigate = useNavigate();
  const data = useLogin();

  if (data === true) {
    // 응답이 true일 경우, 다른 페이지로 리다이렉트
    navigate('/'); // 원하는 페이지로 이동
  }

  return (
    <div className={style.wrapper}>
      <NavBar />
      <CircularProgress />
      <div>깃허브 정보를 가져오는 중입니다..</div>
    </div>
  );
};

export default Login;
