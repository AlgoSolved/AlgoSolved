import React from "react";
import { useParams } from 'react-router';

import Banner from "../components/common/Banner";
import NavBar from "../components/common/Nav";
import Detail from "../components/solutions/Detail";

import { useSolutionDetail } from "../hooks/solutionDetail";


const SolutionDetail = () => {

  const { id } = useParams();

  return (
    <div>
      <NavBar />
      <Detail solution={ useSolutionDetail(id)} />
    </div>
  );
};

export default SolutionDetail;
