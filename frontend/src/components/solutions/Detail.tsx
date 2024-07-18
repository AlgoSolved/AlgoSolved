import React, { useEffect, useState } from "react";
import { Link } from 'react-router-dom';

import { Container, List, ListItem, ListItemText, Chip } from "@mui/material";

import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';

import { SolutionDetail } from "../../types/solution/Solution";
import styles from "../../styles/components/solution/Detail.module.css";

const Detail = (props: SolutionDetail | any) => {
  const [value, setValue] = useState('1');

  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    setValue(newValue);
  };

  return (
    <>
      <Container>
        {props.solution ?
        <Card sx={{ padding: "30px" }}>
          <div className={styles.detail_container}>
            <a className={styles.title} href={props.solution.link}>
              <div>[ {props.solution.problemNumber} ] {props.solution.problemName}</div>
            </a>

            <div style={{ display: 'flex', gap: "5px" }}>
              <Chip label={props.solution.language} color="primary" />
              <Chip label={"랭크"+props.solution.rank} />
            </div>


            <Box sx={{ width: '100%', marginTop: "50px" }}>
              <Tabs
                value={value}
                onChange={handleChange}
              >
                <Tab
                  value="1"
                  label="소스코드 보기"
                  wrapped
                />
              </Tabs>
            </Box>
            <Box>
              <pre>
                {props.solution.sourceCode}
              </pre>
            </Box>


          </div>

          </Card>
          :
          <></>
        }
      </Container>
    </>
  );
};

export default Detail;
