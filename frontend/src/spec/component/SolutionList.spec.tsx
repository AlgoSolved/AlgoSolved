import React from "react";
import { http, HttpResponse } from "msw";
import { render, waitFor } from "@testing-library/react";

import useSolutionList from "../../hooks/solutionList";
import SolutionList from "../../components/home/SolutionList";

describe("메인 페이지 통합테스트", async () => {
  it("solution list api 가 빈값일 때 풀이가 없다고 나온다.", async () => {
    http.get("api/v1/solutions/recent-list", () => {
      return HttpResponse.json(
        {
          code: "4001",
          data: [],
          message: "",
        },
        { status: 200 },
      );
    });

    const { container } = render(<SolutionList list={useSolutionList()} />);

    await waitFor(() => {
      expect(container).toHaveTextContent("최근 문제 풀이가 없습니다.");
    });
  });
});

// function composeStories(): { SolutionComponent: any; } {
//     throw new Error("Function not implemented.");
// }
