import { RepoInfo } from "../../../types/repository/Connect";
import { ApiClient } from "../../ApiClient";
import { AxiosErrorClass } from "../../../types/common/error/Error";
import { ApiResponseProps } from "../../../types/common/BaseResponse";

export const connectRepository = async (repoInfo: RepoInfo) => {
  try {
    const res: any = await ApiClient.post<RepoInfo>(`api/v1/github-repositories/connect`, repoInfo);
    return res.data.data;
  } catch (error: AxiosErrorClass | any) {
    if (error.data) {
      console.log(error);
    }
  }
};
