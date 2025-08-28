import React, { useEffect, useState } from "react";
import { Link } from 'react-router-dom';

import Box from '@mui/material/Box';
import ButtonGroup from '@mui/material/ButtonGroup';
import Button from '@mui/material/Button';
import AppBar from '@mui/material/AppBar';
import Typography from '@mui/material/Typography';
import InputBase from '@mui/material/InputBase';
import IconButton from '@mui/material/IconButton';
import SearchIcon from '@mui/icons-material/Search';
import AccountCircle from '@mui/icons-material/AccountCircle';
import Container from '@mui/material/Container';
import { styled, alpha } from '@mui/material/styles';
import Toolbar from '@mui/material/Toolbar';

import { loginUser } from "../../apis/v1/users/login";

const Search = styled('div')(({ theme }) => ({
  position: 'relative',
  borderRadius: theme.shape.borderRadius,
  backgroundColor: alpha(theme.palette.common.white, 0.15),
  '&:hover': {
    backgroundColor: alpha(theme.palette.common.white, 0.25),
  },
  marginRight: theme.spacing(2),
  marginLeft: 0,
  width: '100%',
  [theme.breakpoints.up('sm')]: {
    marginLeft: theme.spacing(3),
    width: 'auto',
  },
}));

const SearchIconWrapper = styled('div')(({ theme }) => ({
  padding: theme.spacing(0, 2),
  height: '100%',
  position: 'absolute',
  pointerEvents: 'none',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
}));

const StyledInputBase = styled(InputBase)(({ theme }) => ({
  color: 'inherit',
  '& .MuiInputBase-input': {
    padding: theme.spacing(1, 1, 1, 0),
    // vertical padding + font size from searchIcon
    paddingLeft: `calc(1em + ${theme.spacing(4)})`,
    transition: theme.transitions.create('width'),
    width: '100%',
    [theme.breakpoints.up('md')]: {
      width: '30ch',
    },
  },
}));

const Logo = styled(Link)(({theme}) => ({
    color: 'white',
    textDecoration: 'none'
}));

const isLoggedIn = false;


export const NavBar = () => {

  // TODO: 서버의 세션 정보를 클라이언트에 저장해야함.
  const handleLogin = () => {
    loginUser();
  };

  return (

        <AppBar position="static" sx={{ backgroundColor: "#0D1117", color: "#ffffff" }}>
        <Container>
            <Toolbar>
                <Logo to={'/'} >
                    Algo Solved
                </Logo>
                <Box sx={{ flexGrow: 1 }} />
                <Search>
                    <SearchIconWrapper>
                        <SearchIcon />
                    </SearchIconWrapper>
                    <StyledInputBase
                        placeholder="Search..."
                        inputProps={{ 'aria-label': 'search' }}
                    />
                </Search>
                <Box sx={{ display: { xs: 'none', md: 'flex'} }}>
                { isLoggedIn ?
                    <IconButton
                      size="large"
                      edge="end"
                      aria-label="account of current user"
                        //aria-controls={menuId}
                      aria-haspopup="true"
                        //onClick={handleProfileMenuOpen}
                      color="inherit"
                    >
                      <AccountCircle />
                    </IconButton>
                    :

                    <ButtonGroup
                      disableElevation
                      variant="contained"
                      aria-label="Disabled elevation buttons"
                    >
                    <a href={process.env.REACT_APP_API_BASE_URL+'/api/oauth2/authorization/github'}>
                      <Button>Login</Button>
                    </a>
                    </ButtonGroup>
                }
                </Box>
            </Toolbar>
            </Container>
        </AppBar>
   );
}

export default NavBar;
