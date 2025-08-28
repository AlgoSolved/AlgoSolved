import React from 'react';
import { Box, Typography, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const NotFoundPage = () => {
const navigate = useNavigate();

return (
<Box
    display="flex"
    flexDirection="column"
    justifyContent="center"
    alignItems="center"
    height="100vh"
    textAlign="center"
    px={2}
>
  <Typography variant="h1" color="primary" gutterBottom>
    404
  </Typography>
  <Typography variant="h5" gutterBottom>
    페이지를 찾을 수 없습니다.
  </Typography>
  <Typography variant="body1" mb={4}>
    존재하지 않는 경로이거나, 주소가 잘못되었습니다.
  </Typography>
  <Button variant="contained" color="primary" onClick={() => navigate('/')}>
  홈으로 이동
  </Button>
</Box>
);
};

export default NotFoundPage;
