import React from "react";

import Banner from "../components/common/Banner";
import CircularProgress from '@mui/material/CircularProgress';
import { useLogin } from "../hooks/login";
import style from "../styles/pages/Home.module.css";

import { Container } from "@mui/material";

const Login = () => {
  const data = useLogin();

  return (
    <Container>
      <div className={style.wrapper}>
        <CircularProgress />
        <div>깃허브 정보를 가져오는 중입니다..</div>
      </div>
    </Container>
  );
};

export default Login;
