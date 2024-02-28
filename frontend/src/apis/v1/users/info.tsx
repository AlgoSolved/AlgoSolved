import { UserInfo } from "../../../types/users/Info";
import { ApiClient } from "../../ApiClient";
import { AxiosErrorClass } from "../../../types/common/error/Error";
import { ApiResponseProps } from "../../../types/common/BaseResponse";

// TODO: API 에 맞는 uri 로 변경
export const getUserInfo = async () => {
  try {
    const res: ApiResponseProps<UserInfo> = await ApiClient.get(`api/v1/users`);

    return res.body.data;
  } catch (error: AxiosErrorClass | any) {
    if (error.data) {
      console.log(error);
    }
  }
};
