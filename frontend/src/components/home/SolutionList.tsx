import React from "react";

import { Container, List, ListItem, ListItemText } from "@mui/material";

import styles from "../../styles/components/home/SolutionList.module.css";
import { Solution } from "../../types/solution/Solution";

const SolutionItemList = (props: any) => {
  console.log(props.list);
  return (
    <div>
      <Container>
        <div className={styles.title}>최근 문제 풀이</div>
        {props.list === undefined || props.list?.length === 0 ? (
          <div className={styles.blank_list}>최근 문제 풀이가 없습니다.</div>
        ) : (
          <List className={styles.list_container}>
            {props.list?.map((solution: Solution, index: number) => (
              <ListItem
                key={index}
                disableGutters
                secondaryAction={solution.userName}
              >
                <ListItemText
                  primary={
                    solution.problemName +
                    " " +
                    solution.problemType +
                    " - " +
                    solution.problemName
                  }
                />
              </ListItem>
            ))}
          </List>
        )}
      </Container>
    </div>
  );
};

export default SolutionItemList;
