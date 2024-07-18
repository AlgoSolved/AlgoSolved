import { RepoInfo } from "../types/repository/Connect";
import { useEffect, useState } from "react";
import { connectRepository } from "../apis/v1/github-repositories/connectRepository";

export function useConnectRepository(repoInfo: RepoInfo) {

  useEffect(() => {
    const initUserInfo = async () => {
      try {
        const data = await connectRepository(repoInfo);
      } catch {
        console.log("error!");
      }
    };
    initUserInfo();
  }, []);
}
