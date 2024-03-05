import { UserInfo } from "../../../types/users/Info";
import { ApiClient } from "../../ApiClient";
import { AxiosErrorClass } from "../../../types/common/error/Error";
import { ApiResponseProps } from "../../../types/common/BaseResponse";

// TODO: API 에 맞는 uri 로 변경
export const getUserInfo = async (id: number) => {
  try {
    const res: any = await ApiClient.get<UserInfo>(`api/v1/users/${id}`);

    console.log("result: ", res.data);
    return res.data.data;
  } catch (error: AxiosErrorClass | any) {
    if (error.data) {
      console.log(error);
    }
  }
};
