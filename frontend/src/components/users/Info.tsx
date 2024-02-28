import React from "react";
import { Container, List, ListItem, ListItemText } from "@mui/material";
import styles from "../../styles/components/users/Info.module.css";
import { Solution } from "../../types/solution/Solution";

const Info = (props: any) => {
  return (
    <div>
      <Container>
        <div className={styles.title}>유저 정보</div>

        <div className={styles.main_container}>
          <div className={styles.user_container}>
            <div>이름 : {}</div>
            <div>공유한 풀이 수 : {}</div>
            <div>공유된 레포지토리 링크 : {}</div>
          </div>
        </div>
      </Container>
    </div>
  );
};

export default Info;
