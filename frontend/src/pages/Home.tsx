import React from "react";

import Banner from "../components/common/Banner";
import SolutionList from "../components/home/SolutionList";
import { useSolutionList } from "../hooks/solutionList";
import style from "../styles/pages/Home.module.css";

import { Container } from "@mui/material";

const Home = () => {
  return (
      <Container>
        <div className={style.wrapper}>
          <Banner />
          <SolutionList list={useSolutionList()} />
        </div>
      </Container>
  );
};

export default Home;
