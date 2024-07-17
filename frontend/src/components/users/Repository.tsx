import React, { useEffect, useState } from "react";

import { Container } from "@mui/material";

import { connectRepository } from "../../apis/v1/github-repositories/connectRepository";

import styles from "../../styles/components/users/Repository.module.css";

// TODO: 레포지토리 연동 API 연결
const Repository = (props: any) => {

  const [repo, setRepo] = useState<string>("");

  const useHandleSubmit = async(event: any) => {
    event.preventDefault();
    const data = await connectRepository({owner: "", repo: repo});
  }

  return (
    <div>
      <Container>
        <div className={styles.title}>레포지토리 연동</div>

        <div className={styles.main_container}>
          <div className={styles.repository_container}>
            <div>깃허브 레포지토리 링크 변경 {}</div>
            <div className={styles.repo_input_container}>
              <form onSubmit={useHandleSubmit} className={styles.repo_input} >
                <input
                  type="text"
                  name="repo"
                  placeholder="https://github.com/"
                  className={styles.repo_input}
                  onChange={(e) => setRepo(e.target.value)}
                />
                <input className={styles.repo_update_button} type="submit" value="적용" />
              </form>
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
