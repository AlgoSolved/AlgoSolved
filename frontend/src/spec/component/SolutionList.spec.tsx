import React from "react";

import { render, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";
import { rest } from "msw";

import SolutionList from "../../components/home/SolutionList";

describe("메인 페이지 통합테스트", () => {
  it("solution list api 가 빈값일 때 풀이가 없다고 나온다.", async () => {
    rest.get(
      "api/v1/solutions/recent-list",
      (_req: any, _res: any, ctx: any) => {
        return ctx.json(
          {
            code: "4001",
            data: [],
            message: "",
          },
          { status: 200 },
        );
      },
    );

    const { container } = render(<SolutionList list={[]} />);

    await waitFor(() => {
      expect(
        container.getElementsByClassName("blank_list")[0].textContent,
      ).toEqual("최근 문제 풀이가 없습니다.");
    });
  });
});
