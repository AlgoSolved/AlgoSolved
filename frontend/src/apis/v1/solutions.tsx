import { Solution } from "@/types/solution/Solution";
import { ApiClient } from "../ApiClient";
import { AxiosErrorClass } from "@/types/common/error/Error";

export const solutionList = async () => {
  try {
    const { status, data } =
      await ApiClient.get<Solution[]>(`api/v1/solutions`);
    return data;
  } catch (error: unknown) {
    // throw Error(error.message || error.error.message);
    // 페이지 기획에 따라 처리 필요 -> alert를 통해 메세지 창을 보여줄 건지, 그냥 넘기고, 최근 문제가 없다고 표현할지
  }
};
