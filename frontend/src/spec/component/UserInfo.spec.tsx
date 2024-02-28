import React from "react";

import { render, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";

import { server } from "../../mock/server";
import { getUserInfo } from "../../apis/v1/users/info";
import AccountInfo from "../../components/users/Info";
import {
  getBlankUserInfo,
  getNotBlankUserInfo,
} from "../../mock/api/user_info";

describe("유저 정보 컴포넌트 테스트", () => {
  afterEach(() => server.resetHandlers());

  it("user 정보가 빈값인 경우 빈값을 그대로 보여준다.", async () => {
    server.use(getBlankUserInfo(false));

    const { container } = render(<AccountInfo user={getUserInfo()} />);

    await waitFor(() => {
      expect(
        container.getElementsByClassName("user_container")[0].textContent,
      ).toEqual("유저 정보를 가져올 수 없습니다.");
    });
  });
});
