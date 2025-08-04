import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import { Container, List, ListItem, ListItemText, ListItemIcon } from "@mui/material";

import "./App.css";
import Footer from "./components/common/Footer";
import NavBar from "./components/common/Nav";
import Seo from "./components/common/Seo";
import Router from "./routers/router";

function App() {
  return (
  <>
    <BrowserRouter>
    <div className="app-wrapper">
      <Seo />
      <NavBar />
      <div className="app-content">
      <Container>
        <Router />
      </Container>
      </div>
      <footer className="footer">
        <Footer />
      </footer>
    </div>
    </BrowserRouter>
  </>
  );
}

export default App;
