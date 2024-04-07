import { ApiResponseProps } from "@/types/common/BaseResponse";
import { UserInfo } from "@/types/users/Info";
import { rest } from "msw";

export const userApiHandlers = [
  rest.get<UserInfo>("api/v1/users/0", (_req: any, res: any, ctx: any) => {
    return res(
      ctx.json({
        code: "4000",
        data: {},
        message: "success",
      }),
      ctx.status(404),
    );
  }),

  rest.get<UserInfo>("api/v1/users/1", (_req: any, res: any, ctx: any) => {
    return res(
      ctx.json({
        code: "2000",
        data: {
          id: 1,
          username: "test",
          sharedCount: 100,
          link: "link",
        },
        message: "success",
      }),
      ctx.status(200),
    );
  }),
];
