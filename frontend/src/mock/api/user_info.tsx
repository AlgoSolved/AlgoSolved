// src/components/mocks/handlers.ts
import { ApiResponseProps } from "@/types/common/BaseResponse";
import { UserInfo } from "@/types/users/Info";
import { rest } from "msw";

// TODO: API 에 맞는 uri 로 변경
export const getBlankUserInfo = (isError?: boolean) => {
  return rest.get<ApiResponseProps<UserInfo>>(
    "api/v1/users/",
    (_req: any, res: any, ctx: any) => {
      return res.json(
        {
          code: "4000",
          data: {},
          message: "success",
        },
        { status: 404 },
      );
    },
  );
};

export const getNotBlankUserInfo = (isError?: boolean) => {
  return rest.get<ApiResponseProps<UserInfo>>(
    "api/v1/users/",
    (_req: any, res: any, ctx: any) => {
      return res.json(
        {
          code: "2000",
          data: {
            id: 1,
            username: "test",
            sharedCount: 100,
            link: "link",
          },
          message: "success",
        },
        { status: 200 },
      );
    },
  );
};

export default [getBlankUserInfo(), getNotBlankUserInfo()];
