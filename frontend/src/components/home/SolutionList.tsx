import React from "react";
import { Link } from 'react-router-dom';

import EmojiObjectsIcon from '@mui/icons-material/EmojiObjects';
import { Container, List, ListItem, ListItemText, ListItemIcon } from "@mui/material";
import ListItemAvatar from '@mui/material/ListItemAvatar';
import Avatar from '@mui/material/Avatar';
import ListItemButton from '@mui/material/ListItemButton';

import styles from "../../styles/components/home/SolutionList.module.css";
import { Solution, ProblemType } from "../../types/solution/Solution";

const SolutionItemList = (props: any) => {

  return (
    <>
      <Container>
        <div className={styles.title}>최근 문제 풀이</div>
        {props.list === undefined || props.list?.length === 0 ? (
          <div className={styles.blank_list}>최근 문제 풀이가 없습니다.</div>
        ) : (
          <List className={styles.list_container}>
            {props.list?.map((solution: Solution, index: number) => (
            <Link key={solution.id} to={'/problem/detail/'+solution.id} style={{ textDecoration: "none"}}>
            <ListItemButton>
              <ListItem
                key={index}
                style={{color: "black"}}
                disableGutters
                secondaryAction={solution.userName}
              >
                <ListItemIcon>
                    <EmojiObjectsIcon color="primary"/>
                </ListItemIcon>
                <ListItemText
                  key={index+"text"}
                  style={{color: "black"}}
                  primary={solution.problemNumber + " -" + solution.problemName}
                  secondary={
                    solution.problemType == ProblemType.Programmers ? " 프로그래머스 " : " 백준 "
                  }
                />
              </ListItem>
              </ListItemButton>
            </Link>
            ))}
          </List>
        )}
      </Container>
    </>
  );
};

export default SolutionItemList;
