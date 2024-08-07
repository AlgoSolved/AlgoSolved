import { ApiClient } from "../../ApiClient";
import { AxiosErrorClass } from "../../../types/common/error/Error";
import { ApiResponseProps } from "../../../types/common/BaseResponse";


export const loginUser = async () => {
  try {
    const res: any = await ApiClient.post(`api/login`);

    return res.data.data;
  } catch (error: AxiosErrorClass | any) {
    if (error.data) {
      console.log(error);
    }
  }
};
