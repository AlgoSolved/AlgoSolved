import React from "react";
import { Container } from "@mui/material";
import styles from "../../styles/components/users/Info.module.css";
import { UserInfo } from "../../types/users/Info";

const AccountInfo = (props: any) => {
  return (
    <div>
      <Container>
        <div className={styles.title}>유저 정보</div>

        <div className={styles.main_container}>
          <div className={styles.user_container}>
            {/* {props.user === undefined || props.user.username === undefined ? (
              <div>유저 정보를 가져올 수 없습니다.</div>
            ) : ( */}
            <div>
              <div>이름 : test</div>
              <div>공유한 풀이 수 : 100</div>
              <div>공유된 레포지토리 링크 : link</div>
            </div>
            {/* )} */}
          </div>
        </div>
      </Container>
    </div>
  );
};

export default AccountInfo;
