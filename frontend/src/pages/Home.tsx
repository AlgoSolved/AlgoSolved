import React from "react";

import { useEffect, useState } from "react";
import NavBar from "../components/common/Nav";
import Footer from "../components/common/Footer";
import Banner from "../components/common/Banner";
import SolutionList from "../components/home/SolutionList";
import useSolutionList from "../hooks/solutionList";

const Home = () => {
  return (
    <div>
      <div>
        <NavBar />
        <Banner />
        <SolutionList list={useSolutionList()} />
      </div>
      <div>
        <Footer />
      </div>
    </div>
  );
};

export default Home;
