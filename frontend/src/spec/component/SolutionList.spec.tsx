import React from "react";

import { render, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";

import { getBlankSolutionList } from "../../mock/api/solution_list";
import { server } from "../../mock/server";
import SolutionList from "../../components/home/SolutionList";
import { solutionList } from "../../apis/v1/solutions";

describe("메인 페이지 통합테스트", () => {
  it("solution list api 가 빈값일 때 풀이가 없다고 나온다.", async () => {
    server.use(getBlankSolutionList(true));

    const { container } = render(<SolutionList list={solutionList} />);

    await waitFor(() => {
      expect(
        container.getElementsByClassName("blank_list")[0].textContent,
      ).toEqual("최근 문제 풀이가 없습니다.");
    });
  });
});
