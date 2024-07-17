import { AxiosErrorClass } from "../../types/common/error/Error";
import { ApiResponseProps } from "../../types/common/BaseResponse";
import { Solution } from "../../types/solution/Solution";
import { ApiClient } from "../ApiClient";

export const solutionList = async () => {
  try {
    const res: ApiResponseProps<Solution[]> = await ApiClient.get(
      `/api/v1/solutions/recent-list`,
    );

    console.log(res);
    console.log(res.status);
    console.log(res.body.data);

    return res.body.data;
  } catch (error: AxiosErrorClass | any) {
    if (error.data) {
      console.log(error);
    }
  }
};
