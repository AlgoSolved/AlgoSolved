import { Solution } from "@/types/solution/Solution";
import { ApiClient } from "../ApiClient";
import { AxiosErrorClass } from "@/types/common/error/Error";

export const solutionList = async () => {
  try {
    const { status, data } =
      await ApiClient.get<Solution[]>(`api/v1/solutions`);
    return data;
  } catch (error: AxiosErrorClass | any) {
    if (error.response.data) {
      console.log(error);
    }
  }
};
