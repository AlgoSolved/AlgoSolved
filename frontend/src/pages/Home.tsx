import { useEffect, useState } from "react";
import NavBar from "../components/common/Nav";
import Footer from "../components/common/Footer";
import Banner from "../components/common/Banner";
import SolutionList from "../components/home/SolutionList";
import useSolutionList from "../hooks/solutionList";
import style from "../styles/pages/Home.module.css";

const Home = () => {
  return (
    <div className={style.wrapper}>
      <div className={style.content}>
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
