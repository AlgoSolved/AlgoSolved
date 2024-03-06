import React from "react";

import { render, renderHook, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";

import { getUserInfo } from "../../apis/v1/users/info";
import AccountInfo from "../../components/users/Info";
import { userApiHandlers } from "../../mock/api/user_info";
import { setupServer } from "msw/node";

describe("유저 정보 컴포넌트 테스트", () => {
  const server = setupServer(...userApiHandlers);
  beforeAll(() => server.listen());
  afterEach(() => server.resetHandlers());
  afterAll(() => {
    server.close();
  });

  test("user 정보가 빈값인 경우 빈값을 그대로 보여준다.", async () => {
    const result = await getUserInfo(0);
    const { container } = render(<AccountInfo user={result} />);

    await waitFor(() => {
      expect(
        container.getElementsByClassName("user_container")[0].textContent,
      ).toEqual("유저 정보를 가져올 수 없습니다.");
    });
  });

  test("user 정보가 있는 경우 사용자와 관련된 값을 보여준다.", async () => {
    const result = await getUserInfo(1);
    const { container } = render(<AccountInfo user={result} />);

    expect(
      await container.getElementsByClassName("user_container")[0].textContent,
    ).toEqual("이름 : test공유한 풀이 수 : 100공유된 레포지토리 링크 : link");
  });
});
