import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Home from "../pages/Home";
import Users from "../pages/UserInfo";
import SolutionDetail from "../pages/SolutionDetail";

const Router = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/users" element={<Users />} />
        <Route path="/problem/detail/:id" element={<SolutionDetail />} />
      </Routes>
    </BrowserRouter>
  );
};

export default Router;
