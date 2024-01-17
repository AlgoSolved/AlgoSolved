import { Solution } from "@/types/solution/Solution";
import { ApiClient } from "../ApiClient";
import { AxiosErrorClass } from "@/types/common/error/Error";
import { ApiResponseProps } from "@/types/common/BaseResponse";

export const solutionList = async () => {
  try {
    const res: ApiResponseProps<Solution[]> = await ApiClient.get(
      `api/v1/solutions/recent-list`,
    );

    return res.body.data;
  } catch (error: AxiosErrorClass | any) {
    if (error.response.data) {
      console.log(error);
    }
  }
};
