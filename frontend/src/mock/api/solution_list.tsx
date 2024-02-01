// src/components/mocks/handlers.ts
import { ApiResponseProps } from "@/types/common/BaseResponse";
import { Solution } from "@/types/solution/Solution";
import { rest } from "msw";

export const getBlankSolutionList = (isError?: boolean) => {
  return rest.get<ApiResponseProps<Solution[]>>(
    "api/v1/solutions/recent-list",
    (_req: any, res: any, ctx: any) => {
      return res.json(
        {
          code: "4001",
          data: [],
          message: "",
        },
        { status: 200 },
      );
    },
  );
};

export default [getBlankSolutionList()];
