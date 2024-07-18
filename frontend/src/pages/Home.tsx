import React from "react";

import Banner from "../components/common/Banner";
import NavBar from "../components/common/Nav";
import SolutionList from "../components/home/SolutionList";
import { useSolutionList } from "../hooks/solutionList";
import style from "../styles/pages/Home.module.css";

const Home = () => {
  return (
    <div className={style.wrapper}>
      <NavBar />
      <Banner />
      <SolutionList list={useSolutionList()} />
    </div>
  );
};

export default Home;
