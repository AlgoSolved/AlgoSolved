import React from "react";
import { Container, List, ListItem, ListItemText } from "@mui/material";
import styles from "../../styles/components/users/Repository.module.css";
import { Solution } from "../../types/solution/Solution";

const Repository = (props: any) => {
  return (
    <div>
      <Container>
        <div className={styles.title}>레포지토리 연동</div>

        <div className={styles.main_container}>
          <div className={styles.repository_container}>
            <div>깃허브 레포지토리 링크 변경 {}</div>
            <div className={styles.repo_input_container}>
              <input className={styles.repo_input}></input>
              <button className={styles.repo_update_button}>적용</button>
            </div>
            <div className={styles.description}>
              새로 고침을 통해 공유된 레포지토리 링크가 제대로 설정 되었는지
              확인 하세요
            </div>
          </div>
        </div>
      </Container>
    </div>
  );
};

export default Repository;
