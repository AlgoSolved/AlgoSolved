import React, { useEffect, useState } from "react";
import { Link } from 'react-router-dom';

import { Container, List, ListItem, ListItemText } from "@mui/material";

import { SolutionDetail } from "../../types/solution/Solution";
import styles from "../../styles/components/problem/Detail.module.css";

const Detail = (props: SolutionDetail | any) => {

  return (
    <>
      <Container>
        {props ?
          <>
            <div>{props.language}</div>
            <a href={props.link}><div>{props.problemNumber}-{props.problemName}</div></a>
            <div>{props.rank}</div>
            <div>{props.sourceCode}</div>
          </>
          :
          <></>
        }
      </Container>
    </>
  );
};

export default Detail;
