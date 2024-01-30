import React from "react";

import NavBar from "../components/common/Nav";
import Footer from "../components/common/Footer";
import Banner from "../components/common/Banner";
import SolutionList from "../components/home/SolutionList";
import { useSolutionList } from "../hooks/solutionList";
import style from "../styles/pages/Home.module.css";

const Home = () => {
  return (
    <div>
      <div className={style.wrapper}>
        <NavBar />
        <Banner />
        <SolutionList list={useSolutionList()} />
      </div>
      <div className={style.footer}>
        <Footer />
      </div>
    </div>
  );
};

export default Home;
