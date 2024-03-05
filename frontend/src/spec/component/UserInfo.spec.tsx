import React from "react";

import { render, waitFor } from "@testing-library/react";
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
    const { container } = render(<AccountInfo user={getUserInfo(0)} />);
    await waitFor(() => {
      expect(
        container.getElementsByClassName("user_container")[0].textContent,
      ).toEqual("유저 정보를 가져올 수 없습니다.");
    });
  });

//   test("user 정보가 있는 경우 사용자와 관련된 값을 보여준다.", async () => {
//     const { container } = render(<AccountInfo user={getUserInfo(1)} />);
//     await waitFor(() => {
//       expect(
//         container.getElementsByClassName("user_container")[0].textContent,
//       ).toEqual("이름");
//     });
//   });
});
