import React from "react";
import { useParams } from 'react-router';

import Banner from "../components/common/Banner";
import Detail from "../components/solutions/Detail";

import { useSolutionDetail } from "../hooks/solutionDetail";
import { Container } from "@mui/material";

const SolutionDetail = () => {

  const { id } = useParams();

  return (
  <Container>
    <div>
      <Detail solution={ useSolutionDetail(Number(id))} />
    </div>
  </Container>
  );
};

export default SolutionDetail;
