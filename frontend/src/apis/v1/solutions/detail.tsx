import { AxiosErrorClass } from "../../../types/common/error/Error";
import { ApiResponseProps } from "../../../types/common/BaseResponse";
import { SolutionDetail } from "../../../types/solution/Solution";
import { ApiClient } from "../../ApiClient";

export const getSolutionDetail = async (id: number) => {
  try {
    const res: ApiResponseProps<SolutionDetail> = await ApiClient.get(
      `/api/v1/solutions/${id}`,
    );

    return res.data.data;
  } catch (error: AxiosErrorClass | any) {
    if (error.data) {
      console.log(error);
    }
  }
};
