import React from "react";

import Banner from "../components/common/Banner";
import CircularProgress from '@mui/material/CircularProgress';
import { useLogin } from "../hooks/login";
import style from "../styles/pages/Home.module.css";

import { Card, CardContent, Box, Typography } from "@mui/material";

const Login = () => {
  const data = useLogin();

  return (
    <Card sx={{ margin: '2%', minHeight: '500px', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
      <CardContent>
        <Box
          sx={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            height: '100%',
            textAlign: 'center',
          }}
        >
          <Typography variant="h6" gutterBottom>
            깃허브 정보를 가져오는 중입니다..
          </Typography>
          <CircularProgress />
        </Box>
      </CardContent>
    </Card>
  );
};

export default Login;
